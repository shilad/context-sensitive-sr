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
    <title>Macademia survey: Context-sensitive semantic relatedness</title>
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
    <h1>Macademia survey: Context-sensitive semantic relatedness</h1>
<g:form controller="consent" action="save" name = "consent-form" method="post">
    <table>
        <tr>
            <td colspan="3">
                <div id="consent-text">
                    <h4>Overview</h4>
                    We invite you to participate in a research study that measures differences in how individuals perceive the strength of relationships between concepts.
                    This study will advance scholarly knowledge in the field of computational linguistics and improve the algorithms powering the Macademia website.
                    This study is open to all Internet users over 18.

                    <h4>What will my participation involve?</h4>
                    Your participation in the study will require approximately 15 minutes.
                    If you decide to participate in this research you will be asked to rate the relatedness of a series of concept pairs (e.g. How related are "race car" and "engine?").

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

                    <h4>Institutional Oversight</h4>
                    This study has been approved by Macalester College's Institutional Review Board (IRB),
                    and is funded in part by National Science Foundation grant grant IIS-0964697.
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