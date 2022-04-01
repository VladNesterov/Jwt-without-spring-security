
Для запуска проекта необходимо настроить application.properties

Порт http://localhost:8300

Проверка приложения, открываешь postman, прописываешь запросы по следующему паттерну

Post http://localhost:8300/login
           {
            "name":"12@mail.ru",
            "password":"12345"
            }
имена уникальные если ввести name и неправильный пароль, выдаст ошибку
при усешном логине или созлании пользовател получаем токен
           {
           token: "токен"
           }
в качесте секрета используется унальный uuid присвоенный пользователю при его создании в ДБ


Post http://localhost:8300/save/message
           {
           "name":"12@mail.ru",
           "message":"привет"
           }
Headers
Key     Value
Bearer eyJhbGciOiJIUzUxMiJ9.eyJuYW1lOiI6IjEyMzEyMzEiLCJleHAiOjE2NDg3NjkwOTZ9.Y860DZ__d5CRSd7l0Ub_3nwG5JGy29t10kQ1qb9dUoVX3amK_u5Q70oKuNFOqei_BO_RBrA-LIH_WrduJrMg6w
При успешной проверке Токена сообщение загрузится в базу


Post http://localhost:8300/message
           {
           "name":"12@mail.ru",
           "message":"history 10"
           }
Headers
Key     Value
Bearer eyJhbGciOiJIUzUxMiJ9.eyJuYW1lOiI6IjEyMzEyMzEiLCJleHAiOjE2NDg3NjkwOTZ9.Y860DZ__d5CRSd7l0Ub_3nwG5JGy29t10kQ1qb9dUoVX3amK_u5Q70oKuNFOqei_BO_RBrA-LIH_WrduJrMg6w
На данный момент для получений последних записей нам необходимо, чтобы 
                message имел следующую структуру - "message":"history 10".
выдаст послдение 10 сообщений ( можно вписат любое целое число)