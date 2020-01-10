### RestaurantVoting
Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

The task is:

Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users
Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
Menu changes each day (admins do the updates)
Users can vote on which restaurant they want to have lunch at
Only one vote counted per user
If user votes again the same day:
If it is before 11:00 we asume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed
Each restaurant provides new menu each day.

As a result, provide a link to github repository.

It should contain the code and README.md with API documentation and curl commands to get data for voting and vote.

### API documentation and curl commands:

1. Administrator actions with restaurant:

- See all restaurants that have a database http://localhost:8080/rest/admin/restaurants

curl -X GET \
  http://localhost:8080/rest/admin/restaurants \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 19727bf8-3375-d9a4-c505-6ba213179ad2'
  
- Choose a specific restaurant by its id http://localhost:8080/rest/admin/restaurants/{id}

curl -X GET \
  http://localhost:8080/rest/admin/restaurants/10 \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: cb9e620b-8dde-6ab8-c2ed-220376bfc153'
  
- Choose a specific restaurant by its name http://localhost:8080/rest/admin/restaurants/byName

curl -X GET \
  'http://localhost:8080/rest/admin/restaurants/byName?name=La%20Creperie' \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: a7634c68-7203-9d3e-2b82-10ddb873a1d3'
  
- Remove the restaurant from the database by its id http://localhost:8080/rest/admin/restaurants/{id}

curl -X DELETE \
  http://localhost:8080/rest/admin/restaurants/8 \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 304761d0-ee1b-b665-3bb0-d83c5c6f3320'
  
- Add a new restaurant http://localhost:8080/rest/admin/restaurants
We submit a restaurant object to the request body in JSON format: 

curl -X POST \
  http://localhost:8080/rest/admin/restaurants \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: bad43493-d70e-2b68-9c82-3c72b0c4027e' \
  -d '{
	"name": "Підпільний кіндрат"
}'

- The administrator can also edit the restaurant selected by id http://localhost:8080/rest/admin/restaurants/{id}
We submit a restaurant object to the request body in JSON format: 

curl -X PUT \
  http://localhost:8080/rest/admin/restaurants/5 \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 6b906e1b-19b6-cacf-9878-f78394966faa' \
  -d '{
	"name": "Гасова лямпа"
}'

2. Food Admin Actions:

- See all the food available at the restaurant today. Choosing a restaurant by its id.
http://localhost:8080/rest/admin/dishes/restaurant/{restaurantId}

curl -X GET \
  http://localhost:8080/rest/admin/dishes/restaurant/10 \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 33a602af-0909-e5f0-6d18-d3890ea5b83b'
  
- Find food by her id http://localhost:8080/rest/admin/dishes/{id}

curl -X GET \
  http://localhost:8080/rest/admin/dishes/13 \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: cda254ad-73f9-396e-a0c7-7e425a7c2bc5'
  
- Find food by its name http://localhost:8080/rest/admin/dishes/byName

curl -X GET \
  'http://localhost:8080/rest/admin/dishes/byName?name=%D0%A1%D0%B0%D0%BB%D0%B0%D1%82%20%D0%9D%D0%BE%D0%B2%D0%BE%D0%BB%D1%8C%D0%B2%D1%96%D0%B2%D1%81%D1%8C%D0%BA%D0%B8%D0%B9' \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 6971dbc2-c9b5-c466-3ac4-cee9bb6d00b9'
  
- Find a list of food at a specified price

curl -X GET \
  'http://localhost:8080/rest/admin/dishes/byPrice?price=125' \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: abda364a-4df0-0b63-130b-23fc7c9b9fe9'
  
- Remove food from the base by its id

curl -X DELETE \
  http://localhost:8080/rest/admin/dishes/25 \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 4dc11e65-d5d1-4c14-22c0-cfeb9551d14e'
  

- Add food to the database. The food is added from the id of the restaurant where it is cooked.
http://localhost:8080/rest/admin/dishes/{restaurantId}

curl -X POST \
  http://localhost:8080/rest/admin/dishes/9 \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: f4270860-7db7-da1f-0b4d-b418c996f01a' \
  -d '{
    "name": "Борщ",
    "price": 60,
    "createdDate": "2020-01-09 19:03"
}'

- The administrator can also edit the food selected by id 
http://localhost:8080/rest/admin/dishes/{id}?restaurantId={restaurantId}

curl -X PUT \
  'http://localhost:8080/rest/admin/dishes/20?restaurantId=5' \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: dc389df0-ae75-6c7d-d08e-ba89728b01e0' \
  -d '{
    "name": "Грибна юшка з вушками",
    "price": 99.55,
    "createdDate": "2020-01-10 16:00"
}'

3. Admin actions with logged in users

- View all users http://localhost:8080/rest/admin/users

curl -X GET \
  http://localhost:8080/rest/admin/users \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 6c8ad533-15b8-ceb5-43d6-abe3defb06fb'

- Find a user by his id http://localhost:8080/rest/admin/users/{id}

curl -X GET \
  http://localhost:8080/rest/admin/users/7 \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 543184a1-8620-1796-8333-bb01ef8ee49e'
  
- Find a user by his name http://localhost:8080/rest/admin/users/byName

curl -X GET \
  'http://localhost:8080/rest/admin/users/byName?name=Victor' \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 9209bca7-2e6c-48aa-2c5b-1df5da17102b'
  
- Find the user by his email http://localhost:8080/rest/admin/users/byEmail

curl -X GET \
  'http://localhost:8080/rest/admin/users/byEmail?email=harry.maguire%40gmail.com' \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 54bc0e90-a3a5-6551-0b72-8ff2a88b3936'
  
- Remove user by his id http://localhost:8080/rest/admin/users/{id}

curl -X DELETE \
  http://localhost:8080/rest/admin/users/5 \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 8c569e1a-017a-a1c4-1923-816995ee4ffa'
  
- Add a new user http://localhost:8080/rest/admin/users

curl -X POST \
  http://localhost:8080/rest/admin/users \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: af6a3fef-99d3-6d79-3676-6777570f76da' \
  -d '{
    "name": "Іван",
    "email": "ivan@gmail.com",
    "password": "qwerty",
    "registered": "2020-01-10 19:48",
    "roles": [
        "ROLE_USER"
    ]
}'

- Edit user id data http://localhost:8080/rest/admin/users/{id}

curl -X PUT \
  http://localhost:8080/rest/admin/users/4 \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 1e7cf700-f30f-4a54-3ef4-6e8cdee88fb1' \
  -d '{
    "name": "Pedro",
    "email": "pedro@gmail.com",
    "password": "ytrewq",
    "registered": "2020-01-10 19:48",
    "roles": [
        "ROLE_ADMIN"
    ]
}'

4. Admin actions with voices

- Find a voice by id http://localhost:8080/rest/admin/vote/{id}

curl -X GET \
  http://localhost:8080/rest/admin/vote/4 \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: b08f4b5f-8e73-bba6-d91f-940578bc39ae'
  
- Find all the voices of an individual user http://localhost:8080/rest/admin/vote/user/{id}

curl -X GET \
  http://localhost:8080/rest/admin/vote/user/2 \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 4d3b805f-c8f6-bd2e-d5cc-99cc824538d2'
  
- Find all votes for the selected restaurant http://localhost:8080/rest/admin/vote/restaurant/{restaurantId}

curl -X GET \
  http://localhost:8080/rest/admin/vote/restaurant/5 \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: e97443ae-aef7-d247-10e0-f38b65e51047'
  
- Find all votes for the selected date http://localhost:8080/rest/admin/vote/byDate

curl -X GET \
  'http://localhost:8080/rest/admin/vote/byDate?date=2019-12-20' \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 3a8f34e1-e23e-5aae-72d6-2a35d61ac812'
  
- Find all the votes for today http://localhost:8080/rest/admin/vote/today

curl -X GET \
  http://localhost:8080/rest/admin/vote/today \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 81e5289e-a096-c8ec-c3c6-649c899e4139'
  
- Find all votes for today within the specified dates http://localhost:8080/rest/admin/vote/filter

curl -X GET \
  'http://localhost:8080/rest/admin/vote/filter?startDate=2019-12-20&endDate=2019-12-22' \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: b688e084-d414-35c8-6bf2-722dc4f04dac'
  
- Find all votes for today within the specified dates and selected user id
http://localhost:8080/rest/admin/vote/filterId

curl -X GET \
  'http://localhost:8080/rest/admin/vote/filterId?startDate=2019-12-20&endDate=2019-12-22&userId=2' \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 603eb35c-fdf7-b891-95a1-740f1f222cfe'
  
- Remove voice found by id http://localhost:8080/rest/admin/vote/{id}

curl -X DELETE \
  http://localhost:8080/rest/admin/vote/2 \
  -H 'authorization: Basic ZGF2aWRAZ21haWwuY29tOjEyMzQ1Ng==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 7c4ed53b-6581-3821-a2c1-86338846caae'
  
5. User actions in the app

- View all restaurants available to vote http://localhost:8080/rest/users/restaurants

curl -X GET \
  http://localhost:8080/rest/users/restaurants \
  -H 'authorization: Basic bGVlLmdyYW50QGdtYWlsLmNvbTo2NTQzMjE=' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 6ad69b5e-a0d1-60f1-f3b6-e9cf43b171a7'
  
- View the menu for today at a restaurant of your choice http://localhost:8080/rest/users/menu/{name}

curl -X GET \
  http://localhost:8080/rest/users/menu/Epicure \
  -H 'authorization: Basic bGVlLmdyYW50QGdtYWlsLmNvbTo2NTQzMjE=' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: be06cfc2-ec89-ac73-2035-bca2cdfa387d'
  
- Vote for your chosen restaurant http://localhost:8080/rest/users/voting

curl -X POST \
  'http://localhost:8080/rest/users/voting?restaurantName=Pianovins' \
  -H 'authorization: Basic bGVlLmdyYW50QGdtYWlsLmNvbTo2NTQzMjE=' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: ea6d08f7-6745-22bd-1955-d321cd35b7b1'
  
- An unregistered user can register http://localhost:8080/rest/users/register
  
curl -X POST \
  http://localhost:8080/rest/users/register \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 21024a12-8639-fa08-1055-e20876710661' \
  -d '{
	"name":"Roman",
	"email":"pobihushka@gmail.com",
	"password":"romashka"
}'

- Registered users can edit their profile http://localhost:8080/rest/users

curl -X PUT \
  http://localhost:8080/rest/users \
  -H 'authorization: Basic cG9iaWh1c2hrYUBnbWFpbC5jb206cm9tYXNoa2E=' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: f46c27d7-200e-cb5f-c39d-7f482671ee22' \
  -d '{
	"name":"Roman",
	"email":"pobihushka.roman@gmail.com",
	"password":"romashka"
}'






 
  








