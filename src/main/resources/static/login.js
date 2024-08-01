// let data  = [[${key_vk}]]
// console.log(data);

function authVk() {
    const uuid = '63248726347156725367128342341298731923';
    const appId = 51955334;
    const redirectUri = 'http://localhost/vk.auth';
    const redirect_state = 'hello';

    const query = `uuid=${uuid}&app_id=${appId}&response_type=silent_token&redirect_uri=${redirectUri}&redirect_state=${redirect_state}`;
    console.log("Hello")
    location.assign(`https://id.vk.com/auth?${query}`);
}

function logout() {
    let currentUrl = window.location.href;
    fetch('/logout1', {
            method: 'GET',
            credentials: 'same-origin'
        }
    )
        .then(response => {
            if (response.ok) {
                window.location.href = currentUrl;
            } else {
                console.error('logout  failed')
            }
        })
}

let form = document.getElementById('form');
// const form = document.getElementById('form');

// console.log(document.getElementById('uname'))
form.addEventListener('submit', function (event) {
    event.preventDefault();
    let formData = new FormData(this);
    const data = {};
    formData.forEach((value, key) => {
        data[key] = value;
    });
    console.log(data)
    fetch(`http://localhost/sign-in?username=${data['username']}&password=${data['password']}`, {
        method: 'POST'
    })
        .then(response => {
            if (response.ok) {
                window.location.href = '/';
            }
            else{
                // alert('dasda!');
                document.getElementById('error').style.display = 'block';
                // alert('Uncorrect data');
            }
        })
});
const btn_cancel = document.getElementById('cancel')
btn_cancel.addEventListener('click', function(){
    window.history.go(-1)
});