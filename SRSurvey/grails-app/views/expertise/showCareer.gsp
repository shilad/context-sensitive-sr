<%--
  Created by IntelliJ IDEA.
  User: research
  Date: 6/20/13
  Time: 10:45 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Career</title>
    <meta name="layout" content="main"/>
    <r:require modules="core" />

</head>
<body>
<div class= "rounded-corners expertise" id="main-container">
    <g:form action="saveCareer">
    <h1>Career</h1>
    <div>
        <p>Do you conduct scholarly research as part of your job?
        For example, do you publish articles or conduct scientific experiments?
        </p>
        <br/>
    </div>
    <div>
        <g:radioGroup labels="${['yes', 'no']}" values="${[true, false]}" name="scholar">
            <label>${it.label}:${it.radio}</label>&nbsp;&nbsp;&nbsp;
        </g:radioGroup>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<g:submitButton name="next" class="myButton"/>
        <br/>
        <br/>
    </div>
    </g:form>
    <div class="error">
        Please select "yes" or "no" to continue.
    </div>
</div>
</body>
</html>

<r:script>
    $(document).ready(function () {
        $("form").submit(function () {
            if ($("input:checked").length == 0) {
                $(".error").show();
                return false;
            } else {
                $(".error").hide();
                return true;
            }
        });
    });
</r:script>