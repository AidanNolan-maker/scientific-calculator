const historyList = document.getElementById("historyList");
const clearHistoryButton = document.getElementById("clearHistoryBtn");

async function loadHistory() {
    try {
        const history = await getHistory();

        historyList.innerHTML = "";

        if (history.length === 0) {
            historyList.innerHTML = '<div class="text-muted">No calculations yet.</div>';
            return;
        }

        history.forEach(item => {
            const div = document.createElement("div");
            div.className = "history-item";
            div.innerHTML = `<strong>${item.expression}</strong><br> = ${item.result}<br>
                <small class="text-muted">${new Date(item.timestamp).toLocaleString()}</small>`;

            div.style.cursor = "pointer";

            div.addEventListener("click", () => {
                elements.expressionInput.value = item.expression;
                elements.expressionInput.focus();
            });

            historyList.appendChild(div);
        });
    } catch (error) {
        historyList.innerHTML = '<div class="text-danger">Unable to load history.</div>';
        console.error(error);
    }
}

clearHistoryButton.addEventListener("click", async () => {
    await clearHistoryApi();

    await loadHistory();
});