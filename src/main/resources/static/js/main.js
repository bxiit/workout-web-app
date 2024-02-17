document.getElementById("regging").addEventListener("submit", function(event) {
    console.log("JS FILE")
    // Предотвращаем стандартное поведение формы (отправку по умолчанию)
    event.preventDefault();

    // Получаем данные формы
    let formData = new FormData(event.target);

    // Пример: отправляем данные формы на сервер с использованием Fetch API
    fetch("/api/register/customer", {
        method: "POST",
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            // Обработка ответа от сервера
            console.log(data);
        })
        .catch(error => {
            // Обработка ошибок
            console.error('Error:', error);
        });
});