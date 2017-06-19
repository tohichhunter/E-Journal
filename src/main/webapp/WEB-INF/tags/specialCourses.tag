<%-- 
    Document   : specialCourses
    Created on : 04-Jun-2017, 15:34:42
    Author     : Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="role"%>

<%-- any content can be specified here e.g.: --%>
<h1>${sessionScope.resourceBundle.locale_special}</h1>
<c:choose>
    <c:when test="${role == 'teacher'}">
        <select id="specsort" onchange="getMyCourses();">
            <option value=""></option>
            <option value="az">AZ</option>
            <option value="za">ZA</option>            
            <option value="capacity">${sessionScope.resourceBundle.locale_capacity}</option>
            <option value="term">${sessionScope.resourceBundle.locale_term}</option>
        </select>
    </c:when>
    <c:otherwise>

        <select id="specsort" onchange="getMyCourses();">
            <option value=""></option>
            <option value="on">${sessionScope.resourceBundle.locale_oncoming}</option>
            <option value="in">${sessionScope.resourceBundle.locale_inprogress}</option>            
            <option value="fin">${sessionScope.resourceBundle.locale_finished}</option>
        </select>
    </c:otherwise>
</c:choose>
