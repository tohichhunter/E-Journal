<%-- 
    Document   : teachers
    Created on : 01-Jun-2017, 17:56:55
    Author     : Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="message"%>

<%-- any content can be specified here e.g.: --%>
<h1>${sessionScope.resourceBundle.locale_teachers}</h1>
<table>    
    <tr>
        <td>
            <button id="show_t" >${sessionScope.resourceBundle.locale_show_teachers}</button>
        </td>
        <td style="margin-left: 50px">
            <button id="newTeacher" >+</button>
        </td>
    </tr>                                
</table>   