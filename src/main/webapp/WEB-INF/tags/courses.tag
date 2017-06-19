<%-- 
    Document   : courses
    Created on : 01-Jun-2017, 14:30:57
    Author     : Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="message"%>

<%-- any content can be specified here e.g.: --%>
 <h1>${sessionScope.resourceBundle.locale_courses}</h1>
<table>    
    <tr>
        <td>
            <select id="sort" >
                <option value=""></option>
                <option value="az">AZ</option>
                <option value="za">ZA</option>            
                <option value="capacity">${sessionScope.resourceBundle.locale_capacity}</option>
                <option value="term">${sessionScope.resourceBundle.locale_term}</option>
            </select>
        </td>
        <td style="margin-left: 50px">
            <c:if test="${sessionScope.role != 'admin'}" >
                <c:set var="type" value="none"/>
            </c:if>
                <button id="newCourse" style="display:${type}" s>+</button>
            
        </td>
    </tr>                                
</table>   