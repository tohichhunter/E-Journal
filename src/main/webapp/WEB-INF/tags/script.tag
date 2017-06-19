<%-- 
    Document   : script
    Created on : 31-May-2017, 20:17:49
    Author     : Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="message"%>

<%-- any content can be specified here e.g.: --%>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
    function getCourses()
    {
        var select = document.getElementById("sort");
        var v = select.options[select.selectedIndex].value;
        var s;
        $.ajax({
            url: 'getCourses',
            data: $.param({sort: v}),
            success: function (data) {
                s = $.parseJSON(data);
                addCourses(s);
            },
            error: function () {
                alert("Error");
            }
        });
    }
    function addCourses(s)
    {
        var role = document.getElementById("role").value;
        var div = document.getElementById('crs');
        var saveC = document.createElement('button');
        saveC.setAttribute("id","saveC");
        saveC.style.display = "none";
        saveC.textContent = "${sessionScope.resourceBundle.locale_save}";
        div.appendChild(saveC);
        var table = document.createElement('table');
        table.setAttribute('id', "table_c");
        table.setAttribute('style', 'width: 100%');
        for (i = 0; i < s.length; i++)
        {
            var r = document.createElement("tr");
            r.setAttribute('style', 'width:100%');
            var cs = document.createElement("td");
            cs.setAttribute('style', 'width: 100%');
            var form = document.createElement("form");
            form.setAttribute("class", "form_c");
            form.setAttribute("action","removeCourse");



            var ni = document.createElement("input");
            ni.setAttribute("type", "text");
            ni.setAttribute("placeholder", "${sessionScope.resourceBundle.locale_course_name}");
            ni.setAttribute("value", s[i].name);
            if(role != 'admin'){
                ni.setAttribute("readonly", "readonly");
            } 
            form.appendChild(ni);

            var ti = document.createElement("input");
            ti.setAttribute("type", "text");
            ti.setAttribute("placeholder", "${sessionScope.resourceBundle.locale_course_theme}");
            ti.setAttribute("value", s[i].theme);
            if(role !== 'admin'){
                ti.setAttribute("readonly", "readonly");
            }
            form.appendChild(ti);

            var ci = document.createElement("input");
            ci.setAttribute("type", "number");
            ci.setAttribute("readonly", "readonly");
            ci.setAttribute("placeholder", "${sessionScope.resourceBundle.locale_capacity}");
            ci.setAttribute("value", s[i].capacity);
            form.appendChild(ci);

            var bi = document.createElement("input");
            bi.setAttribute("type", "date");
            bi.setAttribute("placeholder", "${sessionScope.resourceBundle.locale_beginning}");
            if (s[i].beginning === null)
            {
                bi.setAttribute("value", "");
            } else {
                bi.setAttribute("value", s[i].beginning);
            }
            if(role !== 'admin'){
                bi.setAttribute("readonly", "readonly");
            }
            form.appendChild(bi);

            var ei = document.createElement("input");
            ei.setAttribute("type", "date");
            ei.setAttribute("placeholder", "${sessionScope.resourceBundle.locale_ending}");
            if (s[i].ending === null)
            {
                ei.setAttribute("value", "");
            } else {
                ei.setAttribute("value", s[i].ending);
            }
            if(role !== 'admin'){
                ei.setAttribute("readonly", "readonly");
            }
            form.appendChild(ei);

            var ii = document.createElement("input");
            ii.setAttribute("type", "hidden");
            ii.setAttribute("name","toRemove");
            ii.setAttribute("value", s[i].id);
            form.appendChild(ii);

            var tci = document.createElement("input");
            if(role !== 'admin'){
            tci.setAttribute("type", "hidden");
        } else {
            tci.setAttribute("type", "text");
        }
        tci.setAttribute("placeholder", "${sessionScope.resourceBundle.locale_course_teacher}");
            tci.setAttribute("value", s[i].teacherId);
            form.appendChild(tci);

            
            
            var userId = document.getElementById("user_id").value;
            if(role === 'student'){
                var sb = document.createElement("button");
                sb.textContent = '${sessionScope.resourceBundle.locale_subscribe}';
                sb.setAttribute("value", s[i].id );
                sb.setAttribute("onclick",'subscribeStudent('+ userId +',this);');
                form.appendChild(sb);
            }
            
                if(role === 'admin'){
                    var remove = document.createElement("input");
                    remove.setAttribute("type","submit");
                    remove.setAttribute("value","${sessionScope.resourceBundle.locale_remove}");
                    form.appendChild(remove);
                }
            
            var hr = document.createElement("hr");
            form.appendChild(hr);
            

            cs.appendChild(form);
            r.appendChild(cs);
            table.appendChild(r);
            div.appendChild(table);

        }
        var old = document.getElementById("table_c");
        if (old === null)
        {
            document.getElementById("crs").appendChild(table);
        } else
            document.getElementById("crs").replaceChild(table, old);
        var ins = document.getElementsByTagName('input');
        for (i = 0; i < ins.length; i++) {
            ins[i].setAttribute('style', 'margin-left: 20px; display: inline-block');
            ins[i].setAttribute('onchange', 'showSaveC();');
        }
        ;
    }
</script>
<script>
    $(document).ready(function () {
        document.getElementById('saveC').style.display = "none";
    });
    function showSaveC() {
        var role = document.getElementById('role').value;
        if (role === 'admin')
            document.getElementById('saveC').style.display = "block";
    }
    function showSaveT() {
        var role = document.getElementById('role').value;
        if (role === 'admin')
            document.getElementById('saveT').style.display = "block";
    }
    function submitCourses() {
        var forms = document.getElementsByClassName("form_c");
        var jsonstring = "crs=[";
        for (i = 0; i < forms.length; i++) {
            var name = forms[i].elements[0].value;
            var theme = forms[i].elements[1].value;
            var capacity = forms[i].elements[2].value;
            var beginning = forms[i].elements[3].value;
            var ending = forms[i].elements[4].value;
            var id = forms[i].elements[5].value;
            var teacherId = forms[i].elements[6].value;
            if(!/^\d+$/.test(teacherId)){
                alert("${sessionScope.resourceBundle.locale_digits_allowed}");
                return;
            }
            var jstr = "{ \"id\" : \"" + id + "\", \"name\" : \"" + name + "\", \"theme\" : \"" + theme +
                    "\", \"teacherId\" : \"" + teacherId + "\", \"capacity\" : \"" + capacity +
                    "\", \"beginning\" : \"" + beginning + "\", \"ending\" : \"" + ending + "\"}";
            jsonstring += jstr;
            if (i < forms.length - 1)
                jsonstring += ",";
        }
        jsonstring += "]";

        $.ajax({
            url: 'setCourses',
            data: jsonstring,
            success: function () {
                getCourses();
            },
            error: function () {
                alert("Error");
            }
        });
        
    }
</script>
<script>
    var modal = document.getElementById('coursesList');
    var modal2 = document.getElementById('courseAdd');
    var modal3 = document.getElementById('teachersList');
    var modal4 = document.getElementById('teacherAdd');
// Get the button that opens the modal
    var btn = document.getElementById("sort");
    var btn2 = document.getElementById("newCourse");
    var btn3 = document.getElementById("show_t");
    var btn4 = document.getElementById("newTeacher");
// Get the <span> element that closes the modal
    var span = document.getElementById("closeC");
    var span2 = document.getElementById("closeNewC");
    var span3 = document.getElementById("closeT");
    var span4 = document.getElementById("closeNewT");
// When the user clicks on the button, open the modal
    btn.onchange = function () {
        modal.style.display = "block";
        getCourses();
    }

    btn2.onclick = function () {
        modal2.style.display = "block";
    }

    btn3.onclick = function () {
        modal3.style.display = "block";
        getTeachers();
    }

    btn4.onclick = function () {
        modal4.style.display = "block";
    }

// When the user clicks on <span> (x), close the modal
    span.onclick = function () {
        modal.style.display = "none";
    }
    span2.onclick = function () {
        modal2.style.display = "none";
    }

    span3.onclick = function () {
        modal3.style.display = "none";
    }

    span4.onclick = function () {
        modal4.style.display = "none";
    }


// When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
        if (event.target == modal2) {
            modal2.style.display = "none";
        }
        if (event.target == modal3) {
            modal3.style.display = "none";
        }
        if (event.target == modal4) {
            modal4.style.display = "none";
        }
    }
</script>
<script>
    function validateC() {
        return !(
                document.getElementById("newNameC").value === "" ||
                document.getElementById("newThemeC").value === "" ||
                document.getElementById("newTeacherC").value === "" ||
                document.getElementById("newBeginingC").value === "" ||
                document.getElementById("newEndingC").value === "");
    }
</script>
<script>
    function submitCourse() {
        var isOk = validateC();
        if (!isOk) {

            alert("${sessionScope.resourceBundle.locale_required}");
        } else {
            var name = document.getElementById("newNameC").value;
            var theme = document.getElementById("newThemeC").value;
            var teacherId = document.getElementById("newTeacherC").value;
            var beginning = document.getElementById("newBeginingC").value;
            var ending = document.getElementById("newEndingC").value;
            var jstr = "crs={ \"name\" : \"" + name + "\", \"theme\" : \"" + theme +
                    "\", \"teacherId\" : \"" + teacherId +
                    "\", \"beginning\" : \"" + beginning + "\", \"ending\" : \"" + ending + "\"}";
            $.ajax({
                url: 'newCourse',
                data: jstr,
                success: function () {
                    alert("OK");
                },
                error: function () {
                    alert("${sessionScope.resourceBundle.locale_error}");
                }
            });
        }
    }
</script>
<script>
    function getTeachers()
    {
        var s;
        $.ajax({
            url: 'getTeachers',
            success: function (data) {
                s = $.parseJSON(data);
                addTeachers(s);
            },
            error: function () {
                alert("${sessionScope.resourceBundle.locale_error}");
            }
        });
    }
    function addTeachers(s)
    {
        var div = document.getElementById('tcs');
        var table = document.createElement('table');
        table.setAttribute('id', "table_t");
        table.setAttribute('style', 'width: 100%');
        for (i = 0; i < s.length; i++)
        {
            var r = document.createElement("tr");
            r.setAttribute('style', 'width:100%');
            var cs = document.createElement("td");
            cs.setAttribute('style', 'width: 100%');
            var form = document.createElement("form");
            form.setAttribute("class", "form_t");



            var fi = document.createElement("input");
            fi.setAttribute("type", "text");
            fi.setAttribute("value", s[i].firstName);
            form.appendChild(fi);

            var li = document.createElement("input");
            li.setAttribute("type", "text");
            li.setAttribute("value", s[i].lastName);
            form.appendChild(li);

            var ii = document.createElement("input");
            ii.setAttribute("type", "text");
            ii.setAttribute("readonly", "readonly");
            ii.setAttribute("value", s[i].id);
            form.appendChild(ii);

            var sl = document.createElement("select");
            for (j = 0; j < s[i].courses.length; j++) {
                var co = document.createElement("option");
                co.text = s[i].courses[j].name;
                sl.add(co);
            }
            form.appendChild(sl);

            var hr = document.createElement("hr");
            form.appendChild(hr);

            cs.appendChild(form);
            r.appendChild(cs);
            table.appendChild(r);
            div.appendChild(table);

        }
        var old = document.getElementById("table_t");
        if (old === null)
        {
            document.getElementById("tcs").appendChild(table);
        } else
            document.getElementById("tcs").replaceChild(table, old);
        var ins = document.getElementsByTagName('input');
        for (i = 0; i < ins.length; i++) {
            ins[i].setAttribute('style', 'margin-left: 20px; display: inline-block');
            ins[i].setAttribute('onchange', 'showSaveT();');
        }
        var sls = document.getElementsByTagName('select');
        for (i = 0; i < ins.length; i++) {
            sls[i].setAttribute('style', 'margin-left: 20px; display: inline-block');
        }
    }
</script>
<script>
    function validateT() {
        if (document.getElementById("newFirstNameT").value === "" ||
                document.getElementById("newLastNameT").value === "" ||
                document.getElementById("newLoginT").value === "" ||
                document.getElementById("newPasswordT").value === "" ||
                document.getElementById("newRePasswordT").value === "")
        {
            alert("${sessionScope.resourceBundle.locale_required}");
            return false;
        } else
        if (document.getElementById("newPasswordT").value !==
                document.getElementById("newRePasswordT").value)
        {
            alert("${sessionScope.resourceBundle.locale_dont_match}");
            return false;
        } else
        {
            return true;
        }
    }
</script>
<script>
    function submitTeacher() {
        var isOk = validateT();
        if (!isOk) {

            return;
        } else {
            var firstName = document.getElementById("newFirstNameT").value;
            var lastName = document.getElementById("newLastNameT").value;
            var login = document.getElementById("newLoginT").value;
            var password = document.getElementById("newRePasswordT").value;
            var jstr = "tcs={ \"firstName\" : \"" + firstName + "\", \"lastName\" : \"" + lastName +
                    "\", \"login\" : \"" + login +
                    "\", \"password\" : \"" + password + "\"}";
            $.ajax({
                url: 'newTeacher',
                data: jstr,
                success: function () {
                    alert("OK");
                },
                error: function () {
                    alert("${sessionScope.resourceBundle.locale_error}");
                }
            });
        }
    }
</script>
<script>
    function submitTeachers() {
        var forms = document.getElementsByClassName("form_t");
        var jsonstring = "tcs=[";
        for (i = 0; i < forms.length; i++) {
            var firstName = forms[i].elements[0].value;
            var lastName = forms[i].elements[1].value;
            var id = forms[i].elements[2].value;
            var jstr = "{\"id\" : \"" + id + "\", \"firstName\" : \"" + firstName +
                    "\", \"lastName\" : \"" + lastName + "\"}";
            jsonstring += jstr;
            if (i < forms.length - 1)
                jsonstring += ",";
        }
        jsonstring += "]";

        $.ajax({
            url: 'setTeachers',
            data: jsonstring,
            success: function () {
                getTeachers();
            },
            error: function () {
                alert("${sessionScope.resourceBundle.locale_error}");
            }
        });
    }
</script>
<script>
    function whiteBring(){
        document.getElementById("svcrd").removeAttribute("disabled");
    }
    function CONFPASS(){
        document.getElementById("cfpswd").style.display="block";    
    }
    
    function validateCred() {
                var inpt = document.getElementById("updateCred").elements;
                for (i = 0; i < inpt.length-1; i++) {
                    if (inpt[i].value === "") {
                        inpt[i].style.color = "CORAL";
                        alert("${sessionScope.resourceBundle.locale_required}");
                        return false;
                    }
                }
                if (inpt[3].value.length < 6) {
                    alert("${sessionScope.resourceBundle.locale_length}");
                    return false;
                }

                if (inpt[3].value !== inpt[4].value) {
                    alert("${sessionScope.resourceBundle.locale_dont_match}");
                    return false;
                }                
            }
            
            function Bring(el) {
                el.style.color = "white";
                whiteBring();
            }
</script>

