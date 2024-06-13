 document.getElementById("myButton").addEventListener("click", function() {
    fetch('https://example.com/data') // Вместо 'https://example.com/data' укажите URL вашего сервера
     .then(response => {
            if (!response.ok) {
                 throw new Error('Ошибка при запросе: ' + response.status);
                   }
              return response.json();
         })
                .then(data => {
                    console.log('Ответ от сервера:', data);
                    // Здесь можно обработать полученные данные
                })
                .catch(error => {
                    console.error('Ошибка:', error);
                });
        });

