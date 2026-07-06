function setResult(text) {
    elements.resultDisplay.textContent = text;
}

function setLoading(isLoading) {
    equalsButton.disabled = isLoading;
}

function toggleAngleMode() {
    if (appState.angleMode === "DEG")
        appState.angleMode = "RAD";
    else
        appState.angleMode = "DEG";

    elements.angleModeButton.textContent = appState.angleMode;

    console.log("Angle mode: ", appState.angleMode);
}