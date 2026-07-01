const FUNCTIONS = [
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
];

const historyList = document.getElementById("historyList");

const copyButton = document.getElementById("copyBtn");
const clearHistoryButton = document.getElementById("clearHistoryBtn");

const buttons = [
    "MC", "MR", "MS", "M+", "M-", "C",
    "(", ")", "%", "^", "!", "/",
    "sin", "cos", "tan", "sqrt", "log", "ln",
    "7", "8", "9", "*", "pi", "e",
    "4", "5", "6", "-", "abs", "floor",
    "1", "2", "3", "+", "ceil", "round",
    "0", ".", "=", "random", "Ans", "⌫"
];

const grid = document.getElementById("buttonGrid");

buttons.forEach(text => {
    const button = document.createElement("button");
    button.className = "btn btn-primary calc-btn";
    button.textContent = text;
    grid.appendChild(button);
});

const equalsButton = [...document.querySelectorAll(".calc-btn")].find(button => button.textContent === "=");

const toggle = document.getElementById("themeToggle");

toggle.addEventListener("change", () => {
    document.body.classList.toggle("dark-mode");
});

const expressionInput = document.getElementById("expression");
const resultDisplay = document.getElementById("result");

let lastAnswer = "";

grid.addEventListener("click", (event) => {
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
            insertText(lastAnswer);
            break;

        default:
            insertText(value);
    }
}

function insertText(text) {
    if (FUNCTIONS.includes(text)) {
        text += "()"
    }

    expressionInput.focus();

    const start = expressionInput.selectionStart;
    const end = expressionInput.selectionEnd;

    expressionInput.setRangeText(text, start, end, "end");

    if (text.endsWith("()")) {
        expressionInput.setSelectionRange(
            expressionInput.selectionStart - 1,
            expressionInput.selectionStart - 1
        );
    }
}

function clearExpression() {
    expressionInput.value = "";

    setResult("0");
}

function backspace() {
    const start = expressionInput.selectionStart;
    const end = expressionInput.selectionEnd;

    if (start !== end) {
        expressionInput.setRangeText("", start, end, "end");
        return;
    }

    if (start > 0) {
        expressionInput.setRangeText("", start - 1, start, "end");
    }
}

async function calculateExpression() {
    const expression = expressionInput.value.trim();

    if (expression.length === 0) {
        setResult("Enter an expression");
        return;
    }

    setLoading(true);

    setResult("Calculating...");

    try {
        const response = await apiRequest(API.CALCULATE, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                expression: expression
            })
        });

        const data = await response.json();

        if (data.success) {
            setResult(data.result);
            lastAnswer = data.result;

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

expressionInput.addEventListener("keydown", event => {
    if (event.key === "Enter") {
        event.preventDefault();

        calculateExpression();
    }
});

copyButton.addEventListener("click", async () => {
    try {
        await navigator.clipboard.writeText(resultDisplay.textContent);
    } catch (error) {
        alert("Unable to copy.");
    }
});







async function apiRequest(url, options = {}) {
    const response = await fetch(url, options);

    if (!response.ok) {
        throw new Error(`Request failed (${response.status})`);
    }

    return response;
}