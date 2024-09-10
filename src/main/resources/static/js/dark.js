// document.addEventListener('DOMContentLoaded', () => {
//     const homePage = document.getElementById('homePage');
  
//     if (homePage) {
//       let currentTheme = getData();
//       changeTheme(currentTheme);
      
//       function changeTheme(theme) {
//         // Remove existing theme classes (if any)
//         document.querySelector('html').classList.remove('light', 'dark');
  
//         // Apply the current theme to the home page
//         homePage.classList.remove('light', 'dark');
//         homePage.classList.add(theme);
  
//         // Update the theme change button image
//         let changeThemeButtonListener = document.querySelector("#theme_change");
//         changeThemeButtonListener.src = theme === 'light' ? '/img/lamp.png' : '/img/dark-mode.png';
  
//         // Set the event listener for the theme change button
//         changeThemeButtonListener.addEventListener('click', () => {
//           theme = theme === "dark" ? "light" : "dark";
//           homePage.classList.remove('light', 'dark');
//           homePage.classList.add(theme);
//           changeThemeButtonListener.src = theme === 'light' ? '/img/lamp.png' : '/img/moon.png';
//           setData(theme);
//         });
//       }
  
//       // Set data to local storage
//       function setData(theme) {
//         localStorage.setItem("theme", theme);
//       }
  
//       // Fetch data from local storage
//       function getData() {
//         let data = localStorage.getItem("theme");
//         return data ? data : "light";
//       }
  
//       // Ensure the theme change button has the correct image initially
//       document.querySelector("#theme_change").src = currentTheme === 'light' ? '/img/lamp.png' : '/img/moon.png';
//     }
//   });
  