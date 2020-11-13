# [NewsPortal ](https://github.com/amodoipeter/newsapi)

Author [amodoipeter](https://github.com/amodoipeter)

## Brief description
NewsPortal is an Organisational news dissemination application. 
Users can post news to the general staff or to staff in a specific department.

## User Requirements   

The user would like to;

- Create departments
- Create news and categorize then as general or belonging to a specific department
- Add users to a department where one department has many users
- See individual users, their details like position,role and department.
- See department details like name, details and number of employees in the department
- See all users from a specific department and news relating to that department
- Post general news or news relating s a specific department.

## How it works

# Server Side

The application has four model classes;

- User
- News
- DepartmentNews
- Department

DepartmentNews inherits from News.
In the database, model User maps to table users, model Department maps to table departments while model
News and model DepartmentNews map to one table news through single table inheritance.

Three dao interfaces, and their corresponding implementation classes;
- Interface UserDao implemented by class Sql2oUserDao, handles CRUD data operations related to model User.
- Interface NewsDao implemented by class Sql2oNewsDao, handles CRUD data operations related to model News and DepartmentNews.
- Interface DepartmentDao implemented by class Sql2oDepartmentDao, handles CRUD data operations related to model Department.

The API routes;

- GET "/users"
- GET "/departments"
- GET "/users/:id"
- GET "/departments/:id"
- GET "/departments/:id/users"
- GET "/departments/:id/news"
- GET "/news"
- GET "/news/general"
- GET "/news/department"

- POST "/Users/new"
- POST "/Departments/new"
- POST "/News/new"
- POST "/DepartmentNews/new"

# Client Side

The client interface is provided through a browser. The landing page by default shows all users.
The landing page has tabs to view;
- Users
- Departments
- General news
- Department news

On each tab, the user is able to see a listing of that category. Each tab has a form where a users can add 
corresponding items, i.e;
 - The users tab has a form to add users, 
 - The departments tab has a form to add departments,
 - The general news tab has a tab to add news to the general category
 - The department news tab has a form to add news to a specific department.
 
If a user tries to add a user or a department that already exist, they are alerted that that department already exists.

The departments tab in addition to showing a new department form and a listing of all departments,
each department list items has options to view all users in that department, or all news in that department.

On clicking this tags, the user can click the departments tab to go back the main departments listing, from which they
can narrow down to another departments users or news.
## Setup Instructions
Fork the project on github [NewsPortal ](https://github.com/amodoipeter/newsapi), then clone it to your local repo.

## Technology used
 - Java
 - SparkJava
 - Gradle
 - JUnit 4
 - Git VCS
 - IntelliJ IDEA Community Edition
 - Postgres
 - Sql2o
 - AngularJs
 
## Known Bugs
There are no known bugs. Please submit those you find for correction.

## Contributing
Pull requests are encouraged.

## License
This project is licensed under the MIT Open Source license.
