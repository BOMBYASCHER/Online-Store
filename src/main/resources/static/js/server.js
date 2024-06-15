 document.getElementById("myButton").addEventListener("click", function() {
    fetch('https://online-store-fmqm.onrender.com/api/products') 
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

