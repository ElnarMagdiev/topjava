### curl запросы
Список еды:
<br/>
Rest-method: get
<br/>
curl -v http://localhost:8080/topjava/rest/meals
<br/><br/>

Получить еду 100003:
<br/>
Rest-method: get
<br/>
curl -v http://localhost:8080/topjava/rest/meals/100003
<br/><br/>

Удалить еду 100003:
<br/>
Rest-method: delete
<br/>
curl -X DELETE http://localhost:8080/topjava/rest/meals/100003
<br/><br/>

Создать еду:
<br/>
Rest-method: post
<br/>
curl -X POST http://localhost:8080/topjava/rest/meals -H 'content-type:application/json' -d '{"dateTime":"2021-12-01T11:30:01","description":"Новая еда","calories":"555"}'
<br/><br/>

Изменить еду 100004:
<br/>
Rest-method: put
<br/>
curl -X PUT http://localhost:8080/topjava/rest/meals/100004 -H 'content-type:application/json' -d '{ "id":"100004","dateTime":"2021-12-01T11:30:10","description":"Изменная еда","calories":"666"}'
<br/><br/>

Получить список еды на дату/время:
<br/>
Rest-method: get
<br/>
curl -s "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-01&startTime=00:01&endDate=2020-01-31&endTime=23:59"

