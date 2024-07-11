console.log("HELLO");
document.getElementById('workoutForm').addEventListener("submit", function (event) {
    event.preventDefault();
    // let formData = document.getElementById('workoutForm')
    // let json = {};
    // formData.forEach((value, key) => json[key] = value);
    const form = document.getElementById('workoutForm');

// Получаем данные формы
    const formData = new FormData(form);
    console.log(JSON.stringify(Object.fromEntries(formData.entries())))
    let answer;
    fetch('http://localhost/v1/training/v1/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(Object.fromEntries(formData.entries()))
    })
        .then(response => {
            answer = response.status === 200;
            console.log(response)
            return response.text();
        })
        .then(data => {
            console.log(data)
        })
        .catch(error => console.error(error));
    if (!answer) {
        // window.location.replace('/');
    } else {
        alert('Some Error! :(');
    }
});
// document.getElementById('workoutForm').addEventListener('submit', function (event) {
//     event.preventDefault();
//
//     const workoutType = document.getElementById('workoutType').value;
//     const distance = document.getElementById('distance').value;
//     const duration = document.getElementById('duration').value;
//     const heartRate = document.getElementById('heartRate').value;
//     // const comments = document.getElementById('comments').value;
//
//     console.log('Тип тренировки:', workoutType);
//     console.log('Дистанция:', distance);
//     console.log('Время:', duration);
//     console.log('Средний пульс:', heartRate);
//     // console.log('Комментарии:', comments);
//
//     alert('Данные тренировки загружены успешно!');
// });

let navToggle = document.querySelector(".nav__toggle");
let navWrapper = document.querySelector(".nav__wrapper");

navToggle.addEventListener("click", function () {
    if (navWrapper.classList.contains("active")) {
        this.setAttribute("aria-expanded", "false");
        this.setAttribute("aria-label", "menu");
        navWrapper.classList.remove("active");
    } else {
        navWrapper.classList.add("active");
        this.setAttribute("aria-label", "close menu");
        this.setAttribute("aria-expanded", "true");
        searchForm.classList.remove("active");
        searchToggle.classList.remove("active");
    }
});

let searchToggle = document.querySelector(".search__toggle");
let searchForm = document.querySelector(".search__form");

searchToggle.addEventListener("click", showSearch);

function showSearch() {
    searchForm.classList.toggle("active");
    searchToggle.classList.toggle("active");

    navToggle.setAttribute("aria-expanded", "false");
    navToggle.setAttribute("aria-label", "menu");
    navWrapper.classList.remove("active");

    if (searchToggle.classList.contains("active")) {
        searchToggle.setAttribute("aria-label", "Close search");
    } else {
        searchToggle.setAttribute("aria-label", "Open search");
    }
}

