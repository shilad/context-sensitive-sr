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


    #main-container {
        height:500px !important;
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