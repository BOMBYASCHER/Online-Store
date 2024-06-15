 document.getElementById("myButton").addEventListener("click", function() {
    fetch('https://example.com/data')
     .then(response => {
            if (!response.ok) {
                 throw new Error('Ошибка при запросе: ' + response.status);
                   }
              return response.json();
         })
                .then(data => {
                    console.log('Ответ от сервера:', data);
                })
                .catch(error => {
                    console.error('Ошибка:', error);
                });
        });

