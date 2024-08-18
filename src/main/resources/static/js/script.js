console.log("script loaded");

let currentTheme = getData();
console.log(currentTheme);

changeTheme(currentTheme);

function changeTheme(theme) {
    const htmlElement = document.querySelector('html');
    const themeChangeButton = document.querySelector("#theme_change");

    htmlElement.classList.remove('light', 'dark');
    htmlElement.classList.add(theme);

    themeChangeButton.src = theme === 'light' ? '/img/lamp.png' : '/img/dark-mode.png';

    themeChangeButton.removeEventListener('click', handleThemeChange);
    themeChangeButton.addEventListener('click', handleThemeChange);
}

function handleThemeChange() {
    const htmlElement = document.querySelector('html');
    const themeChangeButton = document.querySelector("#theme_change");

    let currentTheme = htmlElement.classList.contains('dark') ? 'dark' : 'light';
    let newTheme = currentTheme === "dark" ? "light" : "dark";

    htmlElement.classList.remove(currentTheme);
    htmlElement.classList.add(newTheme);

    themeChangeButton.src = newTheme === 'light' ? '/img/lamp.png' : '/img/dark-mode.png';
    setData(newTheme);
}

function setData(theme) {
    localStorage.setItem("theme", theme);
}

function getData() {
    let data = localStorage.getItem("theme");
    return data ? data : "light";
}

document.querySelector("#theme_change").src = currentTheme === 'light' ? '/img/lamp.png' : '/img/dark-mode.png';
