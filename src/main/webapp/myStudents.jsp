<%-- 
    Document   : myStudents
    Created on : 07-Jun-2017, 10:51:24
    Author     : Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${sessionScope.resourceBundle.locale_students}</title>
        <link href="https://v4-alpha.getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="https://v4-alpha.getbootstrap.com/examples/carousel/carousel.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    </head>
    <body>
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
                            <c:if test="${sessionScope.role != 'teacher'}">
                                <c:redirect url="cabinet.jsp"/>
                            </c:if>
                            <button id="btn">&#8194;&#9650;<br>&#8194;&#9650;&#8194;&#8194;&#9650;</button>
                        </div>
                    </div>
                </div>        
            </div>
        </div>
        <div id="studentList" class="modal">

            <!-- Modal content -->
            <div class="modal-content">
                <span class="close" id="close">&times;</span>

                <h1>${sessionScope.resourceBundle.locale_students}</h1> 
                <c:forEach var="student" items="${sessionScope.myStudents}">            
                    <c:choose >
                        <c:when test="${student.role == 'blocked'}">
                            <c:set var="action" value="unblock"/>
                            <c:set var="act" value="${sessionScope.resourceBundle.locale_unblock}"/>
                            <lable>${sessionScope.resourceBundle.locale_blocked}</label>
                            </c:when>
                            <c:otherwise>
                                <c:set var="action" value="block"/>
                                <c:set var="act" value="${sessionScope.resourceBundle.locale_block}"/>
                            </c:otherwise>
                        </c:choose>  
                         <input type="text" readonly="readonly" value="${student.firstName} ${student.lastName}"/>
                        <hr/>
                    </c:forEach>


            </div>

        </div>
        <script>
            var modal = document.getElementById("studentList");
            var btn = document.getElementById("btn");
            var span = document.getElementById("close");
            btn.onclick = function () {
                modal.style.display = "block";
            }
            span.onclick = function () {
                modal.style.display = "none";
            }
            window.onclick = function (event) {
                if (event.target == modal) {
                    modal.style.display = "none";
                }
            }

        </script>
        <script type="text/javascript">
            $(document).ready(ajustamodal);
            $(window).resize(ajustamodal);
            function ajustamodal() {
                var altura = $(window).height() - 100; //value corresponding to the modal heading + footer
                $(".modal-content").css({"height": altura, "margin-top": 40, "overflow-y": "auto"});
            }
        </script>
    </body>
</html>