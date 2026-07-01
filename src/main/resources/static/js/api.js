const API = {
    CALCULATE: "/api/calculate",
    HISTORY: "/api/history"
}

async function apiRequest(url, options = {}) {
    const response = await fetch(url, options);

    if (!response.ok) {
        throw new Error(`Request failed (${response.status})`)
    }

    return response;
}