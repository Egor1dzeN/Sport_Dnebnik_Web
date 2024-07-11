let my_arr = []
getAllTraining()
function getAllTraining(){
    fetch("http://localhost/v1/training/v1/get_all", {
        method:'GET'
    })
        .then(response => response.text())
        .then(data => view(JSON.parse(data)));
}
console.log(my_arr)
const options = {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: 'numeric',
    minute: 'numeric',
    second: 'numeric'
};
function view(workoutData){
    console.log(workoutData)
    const workoutsList = document.getElementById('container');
    workoutsList.innerHTML = '';
    workoutData.forEach(workout =>{
        const workoutCard = document.createElement('div');
        workoutCard.innerHTML = `<div class="workout-card">
        <strong style="font-size: 30px;">${workout['user']['username']}</strong>
        <h2>Тренировка: ${workout['typeTraining']}</h2>
        <div class="workout-info"> 
            <div>Дистанция: ${workout['distance']} км</div>
            <div>Время начала: ${new Date(workout['start_time']).toLocaleString('ru-RU', options)} </div>
            <div>Время: ${workout['duration']} минут</div>
            <div>Пульс:  ${workout['heartRate']} уд/мин</div>
        </div>
        
    </div>`

        workoutsList.appendChild(workoutCard);
    })
}


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