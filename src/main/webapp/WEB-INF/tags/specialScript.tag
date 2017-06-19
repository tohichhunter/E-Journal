<%-- 
    Document   : specialScript
    Created on : 04-Jun-2017, 21:33:23
    Author     : Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="message"%>

<%-- any content can be specified here e.g.: --%>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
    function getMyCourses(){
     var specselect = document.getElementById("specsort");
        var v = specselect.options[specselect.selectedIndex].value;
        var s;
        $.ajax({
            url: 'getMyCourses',
            data: $.param({sort: v}),
            success: function (data) {
                s = $.parseJSON(data);
                addSpecialCourses(s);
            },
            error: function () {
                alert("Error");
            }
        });
    }
     function addSpecialCourses(s)
    {
        var role = document.getElementById("role").value;
        var div = document.getElementById('scrs');
        var table = document.createElement('table');
        table.setAttribute('id', "table_sc");
        table.setAttribute('style', 'width: 100%');
        for (i = 0; i < s.length; i++)
        {
            var r = document.createElement("tr");
            r.setAttribute('style', 'width:100%');
            var cs = document.createElement("td");
            cs.setAttribute('style', 'width: 100%');
            var form = document.createElement("form");
            form.setAttribute("class", "form_c");



            var ni = document.createElement("input");
            ni.setAttribute("type", "text");
            ni.setAttribute("value", s[i].name);
            if(role != 'admin'){
                ni.setAttribute("readonly", "readonly");
            }
            form.appendChild(ni);

            var ti = document.createElement("input");
            ti.setAttribute("type", "text");
            ti.setAttribute("value", s[i].theme);
            if(role != 'admin'){
                ti.setAttribute("readonly", "readonly");
            }
            form.appendChild(ti);

            var ci = document.createElement("input");
            ci.setAttribute("type", "number");
            ci.setAttribute("readonly", "readonly");
            ci.setAttribute("value", s[i].capacity);
            form.appendChild(ci);

            var bi = document.createElement("input");
            bi.setAttribute("type", "date");
            if (s[i].beginning === null)
            {
                bi.setAttribute("value", "");
            } else {
                bi.setAttribute("value", s[i].beginning);
            }
            if(role != 'admin'){
                bi.setAttribute("readonly", "readonly");
            }
            form.appendChild(bi);

            var ei = document.createElement("input");
            ei.setAttribute("type", "date");
            if (s[i].ending === null)
            {
                ei.setAttribute("value", "");
            } else {
                ei.setAttribute("value", s[i].ending);
            }
            if(role != 'admin'){
                ei.setAttribute("readonly", "readonly");
            }
            form.appendChild(ei);

            <c:choose>
                <c:when test="${role == 'teacher'}"> 
            var ii = document.createElement("input");
            ii.setAttribute("type", "hidden");
            ii.setAttribute("value", s[i].id);
            form.appendChild(ii);
                </c:when>
                <c:otherwise>
            var ii = document.createElement("input");
            ii.setAttribute("type", "text");
            ii.setAttribute("readonly","readonly");
            ii.setAttribute("value", s[i].id);
            form.appendChild(ii);
                </c:otherwise>
            </c:choose>

            var tci = document.createElement("input");
            tci.setAttribute("type", "hidden");
            tci.setAttribute("value", s[i].teacherId);
            form.appendChild(tci);

            
            if(role === 'teacher'){
                var p = document.createElement("p");
                var jb = document.createElement("a");
                jb.textContent= '${sessionScope.resourceBundle.locale_get_journal}';
                jb.setAttribute("class", "btn btn-lg btn-primary");
                jb.setAttribute("href", "getJournal?id="+s[i].id);
                p.appendChild(jb);
                form.appendChild(p);
            }
            
            
            var hr = document.createElement("hr");
            form.appendChild(hr);
            

            cs.appendChild(form);
            r.appendChild(cs);
            table.appendChild(r);
            div.appendChild(table);

        }
        var old = document.getElementById("table_sc");
        if (old === null)
        {
            document.getElementById("scrs").appendChild(table);
        } else
            document.getElementById("scrs").replaceChild(table, old);
        
    }
</script>
<script>
    var specmodal = document.getElementById('specialcoursesList');
// Get the button that opens the modal
    var specbtn = document.getElementById("specsort");
// Get the <span> element that closes the modal
    var specspan = document.getElementById("closeSC");
// When the user clicks on the button, open the modal
    specbtn.onchange = function () {
        specmodal.style.display = "block";
        getMyCourses();
    }

   

// When the user clicks on <span> (x), close the modal
    specspan.onclick = function () {
        specmodal.style.display = "none";
    }
   

// When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
        if (event.target == specmodal) {
            specmodal.style.display = "none";
        }
        
    }
</script>