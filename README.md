<a name="readme-top"></a>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href=" ">
    <img src="documentation/bookApi-logo.png" alt="Logo" width="320" height="240">
  </a>

  <h3 align="center">Book store API</h3>

  <p align="center">
    Api for book store
    <br />
    <a href="https://github.com/mkulovac1/bookStoreApi"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="">View Demo</a>
    ·
    <a href="https://github.com/mkulovac1/bookStoreApi/issues">Report Bug</a>
  </p>
</div>




<!-- ABOUT THE PROJECT -->
## About The Project

<p style="text-align: justify;">
  Book store API is a project that can be used, as the name suggests, in a book store to manipulate books and users on backend. The api is made in Java (framework - latest Spring Boot version) and MySQL. 
  JWT authentication is used. As for the user, it is possible to perform standard CRUD operations as admin. 
  It is possible to confirm the registration via a link, this is necessary so that the user can login into the system later. The user has 10 minutes to confirm the registration, if user does not confirm it, user receives a new link for confirmation to the email. 
  As for books, it is also possible to do CRUD operations as an admin. 
  In addition, we have sorting and filtering of books by several criteria (simultaneously and individually). 
  Pagination has also been added
  The role is very important, so a special class, service and controller was created for it.
  There are two roles in the system, user and admin. 
  Admin can do all operations. 
  User can do everything except CRUD operations for users, books and roles.
</p>

<p align="right">(<a href="#readme-top">back to top</a>)</p>




## Built With

* Java
* Spring Boot
* MySQL

<p align="right">(<a href="#readme-top">back to top</a>)</p>




<!-- USAGE EXAMPLES -->
## Usage

Here you can see examples of using this API through Postman.

### REGISTRATION

<br>

**Empty USER table:**

<p align="center">
  <a href="">
    <img src="documentation/registration/user-empty.png" alt="user-empty">
  </a>  
</p>



**Register:**

<p align="center">
  <a href="">
    <img src="documentation/registration/register.png" alt="register">
  </a>  
</p>



**Verification via e-mail:**

<p align="center">
  <a href="">
    <img src="documentation/registration/email-verification.png" alt="email-verification">
  </a>  
</p>



**Confirming registration:**

<p align="center">
  <a href="">
    <img src="documentation/registration/verification-click.png" alt="verification-click">
  </a>  
</p>



**Late confirmation:**

<p align="center">
  <a href="">
    <img src="documentation/registration/invalid-token-registration.png" alt="invalid-token-registration">
  </a>  
</p>

<br> <br> <br> <br> 

### LOGIN/AUTH:

<br>

**Login:**

<p align="center">
  <a href="">
    <img src="documentation/registration/auth-login.png" alt="invalid-token-registration">
  </a>  
</p>



**Decoding JWT:**

<p align="center">
  <a href="">
    <img src="documentation/registration/jwt-io.png" alt="invalid-token-registration">
  </a>  
</p>

<br> <br> <br> <br> 

### ROLES


<p align="center">
  <a href="">
    <img src="documentation/role/role-empty.png" alt="role-empty">
  </a>  
</p>



**Creating role:**

<p align="center">
  <a href="">
    <img src="documentation/role/roles-create.png" alt="roles-create">
  </a>  
</p>



**Creating existing role:**

<p align="center">
  <a href="">
    <img src="documentation/role/role-exist.png" alt="role-exist">
  </a>  
</p>



**Getting all roles:**

<p align="center">
  <a href="">
    <img src="documentation/role/roles-all.png" alt="roles-all">
  </a>  
</p>



**Roles in database:**

<p align="center">
  <a href="">
    <img src="documentation/role/roles-all-db.png" alt="roles-all">
  </a>  
</p>



**Deleting role:**

<p align="center">
  <a href="">
    <img src="documentation/role/role-delete.png" alt="roles-all">
  </a>  
</p>



**Database after deleting role:**

<p align="center">
  <a href="">
    <img src="documentation/role/role-delete-db.png" alt="roles-all">
  </a>  
</p>

<br> <br> <br> <br> 

### USERS

<br>

**Adding user:**

<p align="center">
  <a href="">
    <img src="documentation/user/users-add.png" alt="user">
  </a>  
</p>



**Database after adding user:**

<p align="center">
  <a href="">
    <img src="documentation/user/users-add-db.png" alt="user">
  </a>  
</p>



**All users:**

<p align="center">
  <a href="">
    <img src="documentation/user/users-all.png" alt="user">
  </a>  
</p>



**All users in database:**

<p align="center">
  <a href="">
    <img src="documentation/user/users-db.png" alt="user">
  </a>  
</p>



**Deleting user:**

<p align="center">
  <a href="">
    <img src="documentation/user/users-delete.png" alt="user">
  </a>  
</p>



**Database after deleting user:**

<p align="center">
  <a href="">
    <img src="documentation/user/users-delete-db.png" alt="user">
  </a>  
</p>



**Activate user:**

<p align="center">
  <a href="">
    <img src="documentation/user/users-activate.png" alt="user">
  </a>  
</p>



**Activate user - database:**

<p align="center">
  <a href="">
    <img src="documentation/user/users-activate-db.png" alt="user">
  </a>  
</p>



**Deactivate user:**

<p align="center">
  <a href="">
    <img src="documentation/user/users-deactivate.png" alt="user">
  </a>  
</p>



**Deactivate user - database:**

<p align="center">
  <a href="">
    <img src="documentation/user/users-deactivate-db.png" alt="user">
  </a>  
</p>



**Get user by e-mail:**

<p align="center">
  <a href="">
    <img src="documentation/user/users-get-user-by-email.png" alt="user">
  </a>  
</p>



**Change/update user's password:**

<p align="center">
  <a href="">
    <img src="documentation/user/users-updatePassword.png" alt="user">
  </a>  
</p>

<br> <br> <br> <br>  

### BOOKS

<br>

**Empty BOOK table:**

<p align="center">
  <a href="">
    <img src="documentation/book/books-empty-db.png" alt="book">
  </a>  
</p>



**Adding multiple books:**

<p align="center">
  <a href="">
    <img src="documentation/book/books-add.png" alt="book">
  </a>  
</p>



**Database after adding multiple books:**

<p align="center">
  <a href="">
    <img src="documentation/book/books-add-db.png" alt="book">
  </a>  
</p>



**Adding one book:**

<p align="center">
  <a href="">
    <img src="documentation/book/books-book-add.png" alt="book">
  </a>  
</p>



**Database after adding one book:**

<p align="center">
  <a href="">
    <img src="documentation/book/books-book-add-db.png" alt="book">
  </a>  
</p>



**Getting all books:**

<p align="center">
  <a href="">
    <img src="documentation/book/books-all.png" alt="book">
  </a>  
</p>



**Getting one book:**

<p align="center">
  <a href="">
    <img src="documentation/book/books-get-book.png" alt="book">
  </a>  
</p>



**Database before updating the book:**

<p align="center">
  <a href="">
    <img src="documentation/book/books-update-before.png" alt="book">
  </a>  
</p>



**Updating the book:**

<p align="center">
  <a href="">
    <img src="documentation/book/books-update-after.png" alt="book">
  </a>  
</p>



**Database after updating the book:**

<p align="center">
  <a href="">
    <img src="documentation/book/books-update-after-db.png" alt="book">
  </a>  
</p>



**Deleting the book:**

<p align="center">
  <a href="">
    <img src="documentation/book/books-delete.png" alt="book">
  </a>  
</p>



**Filtering books using one criteria**

<p align="center">
  <a href="">
    <img src="documentation/book/books-filter-one.png" alt="book">
  </a>  
</p>



**Filtering books using multiple criteria**

<p align="center">
  <a href="">
    <img src="documentation/book/books-filter-many.png" alt="book">
  </a>  
</p>



**Sorting books with pagination:**

<p align="center">
  <a href="">
    <img src="documentation/book/books-sort.png" alt="book">
  </a>  
</p>

 

_For more examples, please refer to the [Documentation](https://github.com/mkulovac1/bookStoreApi/documentation)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>




<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>




<!-- CONTACT -->
## Contact

Merim Kulovac, [@merimkulovac](https://www.linkedin.com/in/merimkulovac/), e-mail: merim.kulovac@outlook.com

<p align="right">(<a href="#readme-top">back to top</a>)</p>
