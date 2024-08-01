let url = 'http://localhost';
const form = document.getElementById('form');

form.addEventListener('submit', function (event) {
    event.preventDefault();
    const dataForm = new FormData(this);

    let data = {};
    dataForm.forEach((value, key) => {
        data[key] = value;
    });
    const psw1 = document.getElementById('psw1')
    const psw2 = document.getElementById('psw2')
    if (psw1.value !== psw2.value) {
        psw1.style.borderColor = "red";
        psw1.style.borderWidth = '2px';
        psw2.style.borderColor = "red";
        psw2.style.borderWidth = '2px';
        return;
    }

    let status = '';
    fetch(url + `/sign-up?username=${data['username']}&password=${data['password1']}&name=${data['name']}&surname=${data['surname']}&email=${data['email']}`, {
        method: 'POST'
    })
        .then(response => {
            console.log(response.status)
            status = response.status
            return response.text()
        })
        .then((data) => {
            if (status >= 400) {
                data = JSON.parse(data)
                // console.log(data)
                showError(data['error_msg']);
            } else {
                window.location.replace("/login");
            }
        })
        .catch((error) => {
            showError('Some Error :(')
        })
});
const btn_cancel = document.getElementById('cancel')
btn_cancel.addEventListener('click', function () {
    window.history.go(-1)
})

function showError(message) {
    console.log(message)
    const error_text = document.getElementById('error');
    error_text.innerHTML = message;
    error_text.style.display = 'block';
}