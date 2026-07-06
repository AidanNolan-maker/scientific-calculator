const FUNCTIONS = Object.freeze([
    "sin",
    "cos",
    "tan",
    "sqrt",
    "log",
    "ln",
    "abs",
    "floor",
    "ceil",
    "round",
    "random"
]);

const elements = {
    buttonGrid: document.getElementById("buttonGrid"),
    expressionInput: document.getElementById("expression"),
    resultDisplay: document.getElementById("result"),
    copyButton: document.getElementById("copyBtn"),
    themeToggle: document.getElementById("themeToggle"),
    angleModeButton: document.getElementById("angleModeBtn")
};

const buttons = [
    "MC", "MR", "MS", "M+", "M-", "C",
    "(", ")", "%", "^", "!", "/",
    "sin", "cos", "tan", "sqrt", "log", "ln",
    "7", "8", "9", "*", "pi", "e",
    "4", "5", "6", "-", "abs", "floor",
    "1", "2", "3", "+", "ceil", "round",
    "0", ".", "=", "random", "Ans", "⌫"
];

buttons.forEach(text => {
    const button = document.createElement("button");
    button.className = "btn btn-primary calc-btn";
    button.textContent = text;
    elements.buttonGrid.appendChild(button);
});

const equalsButton = [...document.querySelectorAll(".calc-btn")].find(button => button.textContent === "=");

elements.themeToggle.addEventListener("change", () => {
    document.body.classList.toggle("dark-mode");
});

elements.buttonGrid.addEventListener("click", (event) => {
    if (!event.target.classList.contains("calc-btn")) {
        return;
    }

    const value = event.target.textContent;

    handleButton(value);
});

function handleButton(value) {
    switch (value) {
        case "=":
            calculateExpression();
            break;

        case "⌫":
            backspace();
            break;

        case "C":
            clearExpression();
            break;

        case "Ans":
            insertText(appState.lastAnswer);
            break;

        case "MS":
            memoryStore();
            break;

        case "MR": {
            const value = memoryRecall();

            if (value != null)
                insertText(value);
            break;
        }

        case "MC":
            memoryClear();
            break;

        case "M+":
            memoryAdd();
            break;

        case "M-":
            memorySubtract();
            break;

        default:
            insertText(value);
    }
}

function insertText(text) {
    if (FUNCTIONS.includes(text)) {
        text += "()"
    }

    elements.expressionInput.focus();

    const start = elements.expressionInput.selectionStart;
    const end = elements.expressionInput.selectionEnd;

    elements.expressionInput.setRangeText(text, start, end, "end");

    if (text.endsWith("()")) {
        elements.expressionInput.setSelectionRange(
            elements.expressionInput.selectionStart - 1,
            elements.expressionInput.selectionStart - 1
        );
    }
}

function clearExpression() {
    elements.expressionInput.value = "";

    setResult("0");
}

function backspace() {
    const start = elements.expressionInput.selectionStart;
    const end = elements.expressionInput.selectionEnd;

    if (start !== end) {
        elements.expressionInput.setRangeText("", start, end, "end");
        return;
    }

    if (start > 0) {
        elements.expressionInput.setRangeText("", start - 1, start, "end");
    }
}

async function calculateExpression() {
    const expression = elements.expressionInput.value.trim();

    if (expression.length === 0) {
        setResult("Enter an expression");
        return;
    }

    setLoading(true);

    setResult("Calculating...");

    try {
        const data = await calculate(expression);

        if (data.success) {
            setResult(data.result);
            appState.lastAnswer = data.result;

            await loadHistory();
        } else {
            setResult("❌ " +  data.error);
        }
    } catch (error) {
        setResult("❌ Unable to connect to the server.");
    } finally {
        setLoading(false);
    }
}

elements.expressionInput.addEventListener("keydown", event => {
    if (event.key === "Enter") {
        event.preventDefault();

        calculateExpression();
    }
});

elements.copyButton.addEventListener("click", async () => {
    try {
        await navigator.clipboard.writeText(elements.resultDisplay.textContent);
    } catch (error) {
        alert("Unable to copy.");
    }
});

elements.angleModeButton.addEventListener("click",  () => {
    toggleAngleMode();
});