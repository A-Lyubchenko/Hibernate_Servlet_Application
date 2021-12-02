<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>UPDATE SKILL</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
</head>

<body>
<h2 class="btn-success">UPDATE SKILL</h2>
<br/>
<br/>
<form class="row g-3" method="POST" action="skills/updateSkill">
    <input type="hidden" name="updateId" value="${updateId}">
    <div class="col-md-6">
        <label class="form-label" for="department"><strong>Enter new department</strong></label>
        <input class="form-control" type="text" name="department" id="department" placeholder="skill/department">
        <div style="color: green" if="${fields.hasErrors('department')}" errors="${department}">Department must not be decimal</div>
    </div>
    <br/>
    <br/>
    <div class="col-md-6">
        <label class="form-label" for="level"><strong>Enter new level</strong></label>
        <input class="form-control" type="text" name="level" id="level" placeholder="skill/level">
        <div style="color: green" if="${fields.hasErrors('level')}" errors="${level}">Level must not be decimal</div>
    </div>
    <br/>
    <br/>
    <h4 style="color: blue">If you write wrong field, page will refresh</h4>
    <input class="btn btn-success" type="submit"  value="Update"/>
</form>
</body>
</html>