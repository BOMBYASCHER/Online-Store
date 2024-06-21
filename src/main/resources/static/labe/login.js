const signInBtn = document.querySelector('.signin-btn');
const signUpBtn = document.querySelector('.signup-btn');
const formBox = document.querySelector('.form-box');
const listProductHTML = document.querySelector('.reg');
const tProductHTML = document.querySelector('vxod')

signUpBtn.addEventListener('click', function () {
  formBox.classList.add('active');
});

signInBtn.addEventListener('click', function () {
  formBox.classList.remove('active');
});
listProductHTML.addEventListener('click', (event) => { 
  function sendJSON() {
   
    let email = document.querySelector('#email');
    let password = document.querySelector('#password');
   
    let result = document.querySelector('.result');
   
    let xhr = new XMLHttpRequest();
    
    let url = "api/login ";
    
    xhr.open("POST", url, true);
   
    xhr.setRequestHeader("Content-Type", "application/json");
   
    xhr.onreadystatechange = function () {
    
      if (xhr.readyState === 4 && xhr.status === 200) {
        
        result.innerHTML = this.responseText;
      }
    };
    
    var data = JSON.stringify({ "email": email.value, "password": password.value,});
   
    xhr.send(data);
    then((response) => {
      console.log(response);
    });
  }
});
tProductHTML.addEventListener('click', (event) => { 
  function sendJSON() {
    // с помощью jQuery обращаемся к элементам на странице по их именам
    let password = document.querySelector('#password');
    let Email = document.querySelector('#Email');
    let login = document.querySelector('#login');
    let conpassword = document.querySelector('#conpassword');
    // а вот сюда мы поместим ответ от сервера
    let result = document.querySelector('.result');
    // создаём новый экземпляр запроса XHR
    let xhr = new XMLHttpRequest();
    // адрес, куда мы отправим нашу JSON-строку
    let url = "api/registry";
    // открываем соединение
    xhr.open("POST", url, true);
    // устанавливаем заголовок — выбираем тип контента, который отправится на сервер, в нашем случае мы явно пишем, что это JSON
    xhr.setRequestHeader("Content-Type", "application/json");
    // когда придёт ответ на наше обращение к серверу, мы его обработаем здесь
    xhr.onreadystatechange = function () {
      // если запрос принят и сервер ответил, что всё в порядке
      if (xhr.readyState === 4 && xhr.status === 200) {
        // выводим то, что ответил нам сервер — так мы убедимся, что данные он получил правильно
        result.innerHTML = this.responseText;
      }
    };
    // преобразуем наши данные JSON в строку
    var data = JSON.stringify({"conpassword": conpassword.value, "password": password.value,"Email": Email.value, "login": login.value, });
    // когда всё готово, отправляем JSON на сервер
    xhr.send(data);
    then((response) => {
      console.log(response);
    });
  }
});