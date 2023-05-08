# order-management-system
Interview question
TASK:
- Local database installation (mysql, postgresql or any database)
- Start the java application
- There should be two tables and these tables should be related to each other.
Order ( create date, id , total price, customer, )
Customer (id , name , age , orders )
- Java spring application should include services such as adding, deleting, updating, listing, and
The transaction detail made in the response should be returned.
- Adding, deleting, updating, listing operations should be possible with postman etc.
These steps should be followed in order and registered to the database via the java application (Any
You can register, it doesn't matter. Java request to open a table in the database and enter a value in that table
over). Then get and list java spring with the same request thrown application (example postman ...)
By calling endpoints, the record thrown to the database should be returned as a response.

- In addition to the above add-delete-update listing operations
- It must be a service and order orders created after the date given as a parameter.
list it.
- It should be a service and take a word or letter value as a parameter and
Let it fetch the order id of the customer and the customer with this value in it.
- There must be a service and list of customers who do not have an order
