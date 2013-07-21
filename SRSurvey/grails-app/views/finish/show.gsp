<%--
  Created by IntelliJ IDEA.
  User: Margaret
  Date: 6/4/13
  Time: 10:46 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Comment</title>
    <meta name="layout" content="main"/>
    <r:require modules="core" />
</head>
<body>
<div class= "finish rounded-corners" id="main-container">
    <br>
    <h1>Finished?</h1>
    <g:form controller="finish" action="save" method="post">
        <p>
            Thank you for volunteering your time and expertise!
        </p>
        <div>
            Do you have any comments about this survey or your responses?
            <textarea rows="10" cols="80" class ="rounded-corners"  name="comments"></textarea>
        </div>
        <g:if test="${person.numAnswers() > 60}">
            <div>
                <g:submitButton name="submit" value="Save comments." class="myButton" title="Save comments.."/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </div>

        </g:if>
        <g:else>
            <div>
                Would you provide more responses to help improve Macademia? <br/>
                <g:submitButton name="submit" value="Sure! I'll complete another round of ratings." class="myButton" title="No thanks! I'm done."/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <g:submitButton name="submit" value="No thanks! I'm done." class="myButton" title="No thanks! I'm done."/>
            </div>
        </g:else>
    </g:form>
</div>
</body>
</html>