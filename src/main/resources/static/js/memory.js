/**
 * Stores the current answer in memory
 */
function memoryStore() {
    console.log("memoryStore called");

    const value = getLastAnswerAsNumber();

    if (appState.lastAnswer === null)
        return;

    appState.memoryValue = value;

    console.log("Memory:", appState.memoryValue);
}

/**
 * Recalls the stored memory value
 */
function memoryRecall() {
    console.log("memoryRecall called");

    if (appState.memoryValue === null)
        return null;

    return appState.memoryValue.toString();
}

/**
 * Clears calculator memory
 */
function memoryClear() {
    console.log("memoryClear called");

    console.log("Before clear:", appState.memoryValue);

    appState.memoryValue = null;

    console.log("After clear:", appState.memoryValue);
}

/**
 * Adds the current answer to memory
 */
function memoryAdd() {
    const value = getLastAnswerAsNumber();

    if (value === null)
        return;

    if (appState.memoryValue === null)
        appState.memoryValue = value;
    else
        appState.memoryValue += value;

    console.log("Memory:", appState.memoryValue);
}

/**
 * Subtracts the current answer from memory.
 */
function memorySubtract() {
    const value = getLastAnswerAsNumber();

    if (value === null)
        return;

    if (appState.memoryValue === null)
        appState.memoryValue = -value;
    else
        appState.memoryValue -= value;

    console.log("Memory:", appState.memoryValue);
}

function getLastAnswerAsNumber() {
    if (appState.lastAnswer == null || appState.lastAnswer === "")
        return null;

    const value = Number(appState.lastAnswer);

    return Number.isNaN(value) ? null : value;
}