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
    <title>Comments</title>
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

    p.margin
    {
        margin: 60px 20px 0px 95px;
    }


    .myButton {

        -moz-box-shadow:inset 0px 1px 0px 0px #ffffff;
        -webkit-box-shadow:inset 0px 1px 0px 0px #ffffff;
        box-shadow:inset 0px 1px 0px 0px #ffffff;

        background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #ededed), color-stop(1, #dfdfdf));
        background:-moz-linear-gradient(top, #ededed 5%, #dfdfdf 100%);
        background:-webkit-linear-gradient(top, #ededed 5%, #dfdfdf 100%);
        background:-o-linear-gradient(top, #ededed 5%, #dfdfdf 100%);
        background:-ms-linear-gradient(top, #ededed 5%, #dfdfdf 100%);
        background:linear-gradient(to bottom, #ededed 5%, #dfdfdf 100%);
        filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#ededed', endColorstr='#dfdfdf',GradientType=0);

        background-color:#ededed;

        -moz-border-radius:15px;
        -webkit-border-radius:15px;
        border-radius:15px;

        border:1px solid #666563;

        display:inline-block;
        color:#2b2926;
        font-family:arial;
        font-size:15px;
        font-weight:bold;
        padding:6px 14px;
        text-decoration:none;

        text-shadow:0px 1px 0px #ffffff;

    }

    </style>


</head>
<body>
<div class= "rounded-corners" id="main-container">
    <br>
    <h1>Comments</h1>

    <p class= "margin">Please enter any comments or concerns:</p>
    <table>
        <tr>
            <td>
                <textarea rows="4" cols="50" class = "rounded-corners"  id = "text-area" name="text">
                </textarea>
            </td>
        </tr>
        <tr>
            <td style="text-align: right;">
                <a href="#" class="myButton">Submit</a>
            </td>
        </tr>

    </table>

</div>
</body>
</html>