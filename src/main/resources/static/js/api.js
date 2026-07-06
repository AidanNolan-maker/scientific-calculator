const API = {
    CALCULATE: "/api/calculate",
    HISTORY: "/api/history"
}

async function apiRequest(url, options = {}) {
    const response = await fetch(url, options);

    if (!response.ok) {
        const message = await response.text();
        throw new Error(`Request failed (${response.status}): ${message}`);
    }

    return response;
}

async function calculate(expression) {
    const response = await apiRequest(API.CALCULATE, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            expression: expression,
            angleMode: appState.angleMode
        })
    });

    return await response.json();
}

async function getHistory() {
    const response = await apiRequest(API.HISTORY);

    return await response.json();
}

async function clearHistoryApi() {
    await apiRequest(API.HISTORY, {
        method: "DELETE"
    });
}