-- What are the categories of products in the database?

use northwind;
select category from products 

-- What products are made by Dell?

select *
from  northwind.products
where product_name 
like '%Dell%'


-- List all the orders shipped to Pennsylvania.

select *
from northwind.orders
where ship_city = 'Pennsylvania'

-- List the first name and last name of all employees with last names that start with the letter W.

select first_name , last_name 
from northwind.employees 
where last_name  
like 'W%'

-- List all customers from zip codes that start with 55.

select id , first_name , last_name , postal_code , city 
from northwind.customers
where postal_code
like '55%'


-- List all customers from zip codes that end with 0.

select id , first_name , last_name , postal_code , city  
from northwind.customers
where postal_code
like '%0'

-- List the first name, last name, and email for all customers with a ".org" email address.

select first_name , last_name , email
from northwind.customers 
where email 
like '%.org%'

-- List the first name, last name, and phone number for all customers from the 202 area code.

select first_name , last_name , phone
from northwind.customers 
where phone 
like '%202%'


-- List the first name, last name, and phone number for all customers from the 202 area code, ordered by last name, first name.

select first_name , last_name , phone
from northwind.customers 
where phone 
like '%202%'
order by last_name ,first_name 
