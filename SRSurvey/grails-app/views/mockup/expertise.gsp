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
    <style type="text/css">
    #text-area{

        margin: 10px 1px 0px 81px;
        width: 500px;
        height: 250px;
        background-color: whitesmoke;
        resize: none;
        border: 2.4px solid #000000;
    }

    tr.padding > td
    {
        padding-bottom: 10px;
    }


    #main-container {
        height:500px !important;
    }

    </style>


</head>
<body>
<div class= "rounded-corners" id="main-container">
    <br>
    <h1>Area of Study</h1>
    <br/><br/>
    <div class="margin" style="width: 69%;margin: auto;">
    <p>Please enter up to three of your fields of expertise, in order of preference:</p>
    <br/><br/>
    <table width="100%">
        <tr class="padding">
            <td>
                <h2>Primary:</h2>
            </td>
            <td>
                <select name="department_select">
                    <option selected disabled="true" value="Primary">Please Select An Option</option>
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
                    <option value='Philosophy'>Philosophy</option>
                    <option value='Physical_Ed._Recreation_&_Athletics'>Physical Ed. Recreation & Athletics</option>
                    <option value='Physics'>Physics</option>
                    <option value='Political_Science'>Political Science</option>
                    <option value='Psychology'>Psychology</option>
                    <option value='Religious_Studies'>Religious Studies</option>
                    <option value='Sociology'>Sociology</option>
                    <option value='Theatre_Arts'>Theatre Arts</option>
                    <option value='Visual_Arts'>Visual Arts</option>
                </select>
            </td>
            </tr>
        <tr class="padding">
            <td>
               <h2>Secondary:</h2>
            </td>
            <td>
               <select name="department_select">
                    <option selected disabled="true" value="Primary">Please Select An Option</option>
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
                    <option value='Philosophy'>Philosophy</option>
                    <option value='Physical_Ed._Recreation_&_Athletics'>Physical Ed. Recreation & Athletics</option>
                    <option value='Physics'>Physics</option>
                    <option value='Political_Science'>Political Science</option>
                    <option value='Psychology'>Psychology</option>
                    <option value='Religious_Studies'>Religious Studies</option>
                    <option value='Sociology'>Sociology</option>
                    <option value='Theatre_Arts'>Theatre Arts</option>
                    <option value='Visual_Arts'>Visual Arts</option>
                </select>
            </td>
        </tr>
        <tr class="padding">
            <td>
                <h3>Tertiary:</h3>
            </td>
            <td>
                <select name="department_select">
                    <option selected disabled="true" value="Primary">Please Select An Option</option>
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
                    <option value='Philosophy'>Philosophy</option>
                    <option value='Physical_Ed._Recreation_&_Athletics'>Physical Ed. Recreation & Athletics</option>
                    <option value='Physics'>Physics</option>
                    <option value='Political_Science'>Political Science</option>
                    <option value='Psychology'>Psychology</option>
                    <option value='Religious_Studies'>Religious Studies</option>
                    <option value='Sociology'>Sociology</option>
                    <option value='Theatre_Arts'>Theatre Arts</option>
                    <option value='Visual_Arts'>Visual Arts</option>
                </select>
            </td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td style="text-align: right;">
                <a href="#" class="myButton">Submit</a>
            </td>
        </tr>

    </table>
    </div>

</div>
</body>
</html>