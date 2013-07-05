<%--
  Created by IntelliJ IDEA.
  User: Margaret
  Date: 6/4/13
  Time: 2:04 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Research study: Context-sensitive semantic relatedness</title>
    <meta name="layout" content="main"/>
    <r:require modules="core" />
    <r:script>

    // from http://stackoverflow.com/questions/46155/validate-email-address-in-javascript
    function validateEmail(email) {
        var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(email);
    }
    $(document).ready(function () {
                $("#consent-text").scrollTop(1000);
                $("#consent-text").animate({scrollTop : 0});

                $("form").on("submit", function() {
                    var email = $("#email").val();
                    if (email && validateEmail(email)) {
                        return true;
                    } else {
                        alert('please enter a valid email');
                        return false;
                    }
                });
            });
    </r:script>
</head>
<body>
<div class= "consent rounded-corners" id="main-container">
    <h1>Research study: Context-sensitive semantic relatedness</h1>
<g:form controller="Interest" action="interest" name = "consent-form" method="post">
    <table>
        <tr>
            <td colspan="3">
                <div id="consent-text">
                    <h4>Description of the Research</h4>
                    You are invited to participate in a research study that examines whether different groups
                    interpret phrases differently. This study is open to all internet users over 18.

                    <h4>What will my participation involve?</h4>
                    If you decide to participate in this research you will be asked to rate the similarity of a
                    series of phrase pairs (e.g. How related are "race car" and "engine?").
                    Your participation in the study will require approximately 10 minutes.

                    <h4>Principal Investigators:</h4>
                    Shilad Sen (<a href="mailto:ssen@macalester.edu">ssen@macalester.edu</a>)<br/>
                    Brent Hecht (<a href="mailto:bhecht@cs.umn.edu">bhecht@cs.umn.edu</a>)<br/>

                    <h4>Are there any risks to me?</h4>
                    There are no significant risks associated with this survey.

                    <h4>Are there any benefits to me?</h4>
                    We don't expect any direct benefits to you from participation in this study.

                    <h4>How will my confidentiality be protected?</h4>
                    While there will probably be publications as a result of this study, your name will not be used. Only group characteristics will be published.

                    <h4>Whom should I contact if I have any questions?</h4>
                    If you have questions about the research you should contact the Principal Investigator Assistant Prof. Shilad Sen at 651-696-6273.
                    Your participation is completely voluntary.

                </div>

            </td>
        </tr>
        <tr class="bottom">
            <td>
                <b>I am 18 or over and would like to participate.</b>
            </td>
            <td>
                <label for="email">email:</label>
                <input type="text" id="email" name="email" class="myInput" value="${person.email?.encodeAsHTML()}">
            </td>
            <td>
                <label for="continue"></label><button id="continue" name="continue" class="myButton">Go!</button>
            </td>
        </tr>
    </table>
</g:form>
</div>
</body>
</html>