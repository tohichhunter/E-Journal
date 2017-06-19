<%-- 
    Document   : login
    Created on : 26-May-2017, 18:35:12
    Author     : Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login page</title>
        <link href="https://v4-alpha.getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://v4-alpha.getbootstrap.com/examples/signin/signin.css">
    </head>
    <body>
        <c:if test="${sessionScope.role != null}">
            <c:redirect url="cabinet.jsp"/>
        </c:if>
        <div class="container">
            <h2 class="form-signin-heading">Login page</h2>
            <table>
                <form  class="form-signin" id="loginForm" action="auth" method="POST">
                    <tr><td>
                            <input class="form-control" type="text" name="login" placeholder="Login" onchange="white(this);" required autofocus/>
                        </td></tr>
                    <tr><td>
                            <br/>
                        </td></tr>
                    <tr><td>
                            <input class="form-control" type="password" name="password" placeholder="Password" onchange="white(this);"/>
                        </td></tr>
                    <tr><td>
                            <br/>
                        </td></tr>
                    <tr><td>
                            <button class="btn btn-lg btn-primary btn-block" onclick="validate();">Submit</button>
                        </td></tr>
                </form>
                <tr><td>
                        <br>
                    </td></tr>
                <tr>
                    <td>
                        <a href="registration.jsp">Sign up</a>
                    </td>
                </tr>
            </table>

        </div>
        <script type="text/javascript">
            function validate() {
                var inpt = document.getElementsByTagName("input");
                var flag = true;
                for (i = 0; i < inpt.length; i++) {
                    if (inpt[i].value === "") {
                        inpt[i].style.color = "CORAL";
                        flag = false;
                    }
                }
                if (flag) {
                    document.getElementById("loginForm").submit();
                } else {
                    alert("All fields are required!");
                }
            }
            function white(el) {
                el.style.color = "white";
            }
        </script>
    </body>
</html>
