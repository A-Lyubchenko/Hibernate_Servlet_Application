<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>UPDATE DEVELOPER</title>
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

<h2 class="btn-success">CREATE NEW DEVELOPER</h2>
<br/>
<br/>
<form class="row g-3" method="POST" action="developers/createDeveloper">
    <input type="hidden" name="companyId" value="${company.id}">

    <div class="col-md-6">
        <label class="form-label" for="name"><strong>Enter name</strong></label>
        <input class="form-control" type="text" name="name" id="name" placeholder="developer/name">
        <div style="color: green" if="${fields.hasErrors('name')}" errors="${name}">Name must not be decimal</div>

    </div>
    <br/>
    <br/>
    <div class="col-md-6">
        <label class="form-label" for="age"><strong>Enter age</strong></label>
        <input class="form-control" type="text" name="age" id="age" placeholder="developer/age">
        <div style="color: green" if="${fields.hasErrors('age')}" errors="${age}">Age must be positive decimal</div>

    </div>
    <br/>
    <br/>
    <div class="col-md-6">
        <label class="form-label" for="sex"><strong>Enter sex</strong></label>
        <input class="form-control" type="text" name="sex" id="sex" placeholder="developer/sex">
        <div style="color: green" if="${fields.hasErrors('sex')}" errors="${sex}">Sex must be (male) or (female)</div>

    </div>
    <br/>
    <br/>
    <div class="col-md-6">
        <label class="form-label" for="phone_number"><strong>Enter phone_number</strong></label>
        <input class="form-control" type="text" name="phone_number" id="phone_number" placeholder="developer/phone_number">
        <div style="color: green" if="${fields.hasErrors('phone_number')}" errors="${phone_number}">Phone_number must be decimal and have 10 decimal</div>

    </div>
    <br/>
    <br/>
    <div class="col-md-6">
        <label class="form-label" for="salary"><strong>Enter salary</strong></label>
        <input class="form-control" type="text" name="salary" id="salary" placeholder="developer/salary">
        <div style="color: green" if="${fields.hasErrors('salary')}" errors="${salary}">Salary must be decimal and between 1 decimal and 5</div>

    </div>
    <br/>
    <br/>
    <div class="col-md-6">
    <strong>Choose Company</strong>
        <select class="form-select" aria-label="Default select example" name="company">
            <option selected>Open this company menu</option>
            <c:forEach var="company" items="${companies}" varStatus="session">

                <option value="${company.id}">${company.name}</option>
        </c:forEach>
            </select>
    </div>
    <br/>
    <br/>
    <div class="col-md-6">
        <strong>Choose Project</strong>
        <select class="form-select" aria-label="Default select example" name="project">
            <option selected>Open this projects menu</option>
            <c:forEach var="project" items="${projects}" varStatus="session">
                <option value="${project.id}">${project.name}</option>
            </c:forEach>
        </select>
    </div>
    <br/>
    <br/>
    <div class="col-md-6">
        <strong>Choose Skill</strong>
        <select class="form-select" aria-label="Default select example" name="skill">
            <option selected>Open this skills menu</option>
            <c:forEach var="skill" items="${skills}" varStatus="session">
                <option value="${skill.id}">${skill.department}    (${skill.level})</option>
            </c:forEach>
        </select>
    </div>
    <br/>
    <br/>
    <h4 style="color: blue">If you write wrong field, page will refresh</h4>
    <input class="btn btn-success" type="submit"  value="Create"/>
</form>
</body>
</html>
