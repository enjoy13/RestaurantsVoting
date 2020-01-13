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
  
- Remove the restaurant from the database by its id 

`curl -s -X DELETE http://localhost:8080/rest/admin/restaurants/8 --user admin@gmail.com:admin`
  
- Add a new restaurant. We submit a restaurant object to the request body in JSON format: 

`curl -s -X POST -d '{"name":"NewRestaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/restaurants --user admin@gmail.com:admin`

- The administrator can also edit the restaurant selected by id http://localhost:8080/rest/admin/restaurants/{id}
We submit a restaurant object to the request body in JSON format: 

`curl -s -X PUT -d '{"name":"UpdateRestaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/restaurants/5 --user admin@gmail.com:admin`

2. Food Admin Actions:

- See all the food available at the restaurant today. Choosing a restaurant by its id.

`curl -s http://localhost:8080/rest/admin/dishes/restaurant/10 --user admin@gmail.com:admin`

- Find food by her id 

`curl -s http://localhost:8080/rest/admin/dishes/13 --user admin@gmail.com:admin`
  
- Find food by its name 

`curl -s http://localhost:8080/rest/admin/dishes/byName?name=HotDog --user admin@gmail.com:admin`

- Find a list of food at a specified price

`curl -s http://localhost:8080/rest/admin/dishes/byPrice?price=125 --user admin@gmail.com:admin`

- Remove food from the base by its id

`curl -s -X DELETE http://localhost:8080/rest/admin/dishes/25 --user admin@gmail.com:admin`

- Add food to the database. The food is added from the id of the restaurant where it is cooked.

`curl -s -X POST -d '{"name":"NewDish","price":60,"createdDate":"2020-01-09 19:05"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/dishes/9 --user admin@gmail.com:admin`

- The administrator can also edit the food selected by id 

`curl -s -X PUT -d '{"name":"UpdateDish","price":60,"createdDate":"2020-01-09 19:05"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/dishes/20?restaurantId=5 --user admin@gmail.com:admin`

3. Admin actions with logged in users

- View all users

`curl -s http://localhost:8080/rest/admin/users --user admin@gmail.com:admin`

- Find a user by his id 

`curl -s http://localhost:8080/rest/admin/users/7 --user admin@gmail.com:admin`

- Find a user by his name 

`curl -s http://localhost:8080/rest/admin/users/byName?name=Victor --user admin@gmail.com:admin`

- Find the user by his email 

`curl -s http://localhost:8080/rest/admin/users/byEmail?email=harry.maguire%40gmail.com --user admin@gmail.com:admin`

- Remove user by his id 

`curl -s -X DELETE http://localhost:8080/rest/admin/users/5 --user admin@gmail.com:admin`

- Add a new user

`curl -s -X POST -d '{"name":"Ivan","email":"ivan@gmail.com","password":"qwerty","registered":"2020-01-10 19:48","roles": ["ROLE_USER"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/users --user admin@gmail.com:admin`

- Edit user id data 

`curl -s -X PUT -d '{"name":"Pedro","email":"pedro@gmail.com","password":"ytrewq","registered":"2020-01-10 19:48","roles": ["ROLE_ADMIN"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/users/4 --user admin@gmail.com:admin`

4. Admin actions with voices

- Find a voice by id 

`curl -s http://localhost:8080/rest/admin/vote/4 --user admin@gmail.com:admin`
  
- Find all the voices of an individual user 

`curl -s http://localhost:8080/rest/admin/vote/user/2 --user admin@gmail.com:admin`
  
- Find all votes for the selected restaurant 

`curl -s http://localhost:8080/rest/admin/vote/restaurant/5 --user admin@gmail.com:admin`
  
- Find all votes for the selected date 

`curl -s http://localhost:8080/rest/admin/vote/byDate?date=2019-12-20 --user admin@gmail.com:admin`

- Find all the votes for today 

`curl -s http://localhost:8080/rest/admin/vote/today --user admin@gmail.com:admin`
  
- Find all votes for today within the specified dates 

`curl -s http://localhost:8080/rest/admin/vote/filter?startDate=2019-12-20&endDate=2019-12-22 --user admin@gmail.com:admin`

- Find all votes for today within the specified dates and selected user id

`curl -s http://localhost:8080/rest/admin/vote/filterId?startDate=2019-12-20&endDate=2019-12-22&userId=2 --user admin@gmail.com:admin`
  
- Remove voice found by id 

`curl -s -X DELETE http://localhost:8080/rest/admin/vote/2 --user admin@gmail.com:admin`

5. User actions in the app

- See all restaurants that have a database

`curl -s http://localhost:8080/rest/users/restaurants --user user@gmail.com:user`

- Choose a specific restaurant by its id 

`curl -s http://localhost:8080/rest/users/restaurants/10 --user admin@gmail.com:admin`

- Choose a specific restaurant by its name 

`curl -s http://localhost:8080/rest/users/restaurants/byName?name=La%20Creperie --user user@gmail.com:user`
  
- View the menu for today at a restaurant of your choice

`curl -s http://localhost:8080/rest/users/menu/Epicure --user user@gmail.com:user`
  
- Vote for your chosen restaurant 

`curl -s -X POST -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/users/voting/3 --user user@gmail.com:user`

- An unregistered user can register

`curl -s -X POST -d '{"name":"Roman","email":"pobihushka@gmail.com","password":"romashka"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/users/register`

- Registered users can edit their profile

`curl -s -X PUT -d '{"name":"Roman","email":"pobihushka.roman@gmail.com","password":"romashka"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/users --user pobihushka@gmail.com:romashka`







 
  








