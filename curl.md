### curl запросы
Список еды:
Rest-method: get
curl -v http://localhost:8080/topjava/rest/meals

Получить еду 100003:
Rest-method: get
curl -v http://localhost:8080/topjava/rest/meals/100003

Удалить еду 100003:
Rest-method: delete
curl -X DELETE http://localhost:8080/topjava/rest/meals/100003

Создать еду:
Rest-method: post
curl -X POST http://localhost:8080/topjava/rest/meals -H 'content-type:application/json' -d '{"dateTime":"2021-12-01T11:30:01","description":"Новая еда","calories":"555"}'

Изменить еду 100004:
Rest-method: put
curl -X PUT http://localhost:8080/topjava/rest/meals/100004 -H 'content-type:application/json' -d '{ "id":"100004","dateTime":"2021-12-01T11:30:10","description":"Изменная еда","calories":"666"}'

Получить список еды на дату/время:
Rest-method: get
curl -s "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-01&startTime=00:01&endDate=2020-01-31&endTime=23:59"
