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
<h2 class="btn-success">UPDATE DEVELOPER</h2>
<br/>
<br/>
<form class="row g-3" method="POST" action="developers/updateDeveloper">
    <input type="hidden" name="updateId" value="${updateId}">
    <div class="col-md-6">
        <label class="form-label" for="name"><strong>Enter new name</strong></label>
        <input class="form-control" type="text" name="name" id="name" placeholder="developer/name">
        <div style="color: green" if="${fields.hasErrors('name')}" errors="${name}">Name must not be decimal</div>
    </div>
    <br/>
    <br/>
    <div class="col-md-6">
        <label class="form-label" for="age"><strong>Enter new age</strong></label>
        <input class="form-control" type="text" name="age" id="age" placeholder="developer/age">
        <div style="color: green" if="${fields.hasErrors('age')}" errors="${age}">Age must be positive decimal</div>
    </div>
    <br/>
    <br/>
    <div class="col-md-6">
        <label class="form-label" for="sex"><strong>Enter new sex</strong></label>
        <input class="form-control" type="text" name="sex" id="sex" placeholder="developer/sex">
        <div style="color: green" if="${fields.hasErrors('sex')}" errors="${sex}">Sex must be (male) or (female)</div>
    </div>
    <br/>
    <br/>
    <div class="col-md-6">
        <label class="form-label" for="phone_number"><strong>Enter new phone_number</strong></label>
        <input class="form-control" type="text" name="phone_number" id="phone_number" placeholder="developer/phone_number">
        <div style="color: green" if="${fields.hasErrors('phone_number')}" errors="${phone_number}">Phone_number must be decimal and have 10 decimal</div>
    </div>
    <br/>
    <br/>
    <div class="col-md-6">
        <label class="form-label" for="salary"><strong>Enter new salary</strong></label>
        <input class="form-control" type="text" name="salary" id="salary" placeholder="developer/salary">
        <div style="color: green" if="${fields.hasErrors('salary')}" errors="${salary}">Salary must be decimal and between 1 decimal and 5</div>
    </div>
    <br/>
    <br/>
    <h4 style="color: blue">If you write wrong field, page will refresh</h4>
    <input class="btn btn-success" type="submit"  value="Update"/>
</form>
</body>
</html>