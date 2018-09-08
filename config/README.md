GetAll - curl -v -X GET -H "Content-Type: application/json" http://localhost:8080/topjava/rest/meals
Get - curl -v -X GET -H "Content-Type: application/json" http://localhost:8080/topjava/rest/meals/100010
Create - curl -i -X POST -H "Content-Type:application/json" http://localhost:8080/topjava/rest/meals -d '{"dateTime":"2011-05-31T20:00:00","description":"https","calories":125}'
Delete - curl -v -X DELETE http://localhost:8080/topjava/rest/meals/100010
Update - curl -v -H "Content-Type:application/json" -X PUT http://localhost:8080/topjava/rest/meals/100005 -d '{"dateTime":"2011-05-30T20:00:00","description":"ht","calories":155}'
GetBetween - curl -v -X GET "http://localhost:8080/topjava/rest/meals/filter?startDate=2015-05-30&endDate=2015-05-30&startTime=10:00&endTime=12:00"