## Докера нет и тесты не полность покрыты
В сообщении дедлайн не более недели, в форме просили до 5 дней. В неделю уложился +/- пару часов.

```sh
swagger
localhost:8080/swagger-ui/index.html#/
```
```sh
localhost:8080/register -register
localhost:8080/auth -token
localhost:8080/v1/task/** -required jwt
```
