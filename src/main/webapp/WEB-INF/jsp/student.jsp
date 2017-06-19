<%-- 
    Document   : student
    Created on : 03-Jun-2017, 21:57:35
    Author     : Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
--%>

<%@taglib tagdir="/WEB-INF/tags/" prefix="art" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-toggleable-md navbar-inverse fixed-top bg-inverse">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
     <a class="navbar-brand" href="remove">${sessionScope.resourceBundle.locale_logout}</a>
    <ul>${sessionScope.firstName} ${sessionScope.resourceBundle.locale_authorised} ${sessionScope.role}
        <hr>
        <label>${sessionScope.resourceBundle.locale_lang}</label>
        <form id="l1" action="translate" method="POST"/>
        <select name="lang" onchange="document.getElementById('l1').submit();">
            <option></option>
            <option value="ua">Українська</option>
            <option value="en">English</option>
        </select>
    </form>
    </ul>
</nav>

<div id="myCarousel" class="carousel slide" data-ride="carousel" data-interval="false">
    <div class="carousel-inner" role="listbox">
        <div class="carousel-item active">
            <img class="first-slide" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" alt="First slide">
            <div class="container">
                <div class="carousel-caption d-none d-md-block text-left">                    
                    <h1>${sessionScope.resourceBundle.locale_custom}</h1> 
                    <form id="updateCred" action="updateCreds" method="POST" onsubmit="return validateCred()">
                        <input name="firstName" type="text" value="${sessionScope.user.firstName}" placeholder="${sessionScope.resourceBundle.locale_first}" onclick="Bring(this);"/><br/>
                        <input name="lastName" type="text" value="${sessionScope.user.lastName}" placeholder="${sessionScope.resourceBundle.locale_last}" oncklick="Bring(this);"/><br/>
                        <input name="login" type="text" value="${sessionScope.user.login}" placeholder="${sessionScope.resourceBundle.locale_login}" onclick="Bring(this);"/><br/>
                        <input name="password" type="password" value="" placeholder="${sessionScope.resourceBundle.locale_password}" onclick="CONFPASS();"/><br/>
                        <input id="cfpswd" name="confirmPassword" type="password" value="" placeholder="${sessionScope.resourceBundle.locale_confirm}" onclick="Bring(this);" style="display:none"/><br/>
                        <input class="btn btn-lg btn-primary btn-block" id="svcrd" type="submit" value="${sessionScope.resourceBundle.locale_save_changes}" disabled="disabled"/>
                    </form>
                </div>
            </div>
        </div>        
        <div class="carousel-item">
            <img class="second-slide" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" alt="Second slide">
            <div class="container">
                <div class="carousel-caption d-none d-md-block">
                    
                    <art:specialCourses/>
                </div>
            </div>
        </div>
        <div class="carousel-item">
            <img class="third-slide" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" alt="Third slide">
            <div class="container">
                <div class="carousel-caption d-none d-md-block text-right">
                    <art:courses/>
                </div>
            </div>
        </div>
    </div>
    <a class="carousel-control-prev" href="#myCarousel" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="carousel-control-next" href="#myCarousel" role="button" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>
<div hidden="hiden"><art:teachers/></div>
<div id="coursesList" class="modal">

    <!-- Modal content -->
    <div class="modal-content">
        <span class="close" id="closeC">&times;</span>
        <art:form align="LEFT" bgColor="YELLOW"
                  border="6" fgColor="BLACK"
                  font="Verdana" size="18">
            <div id="crs" style="width: 100%">

            </div>
        </art:form>

    </div>

</div>
<div id="courseAdd" class="modal">

    <!-- Modal content -->
    <div class="modal-content">
        <span class="close" id="closeNewC">&times;</span>
        <form id="newCourse">
            <input id="newNameC" type="text" placeholder="${sessionScope.resourceBundle.locale_course_name}"/><br>
            <input id="newThemeC" type="text" placeholder="${sessionScope.resourceBundle.locale_course_theme}"/><br>
            <input id="newTeacherC" type="number" placeholder="${sessionScope.resourceBundle.locale_course_teacher}"/><br>
            <input id="newBeginingC" type="date" placeholder="${sessionScope.resourceBundle.locale_course_beginning}"/><br>
            <input id="newEndingC" type="date" placeholder="${sessionScope.resourceBundle.locale_course_ending}"/><br>
            <button btn btn-lg btn-primary id="saveNC" onclick="submitCourse();" >${sessionScope.resourceBundle.locale_save}</button>
        </form>
    </div>

</div>
<div id="teachersList" class="modal">

    <!-- Modal content -->
    <div class="modal-content">
        <span class="close" id="closeT">&times;</span>
        <button btn btn-lg btn-primary id="saveT" onclick="submitTeachers();" >${sessionScope.resourceBundle.locale_save}</button>
        <art:form align="LEFT" bgColor="YELLOW"
                  border="6" fgColor="BLACK"
                  font="Verdana" size="18">
            <div id="tcs" style="width: 100%">

            </div>
        </art:form>

    </div>

</div>
<div id="teacherAdd" class="modal">

    <!-- Modal content -->
    <div class="modal-content">
        <span class="close" id="closeNewT">&times;</span>
        <form id="newTeacher">
            <input id="newFirstNameT" type="text" placeholder="${sessionScope.resourceBundle.locale_first}"/><br>
            <input id="newLastNameT" type="text" placeholder="${sessionScope.resourceBundle.locale_last}"/><br>
            <input id="newLoginT" type="text" placeholder="${sessionScope.resourceBundle.locale_login}"/><br>
            <input id="newPasswordT" type="password" placeholder="${sessionScope.resourceBundle.locale_password}"/><br>
            <input id="newRePasswordT" type="password" placeholder="${sessionScope.resourceBundle.locale_confirm}/><br>
            <button btn btn-lg btn-primary id="saveNT" onclick="submitTeacher();" >${sessionScope.resourceBundle.locale_save}</button>
        </form>
    </div>

</div>
<div id="specialcoursesList" class="modal">

    <!-- Modal content -->
    <div class="modal-content">
        <span class="close" id="closeSC">&times;</span>
        <art:form align="LEFT" bgColor="YELLOW"
                  border="6" fgColor="BLACK"
                  font="Verdana" size="18">
            <div id="scrs" style="width: 100%">

            </div>
        </art:form>

    </div>

</div>
<art:script/>

<input id="role" type="hidden" name="role" value="${sessionScope.role}" />
<art:specialScript/>
<!--script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script-->



<script src="https://v4-alpha.getbootstrap.com/dist/js/bootstrap.min.js"></script>
<script>
function subscribeStudent(studId, sbtn){
    var btn = sbtn;
    var course = btn.value;
    btn.style.display = "none";
    var student = studId;
    $.ajax({
            url: 'subscribe',
            data: $.param({course:course, student:student}),
            success: function () {
            },
            error: function () {
                alert("Error");
            }
        });
}    
</script>