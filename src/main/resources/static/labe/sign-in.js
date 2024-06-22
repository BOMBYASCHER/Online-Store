const signInFrom = document.getElementById('sign-in-form');
signInFrom.addEventListener('submit', (e) => {
  e.preventDefault();
  const formData = new FormData(signInFrom);
  const json = JSON.stringify(Object.fromEntries(formData));
  fetch('/api/login', {
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
