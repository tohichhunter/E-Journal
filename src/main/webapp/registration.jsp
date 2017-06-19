<%-- 
    Document   : registration
    Created on : 26-May-2017, 18:35:12
    Author     : Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration Page</title>
        <link href="https://v4-alpha.getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://v4-alpha.getbootstrap.com/examples/signin/signin.css">
    </head>
    <body>
        <c:if test="${sessionScope.role != null}">
            <c:redirect url="cabinet.jsp"/>
        </c:if>
        <div class="container">
            <h2 class="form-signin-heading">Enter credentials: </h2>
            <table>
                <form  class="form-signin" id="registrationForm" action="registrate" method="POST" onsubmit="return validate()">
                    <tr><td>
                            <input class="form-control" type="text" name="firstName" placeholder="first name" onchange="white(this);" required autofocus/>
                        </td></tr>
                    <tr><td>
                            <br/>
                        </td></tr>
                    <tr><td>
                            <input class="form-control" type="text" name="lastName" placeholder="last name" onchange="white(this);" required autofocus/>
                        </td></tr>
                    <tr><td>
                            <br/>
                        </td></tr>
                    <tr><td>
                            <input class="form-control" type="text" name="login" placeholder="login" onchange="white(this);" required autofocus/>
                        </td></tr>
                    <tr><td>
                            <br/>
                        </td></tr>
                    <tr><td>
                            <input class="form-control" type="password" name="password" placeholder="password" onchange="white(this);"/>
                        </td></tr>
                    <tr><td>
                            <br/>
                        </td></tr>
                    <tr><td>
                            <input class="form-control" type="password" name="confirmPassword" placeholder="confirm password" onchange="white(this);"/>
                        </td></tr>
                    <tr><td>
                            <br/>
                        </td></tr>
                    <tr><td>
                            <input class="btn btn-lg btn-primary btn-block" type="submit" value="Submit"/>
                        </td></tr>
                </form>
                <tr><td>
                        <br>
                    </td></tr>
                <tr>
                    <td>
                        <a href="login.jsp">Already registered</a>
                    </td>
                </tr>
            </table>

        </div>
        <script type="text/javascript">
            function validate() {
                var inpt = document.getElementsByTagName("input");
                for (i = 0; i < inpt.length; i++) {
                    if (inpt[i].value === "") {
                        inpt[i].style.color = "CORAL";
                        alert("All fields are required");
                        return false;
                    }
                }
                if (inpt[3].value.length < 6) {
                    alert("Password requires at least 6 chars");
                    return false;
                }

                if (inpt[3].value !== inpt[4].value) {
                    alert("Passwords don't match");
                    return false;
                }
               
            }
            function white(el) {
                el.style.color = "white";
            }
        </script>
    </body>
</html>
