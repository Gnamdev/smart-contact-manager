console.log("script loaded");

document.addEventListener("DOMContentLoaded", () => {
    let currentTheme = getData(); // Get the theme from localStorage or default to light
    console.log("Current theme:", currentTheme);

    // Apply the initial theme on page load
    changeTheme(currentTheme);

    // Add the click event listener once when the DOM is fully loaded
    const themeChangeButton = document.querySelector("#theme_change");
    themeChangeButton.addEventListener("click", handleThemeChange);
});

function changeTheme(theme) {
    const htmlElement = document.querySelector("html");
    const themeChangeButton = document.querySelector("#theme_change");

    // Remove any existing theme classes ('light', 'dark')
    htmlElement.classList.remove("light", "dark");
    htmlElement.classList.add(theme); // Apply the new theme

    // Set the correct image based on the theme
    themeChangeButton.src = theme === "light" ? "/img/lamp.png" : "/img/dark-mode.png";

    // Save the theme to localStorage
    setData(theme);
}

function handleThemeChange() {
    const htmlElement = document.querySelector("html");
    let currentTheme = htmlElement.classList.contains("dark") ? "dark" : "light";
    let newTheme = currentTheme === "dark" ? "light" : "dark"; // Toggle the theme

    // Apply the new theme
    changeTheme(newTheme);
}

// Function to store the theme in localStorage
function setData(theme) {
    localStorage.setItem("theme", theme);
}

// Function to get the theme from localStorage
function getData() {
    let data = localStorage.getItem("theme");
    return data ? data : "light"; // Default to light if no theme is saved
}
