<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ABOUT PROJECT</title>
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
        <h2>Project page</h2>
    </div>

        <table class="table">
            <thead>
            <tr>
                <th scope="col">Id</th>
                <th scope="col">Name</th>
                <th scope="col">Start</th>
                <th scope="col">Coast</th>
            </tr>
            </thead>
            <tbody>
                <tr>
                    <td><c:out value = "${project.id}"/></td>
                    <td><c:out value = "${project.name}"/></td>
                    <td><c:out value = "${project.start}"/></td>
                    <td><c:out value = "${project.coast}"/></td>
                    <td>
                        <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                            <div class="btn-group me-2" role="group" aria-label="Second group">
                                <a href="projects/updateProject?updateId=<c:out value = '${project.id}'/>" type="button" class="btn btn-warning">Edit</a>
                                <a href="projects/remove?deleteId=<c:out value = '${project.id}'/>" type="button" class="btn btn-danger">Remove</a>
                            </div>
                        </div>
                    </td>
                </tr>
<%--            </c:forEach>--%>
            </tbody>
        </table>

    <table class="table">
        <thead>
        <tr>
            <th scope="col">Name of Developers working int this project</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach var="developer" items="${project.getDevelopers()}" varStatus="session">
            <tr>
                <td><c:out value = "${developer.name}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <table class="table">
        <thead>
        <tr>
            <th scope="col">Name of Company having in this project</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach var="company" items="${project.getCompany()}" varStatus="session">
            <tr>
                <td><c:out value = "${company.name}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <table class="table">
        <thead>
        <tr>
            <th scope="col">Name of Customer having in this project</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach var="customer" items="${project.getCustomer()}" varStatus="session">
            <tr>
                <td><c:out value = "${customer.name}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    </div>
</body>
</html>

