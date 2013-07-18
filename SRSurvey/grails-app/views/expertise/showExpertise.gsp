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
    <title>Expertise</title>
    <meta name="layout" content="main"/>
    <r:require modules="core" />

</head>
<body>
<div class= "rounded-corners expertise" id="main-container">
    <br>
    <h1>Area of Study</h1>
    <br/><br/>
    <div class="margin" style="width: 69%;margin: auto;">
        <p>Please enter up to three of your fields of expertise, in order of preference:</p>
        <br/><br/>
        <g:form controller="expertise" action="saveExpertise" method="post">
            <table width="100%" style="border-collapse: collapse;">
                <tr class="padding" id="primary">
                    <td>
                        <h2>Primary:</h2>
                    </td>
                    <td>
                        <select name="primary" id="select1">
                            <option selected disabled="true" value="none">Please Select An Option</option>
                            <option value='Anthropology'>Anthropology</option>
                            <option value='Archaeology'>Archaeology</option>
                            <option value='Astronomy'>Astronomy</option>
                            <option value='Biology'>Biology</option>
                            <option value='Business'>Business</option>
                            <option value='Chemistry'>Chemistry</option>
                            <option value='Classics'>Classics</option>
                            <option value='Computer_Science'>Computer Science</option>
                            <option value='Criminal_Justice'>Criminal Justice</option>
                            <option value='Cultural_Studies'>Cultural Studies</option>
                            <option value='Dance'>Dance</option>
                            <option value='Economics'>Economics</option>
                            <option value='Education_Studies'>Education Studies</option>
                            <option value='Engineering'>Engineering</option>
                            <option value='English'>English</option>
                            <option value='Environmental_Studies'>Environmental Studies</option>
                            <option value='Finance'>Finance</option>
                            <option value='Foreign_Languages'>Foreign Languages</option>
                            <option value='Gender_and_Sexuality_Studies'>Gender and Sexuality Studies</option>
                            <option value='Geography'>Geography</option>
                            <option value='Geology'>Geology</option>
                            <option value='Health_Sciences'>Health Sciences</option>
                            <option value='History'>History</option>
                            <option value='Law'>Law</option>
                            <option value='Linguistics'>Linguistics</option>
                            <option value='Mathematics'>Mathematics</option>
                            <option value='Media_Studies'>Media Studies</option>
                            <option value='Music'>Music</option>
                            <option value='Neuroscience'>Neuroscience</option>
                            <option value='Other'>Other</option>
                            <option value='Philosophy'>Philosophy</option>
                            <option value='Physical_Ed._Recreation_&_Athletics'>Physical Ed. Recreation & Athletics</option>
                            <option value='Physics'>Physics</option>
                            <option value='Political_Science'>Political Science</option>
                            <option value='Psychology'>Psychology</option>
                            <option value='Religious_Studies'>Religious Studies</option>
                            <option value='Sociology'>Sociology</option>
                            <option value='Theatre_Arts'>Theatre Arts</option>
                            <option value='Visual_Arts'>Visual Arts</option>
                            <option value="Other">Other</option>
                        </select>
                    </td>

                </tr>
                <tr class="padding" id="secondary">
                    <td>
                        <h2>Secondary:</h2>
                    </td>
                    <td>
                        <select name="secondary" id="select2">
                            <option selected value="none">Please Select An Option</option>
                            <option value='Anthropology'>Anthropology</option>
                            <option value='Archaeology'>Archaeology</option>
                            <option value='Astronomy'>Astronomy</option>
                            <option value='Biology'>Biology</option>
                            <option value='Business'>Business</option>
                            <option value='Chemistry'>Chemistry</option>
                            <option value='Classics'>Classics</option>
                            <option value='Computer_Science'>Computer Science</option>
                            <option value='Criminal_Justice'>Criminal Justice</option>
                            <option value='Cultural_Studies'>Cultural Studies</option>
                            <option value='Dance'>Dance</option>
                            <option value='Economics'>Economics</option>
                            <option value='Education_Studies'>Education Studies</option>
                            <option value='Engineering'>Engineering</option>
                            <option value='English'>English</option>
                            <option value='Environmental_Studies'>Environmental Studies</option>
                            <option value='Finance'>Finance</option>
                            <option value='Foreign_Languages'>Foreign Languages</option>
                            <option value='Gender_and_Sexuality_Studies'>Gender and Sexuality Studies</option>
                            <option value='Geography'>Geography</option>
                            <option value='Geology'>Geology</option>
                            <option value='Health_Sciences'>Health Sciences</option>
                            <option value='History'>History</option>
                            <option value='Law'>Law</option>
                            <option value='Linguistics'>Linguistics</option>
                            <option value='Mathematics'>Mathematics</option>
                            <option value='Media_Studies'>Media Studies</option>
                            <option value='Music'>Music</option>
                            <option value='Neuroscience'>Neuroscience</option>
                            <option value='Other'>Other</option>
                            <option value='Philosophy'>Philosophy</option>
                            <option value='Physical_Ed._Recreation_&_Athletics'>Physical Ed. Recreation & Athletics</option>
                            <option value='Physics'>Physics</option>
                            <option value='Political_Science'>Political Science</option>
                            <option value='Psychology'>Psychology</option>
                            <option value='Religious_Studies'>Religious Studies</option>
                            <option value='Sociology'>Sociology</option>
                            <option value='Theatre_Arts'>Theatre Arts</option>
                            <option value='Visual_Arts'>Visual Arts</option>
                            <option value="Other">Other</option>
                        </select>
                    </td>
                </tr>
                <tr class="padding">
                    <td>
                        <h3>Tertiary:</h3>
                    </td>
                    <td>
                        <select name="tertiary" id="select3">
                            <option selected value="none">Please Select An Option</option>
                            <option value='Anthropology'>Anthropology</option>
                            <option value='Archaeology'>Archaeology</option>
                            <option value='Astronomy'>Astronomy</option>
                            <option value='Biology'>Biology</option>
                            <option value='Business'>Business</option>
                            <option value='Chemistry'>Chemistry</option>
                            <option value='Classics'>Classics</option>
                            <option value='Computer_Science'>Computer Science</option>
                            <option value='Criminal_Justice'>Criminal Justice</option>
                            <option value='Cultural_Studies'>Cultural Studies</option>
                            <option value='Dance'>Dance</option>
                            <option value='Economics'>Economics</option>
                            <option value='Education_Studies'>Education Studies</option>
                            <option value='Engineering'>Engineering</option>
                            <option value='English'>English</option>
                            <option value='Environmental_Studies'>Environmental Studies</option>
                            <option value='Finance'>Finance</option>
                            <option value='Foreign_Languages'>Foreign Languages</option>
                            <option value='Gender_and_Sexuality_Studies'>Gender and Sexuality Studies</option>
                            <option value='Geography'>Geography</option>
                            <option value='Geology'>Geology</option>
                            <option value='Health_Sciences'>Health Sciences</option>
                            <option value='History'>History</option>
                            <option value='Law'>Law</option>
                            <option value='Linguistics'>Linguistics</option>
                            <option value='Mathematics'>Mathematics</option>
                            <option value='Media_Studies'>Media Studies</option>
                            <option value='Music'>Music</option>
                            <option value='Neuroscience'>Neuroscience</option>
                            <option value='Other'>Other</option>
                            <option value='Philosophy'>Philosophy</option>
                            <option value='Physical_Ed._Recreation_&_Athletics'>Physical Ed. Recreation & Athletics</option>
                            <option value='Physics'>Physics</option>
                            <option value='Political_Science'>Political Science</option>
                            <option value='Psychology'>Psychology</option>
                            <option value='Religious_Studies'>Religious Studies</option>
                            <option value='Sociology'>Sociology</option>
                            <option value='Theatre_Arts'>Theatre Arts</option>
                            <option value='Visual_Arts'>Visual Arts</option>
                            <option value="Other">Other</option>
                        </select>
                    </td>
                </tr>


                <tr >
                    <td></td>
                    <td></td>
                    <td style="text-align: right;">
                        <input type="submit" id="next" class="myButton" name="next" value="Next"/>

                    </td>

                </tr>

            </table>
        </g:form>
    </div>
    <div id="errorPrimary" style="display: none;">You must enter at least a primary expertise.</div>
    <div id="errorSecondary" style="display: none;">You cannot enter a tertiary expertise without a secondary.</div>


</div>
<r:script>

    $(document).ready(function () {

        $("form").submit(function( e ) {
            var noError = true;
            $('#select1').find('option:selected').each(function() {
                if ($(this).val() == ("none")) {
                    noError = false;
                    $.fancybox({
                        content: $('#errorPrimary')
                    });
                }
            });

            $('#select2').find('option:selected').each(function() {
                if ($(this).val() == ("none") &&
                $('#select3').find('option:selected').val() != ("none")) {
                    $.fancybox({
                        content: $('#errorSecondary')
                    });
                    noError = false;
                }

            });

            return noError;
        });

    });
</r:script>
</body>
</html>
