
console.log("script loaded");

let currentTheme = getData();

console.log(currentTheme);

changeTheme(currentTheme);

function changeTheme(theme) {
    // Remove existing theme classes (if any)
    document.querySelector('html').classList.remove('light', 'dark');

    // Apply the current theme to the web page
    document.querySelector('html').classList.add(theme);

    // Update the theme change button image
    let changeThemButtonListener = document.querySelector("#theme_change");
    changeThemButtonListener.src = theme === 'light' ? '/img/lamp.png' : '/img/moon.png';

    // Set the event listener for the theme change button
    changeThemButtonListener.addEventListener('click', (event) => {
        console.log("button click");
        console.log(event.type);

        // Remove the current theme
        document.querySelector('html').classList.remove(theme);

        // Toggle the theme
        theme = theme === "dark" ? "light" : "dark";

        // Apply the new theme
        document.querySelector('html').classList.add(theme);

        // Update the button image
        changeThemButtonListener.src = theme === 'light' ? '/img/lamp.png' : '/img/moon.png';

        // Update the local storage
        setData(theme);
    });
}

// Set data to local storage
function setData(theme) {
    localStorage.setItem("theme", theme);
}

// Fetch data from local storage
function getData() {
    let data = localStorage.getItem("theme");
    return data ? data : "light";
}

// Ensure the theme change button has the correct image initially
document.querySelector("#theme_change").src = currentTheme === 'light' ? '/img/lamp.png' : '/img/moon.png';
