<%-- 
    Document   : journal
    Created on : 05-Jun-2017, 17:41:39
    Author     : Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${sessionScope.resourceBundle.locale_journal}</title>
        <link href="https://v4-alpha.getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="https://v4-alpha.getbootstrap.com/examples/carousel/carousel.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    </head>
    <body>
        <c:if test="${sessionScope.role != 'teacher'}">
            <c:redirect url="cabinet.jsp"/>
        </c:if>
        <nav class="navbar navbar-toggleable-md navbar-inverse fixed-top bg-inverse">
            <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <a class="navbar-brand"  href="cabinet.jsp">${sessionScope.resourceBundle.locale_back}</a>
            <ul>${sessionScope.firstName} ${sessionScope.resourceBundle.locale_authorised} ${sessionScope.role}
            </ul>
        </nav>

        <div id="myCarousel" class="carousel slide" data-ride="carousel" data-interval="false">
            <div class="carousel-inner" role="listbox">
                <div class="carousel-item active">
                    <img class="first-slide" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" alt="First slide">
                    <div class="container">
                        <div class="carousel-caption d-none d-md-block text-left">
                            <h1>${sessionScope.resourceBundle.locale_journal}</h1> 
                            <form id="f1" action="setJournal" method="POST" onsubmit="return validateJ()">
                                <input name="id" type="hidden" value="${sessionScope.journal.id}"/>
                                <c:forEach var="student" items="${sessionScope.journal.students}">     
                                    <input type="text" readonly="readonly" value="${student.firstName} ${student.lastName}"/>
                                    <input class="marks" name="${student.id}" value="${sessionScope.journal.points[student.id]}"/><br/>
                                </c:forEach>
                                    <input type="submit" value="${sessionScope.resourceBundle.locale_submit}"/>
                            </form>
                        </div>
                    </div>
                </div>        
            </div>
            <script>
                function validateJ() {
                    var fields = document.getElementsByClassName("marks");
                    var flag = true;
                    for (i = 0; i < fields.length; i++) {
                        flag = /^\d+$/.test(fields[i].value);
                    }
                    if(!flag){
                        alert("${sessionScope.resourceBundle.locale_digits_allowed}");
                        return false;
                    };
                }
            </script>
    </body>
</html>
