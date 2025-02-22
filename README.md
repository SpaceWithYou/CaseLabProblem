# CaseLabProblem
Решение тестового задания для Case Lab по Java
# Пояснение к используемым технологиям
- Для работы с БД используется репозитории Spring Data JPA
- Сборка приложения в контейнере производится с помощью Maven
- Для тестов используется in-memory БД H2 и JUnit
- Для удобства используется Lombok (чтобы не писать геттеры и сеттеры)
- Spring MVC (для реализации REST контроллера)
- Для развертывания используется Docker Compose (отдельно Postgres и отдельно Spring)
- Для примеров использовался Postman
# Пояснение к решению
Все методы и классы имеют комментарии для понимания.<br>
На контроллер подаются HTTP-запросы GET (получение файла/файлов) и 
POST (загрузка файла), в теле запроса передаем/получаем сущности типа FileEntity
в JSON формате (см. далее), эти сущности обрабатываются Spring репозиторием.<br>
Реализованы дополнительные требования:
- Микросервис обернут в Docker
- Написаны Unit тесты (есть ли в этом смысл, т.к. происходит чисто проверка save/find методов 
 репозитория, которые генерируются сами, а сущность обладает только полями и геттерами/сеттерами? 
Нужно ли было писать Mock тесты для контроллера? Вопрос открытый) )
- Добавлен метод получения всех файлов с пагинацией и сортировкой (сортировка с помощью PagingAndSortingRepository)
# Как запустить?
**Запустить compose.yaml файл.** Должно быть два контейнера с БД и приложением (+ Должна создастся папка DockerData с томом для БД). 
(Если не собирается надо перед запуском compose.yaml собрать jar файл с помощью Maven (package))
# Примеры использования API
## Загрузка файла
- Body: (JSON)
`
  {
  "title": "123",
  "creationDate": "2024-07-21T16:23:00+03:00",
  "description": "",
  //Id генерируется автоматически, его можно не указывать
  "content": "S29sYmFzIC0gcGlkb3Jhcw0K"
  }
`
- Сам запрос `http://localhost/file` (POST)
- Возвращаемое значение (UUID): `"06bdc8a3-db79-4af0-a656-9e11b376710e"`
## Получение файла по id (UUID)
- Сам запрос `http://localhost/file/ea1bd284-30c3-407d-9fe6-395532901211` (GET)
- Возвращаемое значение<br> 
`
  {
  "title": "File11",
  "creationDate": "2024-07-21T13:23:00Z",
  "description": "",
  "id": "ea1bd284-30c3-407d-9fe6-395532901211",
  "content": "XHg2NjY5NmM2NTMxMGQwYQ=="
  }
`
## Пагинация и сортировка
- isAscending - boolean тип, отвечает за сортировку по возрастанию/убыванию (true/false).
- pageSize - int, размер страницы (сколько вывести элементов).
- pageNumber - int, какая по счету (начиная с 0) страница будет выведена.
- Сам запрос `http://localhost/file?isAscending=true&pageNumber=1&pageSize=3`
- Возвращаемое значение<br> 
`[
{
"title": "File56",
"creationDate": "2024-07-21T14:23:01Z",
"description": "",
"id": "00700f2a-9099-45f4-b8c9-778fe30a6d27",
"content": "XHg2NjY5NmM2NTMzMGQwYQ=="
},
{
"title": "File4",
"creationDate": "2024-07-21T14:30:00Z",
"description": "",
"id": "17d48625-4687-4ac6-9b08-4bfd758abf4d",
"content": "XHg2NjY5NmM2NTMyMGQwYQ=="
},
{
"title": "File31",
"creationDate": "2024-07-21T15:23:00Z",
"description": "",
"id": "7686d10c-1840-4424-8357-cc6ca3a349e3",
"content": "XHg2NjY5NmM2NTM1MGQwYQ=="
}
]`
- POSTMAN https://elements.getpostman.com/redirect?entityId=31131036-8f912e1b-9e4f-4089-ab69-a0b85d778eb0&entityType=collection