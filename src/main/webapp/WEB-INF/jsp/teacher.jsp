<%-- 
    Document   : teacher
    Created on : 03-Jun-2017, 18:16:24
    Author     : Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
--%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="art" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-toggleable-md navbar-inverse fixed-top bg-inverse">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
   <a class="navbar-brand" href="remove">${sessionScope.resourceBundle.locale_logout}</a>
    <ul><table>
            <tr><td>
                    ${sessionScope.firstName} ${sessionScope.resourceBundle.locale_authorised} ${sessionScope.role}
        <hr>
        <label>${sessionScope.resourceBundle.locale_lang}</label>
        <form id="l1" action="translate" method="POST"/>
        <select name="lang" onchange="document.getElementById('l1').submit();">
            <option></option>
            <option value="ua">Українська</option>
            <option value="en">English</option>
        </select>
    </form>
                </td><td><a href="getMyStudents">${sessionScope.resourceBundle.locale_students}</a></td>
            </tr>
        </table>
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
                    <art:specialCourses role="teacher"/>
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
        <button btn btn-lg btn-primary id="saveC" onclick="submitCourses();" >${sessionScope.resourceBundle.locale_save}</button>
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
<div id="journal" class="modal">

    <!-- Modal content -->
    <div class="modal-content">
        <span class="close" id="closeJ">&times;</span>
        <button btn btn-lg btn-primary id="saveJ" onclick="submitJournal();" >${sessionScope.resourceBundle.locale_save}</button>
        <art:form align="LEFT" bgColor="YELLOW"
                  border="6" fgColor="BLACK"
                  font="Verdana" size="18">
            <div id="jrn" style="width: 100%">

            </div>
        </art:form>

    </div>

</div>
<art:script/>
<art:specialScript/>

<input id="role" type="hidden" name="role" value="${sessionScope.role}" />

<!--script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script-->



<script src="https://v4-alpha.getbootstrap.com/dist/js/bootstrap.min.js"></script>

<script>
            function getJournal(id)
            {
                //document.getElementById("journal").style.display = "block";
                //var s;
                $.ajax({
                 url: 'getJournal',
                 data: $.param({id : id})
                 });
                
            }
            function addJournal(s)
            {

                var div = document.getElementById('jrn');
                var jourId = document.createElement("input");
                jourId.setAttribute("type", "hidden");
                jourId.setAttribute("id", "jourId");
                jourId.setAttribute("value", s.id.value);
                div.appendChild(jourId);
                var table = document.createElement('table');
                table.setAttribute('id', "table_J");
                table.setAttribute('style', 'width: 100%');
                var lable = document.createElement('lable');
                lable.textContent = s.name;
                div.appendChild(lable);
                var save = document.createElement('button');
                save.setAttribute("id", "saveJ");
                save.textContent = "${sessionScope.resourceBundle.locale_save}";
                save.display = none;
                div.appendChild(save);
                for (i = 0; i < s.length; i++)
                {
                    var r = document.createElement("tr");
                    r.setAttribute('style', 'width:100%');
                    var cs = document.createElement("td");
                    cs.setAttribute('style', 'width: 100%');
                    var form = document.createElement("form");
                    form.setAttribute("class", "form_J");

                    var ii = document.createElement("input");
                    ii.setAttribute("type", "hidden");
                    ii.setAttribute("class", "studentId");
                    ii.setAttribute("readonly", "readonly");
                    ii.setAttribute("value", s.students[i].id);
                    form.appendChild(ii);

                    var ni = document.createElement("input");
                    ni.setAttribute("type", "text");
                    ni.setAttribute("readonly", "readonly");
                    ni.setAttribute("value", s.students[i].firstName + " " + s.students[i].lastName);
                    form.appendChild(ni);

                    var pi = document.createElement("input");
                    pi.setAttribute("type", "text");
                    p.setAttribute("class", "mark");
                    pi.setAttribute("value", s.points[s.students[i].id]);
                    form.appendChild(pi);

                    var hr = document.createElement("hr");
                    form.appendChild(hr);

                    cs.appendChild(form);
                    r.appendChild(cs);
                    table.appendChild(r);
                    div.appendChild(table);

                }
                var old = document.getElementById("table_j");
                if (old === null)
                {
                    document.getElementById("jrn").appendChild(table);
                } else
                    document.getElementById("jrn").replaceChild(table, old);
                var ins = table.getElementsByTagName('input');
                for (i = 0; i < ins.length; i++) {
                    ins[i].setAttribute('style', 'margin-left: 20px; display: inline-block');
                    ins[i].setAttribute('onchange', 'showSaveJ();');
                }
            }
            function showSaveJ() {
                document.getElementById("saveJ").display = block;
            }
            function submitJournal() {
                var isOk = validateJ();
                if (!isOk) {
                    alert("Only digits are available");
                } else {
                    var ids = document.getElementsByClassName("studentId");
                    var marks = document.getElementsByClassName("mark");
                    var journal = "journal={\"id\": \"" + document.getElementById("jourId").value + "\",";
                    var points = "points=[";
                    for (i = 0; i < ids.length; i++) {
                        points += "\"" + ids[i].value + "\": \"" + marks[i].value + "\"";
                        if (i < ids.length) {
                            points += ", ";
                        }
                    }
                    points += "]";
                    var students = "students=[";
                    for (i = 0; i < ids.length; i++) {
                        var std = "{\"id\":\"" + ids[i].value + "\"}";
                        students += std;
                        if (i < ids.length) {
                            students += ", ";
                        }
                    }
                    students += "], ";
                    journal += students;
                    journal += points;
                    journal += "}";

                    var data = JSON.stringify(journal);
                    $.ajax({
                        url: 'setJournal',
                        data: data,
                        success: function () {
                            alert("Journal have been updated");
                        },
                        error: function () {
                            alert("Error");
                        }
                    });
                }
            }
            function validateJ() {
                var fields = document.getElementsByClassName("marks");
                var flag = true;
                for (i = 0; i < fields.length; i++) {
                    flag = /^\d+$/.test(fields[i].value);
                }
                return flag;
            }
</script>
<script>
    var jmodal = document.getElementById('journal');
// Get the button that opens the modal
// Get the <span> element that closes the modal
    var jspan = document.getElementById("closeJ");



// When the user clicks on <span> (x), close the modal
    jspan.onclick = function () {
        jmodal.style.display = "none";
    }


// When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
        if (event.target == jmodal) {
            jmodal.style.display = "none";
        }

    }
</script>