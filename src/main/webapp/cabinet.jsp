<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ar" uri="WEB-INF/tlds/style.tld" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="art" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="Anton Rumiantsev" >

        <title>User's cabinet</title>

        <!-- Bootstrap core CSS -->
        <link href="https://v4-alpha.getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="https://v4-alpha.getbootstrap.com/examples/carousel/carousel.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <ar:Style/>
    </head>
    <body>
        <input type="hidden" id="user_id" value="${sessionScope.userId}"/>
        <c:choose>
            <c:when test="${sessionScope.role == 'admin'}">
                <jsp:include page="WEB-INF/jsp/admin.jsp"/>
            </c:when>
            <c:when test="${sessionScope.role == 'teacher'}">
                <jsp:include page="WEB-INF/jsp/teacher.jsp"/>
            </c:when>
             <c:when test="${sessionScope.role == 'student'}">
                <jsp:include page="WEB-INF/jsp/student.jsp"/>
            </c:when>
            <c:otherwise>
                <c:redirect url="login.jsp" />
            </c:otherwise>
        </c:choose> 

    </body>
</html>
