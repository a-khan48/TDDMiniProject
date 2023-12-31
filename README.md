TDDMiniProject - 

This project consist of an order system built using RESTful API, Built upon the Spring framework, A user can create, read, update, and delete orders on a localhost server.

Steps:

- Clone this repository into a project folder
- After you clone the repository, open the project using your preferred IDE
- Run the TDDMiniProjApplication java class.
- Open the system using "http://localhost:8080/orders", it should display an empty page for now.
- After running the class, navigate to the BlogPostController class
- In the "@RequestMapping" line of the program, navigate to the actions menu, which is labeled under the Earth symbol next to "/post"
- In the dropdown menu, hit "Generate request in HTTP client"
- After pressing that button, you should be transferred to a notepad where you can type various GET, POST, PUT, and DELETE commands.
- Remove any current text populated in the notepad and paste these commands to verify functionality. Commands can only be input one at a time. Each command will be numbered in order to verify all commands function (before you paste these commands in, wait until you read all the steps to understand the functionaity of the program):

1. 
POST /orders HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "customerName": "John Doe",
  "orderDate": "2023-07-07",
  "shippingAddress": "123 Main st",
  "total": 99.99
}

2.
PUT /orders/1 HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "customerName": "John Dough",
  "orderDate": "2023-07-07",
  "shippingAddress": "321 Pain st",
  "total": 100.00
}

3.
Get /orders/1 HTTP/1.1
Host: localhost:8080  

4.
DELETE /orders/1 HTTP/1.1
Host: localhost:8080

// Alternatively, you can write your own GET, PUT, POST, or DELETE commands as well. This is just to verify that all functions are working as intended.

- To generate an HTTP Request, hit the two green play buttons in the Generate HTTP Request Menu (Remember, only 1 command at a time!).
- After generating an HTTP Request, you can go to HTTP://localhost:8080/orders/1 where you should see your CRUD command populating the brackets.
- If this post is displayed and the IDE reports no errors after generating an HTTP request, then you have successfully used the order system!
