const signUpFrom = document.getElementById('sign-up-form');
signUpFrom.addEventListener('submit', (e) => {
  e.preventDefault();
  const data = new FormData(signUpFrom);
  const json = JSON.stringify(Object.fromEntries(data));
  fetch('/api/registry', {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: json,
  })
  .then((response) => {
    console.log(response);
  })
  .catch((error) => {
    console.error(error);
  });;
});
