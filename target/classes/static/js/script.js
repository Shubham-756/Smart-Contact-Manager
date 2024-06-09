console.log("Script laoded")

let currentTheme = getTheme();
console.log(currentTheme);
changeTheme(currentTheme)
// Todo

function changeTheme() {

    // set to web page 
    document.querySelector('html').classList.add(currentTheme);

    // set a listener to change theme buttom
    const changeThemeButton = document.querySelector("#theme_change_button");
    changeThemeButton.querySelector('span').textContent = currentTheme == 'light'?'Dark':'Light';

    changeThemeButton.addEventListener("click", (event) => {
        const oldTheme = currentTheme;
        if (currentTheme == "dark") {
            currentTheme = "light";
        } 
        else {
            currentTheme="dark"
        }
        // localstroage me update krenge 
        setTheme(currentTheme);
        document.querySelector("html").classList.remove(oldTheme);
        document.querySelector("html").classList.add(currentTheme);
        changeThemeButton.querySelector('span').textContent = currentTheme == 'light'?'Dark':'Light';
    });

}

// set the theme 

function setTheme(theme) {
    localStorage.setItem("theme", theme);
}

// get the theme

function getTheme() {
    let theme = localStorage.getItem("theme");
    if (theme) return theme;
    else return "light";
}