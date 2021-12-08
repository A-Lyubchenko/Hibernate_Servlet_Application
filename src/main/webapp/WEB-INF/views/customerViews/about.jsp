<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>ABOUT CUSTOMER</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
          integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
          crossorigin="anonymous"></script>
</head>

<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Main page</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link" aria-current="page" href="/companies">Companies</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/customers">Customers</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/developers">Developers</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/projects">Projects</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/skills">Skills</a>
        </li>
      </ul>
    </div>
  </div>
</nav>


<div class="container">
  <div class="row">
    <h2>Customer page</h2>
  </div>

  <table class="table">
    <thead>
    <tr>
      <th scope="col">Id</th>
      <th scope="col">Name</th>
      <th scope="col">Location</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td><c:out value = "${customer.id}"/></td>
      <td><c:out value = "${customer.name}"/></td>
      <td><c:out value = "${customer.location}"/></td>
      <td>
        <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
          <div class="btn-group me-2" role="group" aria-label="Second group">
            <a href="customers/updateCustomer?updateId=<c:out value = '${customer.id}'/>" type="button" class="btn btn-warning">Edit</a>
            <a href="customers/customers?deleteId=<c:out value = '${customer.id}'/>" type="button" class="btn btn-danger">Remove</a>
          </div>
        </div>
      </td>
    </tr>
    </tbody>
  </table>

  <table class="table">
    <thead>
    <tr>
      <th scope="col">Name of Project having this Customer</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach var="project" items="${customer.getProjects()}" varStatus="session">
      <tr>
        <td><c:out value = "${project.name}"/></td>
        <td>
          <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
            <div class="btn-group me-2" role="group" aria-label="Second group">
              <a href="customers/deleteProject?projectId=<c:out value = '${project.id}'/>&customerId=<c:out value = '${customer.id}'/>"
                 type="button" class="btn btn-danger">Delete</a>
            </div>
          </div>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>

  <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
    <div class="btn-group me-2" role="group" aria-label="Second group">
      <a href="customers/addProject?customerId=<c:out value = '${customer.id}'/>" type="button" class="btn btn-success">Add project</a>
    </div>
  </div>
</div>
</body>
</html>

