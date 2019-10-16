A Friend Recommendation System for Music Social Network
=======================

This is a recommendation system to discover music tastes of users and recommend friends to users if they share the similar taste determined.<br>

All data comes from NetEase Music.<br>

Architecture
-------------
Application Type: Spring-Boot Java Web Application (Jetty)<br>

Web framework: Spring-Boot enabled Spring-WebMVC, Spring-Data-Rest<br>

Persistence Access: Spring-Data-Neo4j 5.0.5<br>

Database: Neo4j-Server 3.4.0<br>

Frontend: jquery, bootstrap, Thymeleaf<br>

Get Started
--------------
0. Install Neo4j-Server 3.3+, create a new graph(table) and set up your password.<br>
1. Change the psw in resource/application.propertiesto your own psw.<br>
2. Run the project and type the website http://localhost:8080/get into your browser.<br>
3. Input your user ID and you can access the search page<br>
4. Enjoy!<br>

More Questions
-----------
- How to get my user ID?<br>
&emsp;Visit [NetEase Music](https://music.163.com/)，log into your own account and go to your personal index，and the last parameter of the website is your user ID.<br>
&emsp;For example, the website of my personal index is https://music.163.com/#/user/home?id=2314022, and my user ID is 2314022


