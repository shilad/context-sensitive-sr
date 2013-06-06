<%--
  Created by IntelliJ IDEA.
  User: research
  Date: 6/4/13
  Time: 4:31 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <r:require modules="core" />
    <style>
    html, body, div, span, applet, object, iframe,
    h1, h2, h3, h4, h5, h6, p, blockquote, pre,
    a, abbr, acronym, address, big, cite, code,
    del, dfn, em, img, ins, kbd, q, s, samp,
    small, strike, strong, sub, sup, tt, var,
    b, u, i, center,
    dl, dt, dd, ol, ul, li,
    fieldset, form, label, legend,
    table, caption, tbody, tfoot, thead, tr, th, td,
    article, aside, canvas, details, embed,
    figure, figcaption, footer, header, hgroup,
    menu, nav, output, ruby, section, summary,
    time, mark, audio, video {
        margin: 0;
        padding: 0;
        border: 0;
        font-size: 100%;
        font: inherit;
        vertical-align: baseline;
    }
    .rounded-corners {
        -moz-border-radius: 20px;
        -webkit-border-radius: 20px;
        -khtml-border-radius: 20px;
        border-radius: 20px;
        margin:5px;
    }

    #main-container{
        font-family: Arial, Helvetica, sans-serif;
        margin: 20px auto auto auto;
        width: 750px;

        background: -moz-linear-gradient(top, rgba(132,211,239,1) 0%, rgba(145,232,239,1) 40%, rgba(148,237,239,0.98) 50%, rgba(145,232,239,1) 60%, rgba(132,211,239,1) 100%); /* FF3.6+ */
        background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,rgba(132,211,239,1)), color-stop(40%,rgba(145,232,239,1)), color-stop(50%,rgba(148,237,239,0.98)), color-stop(60%,rgba(145,232,239,1)), color-stop(100%,rgba(132,211,239,1))); /* Chrome,Safari4+ */
        background: -webkit-linear-gradient(top, rgba(132,211,239,1) 0%,rgba(145,232,239,1) 40%,rgba(148,237,239,0.98) 50%,rgba(145,232,239,1) 60%,rgba(132,211,239,1) 100%); /* Chrome10+,Safari5.1+ */
        background: -o-linear-gradient(top, rgba(132,211,239,1) 0%,rgba(145,232,239,1) 40%,rgba(148,237,239,0.98) 50%,rgba(145,232,239,1) 60%,rgba(132,211,239,1) 100%); /* Opera 11.10+ */
        background: -ms-linear-gradient(top, rgba(132,211,239,1) 0%,rgba(145,232,239,1) 40%,rgba(148,237,239,0.98) 50%,rgba(145,232,239,1) 60%,rgba(132,211,239,1) 100%); /* IE10+ */
        background: linear-gradient(to bottom, rgba(132,211,239,1) 0%,rgba(145,232,239,1) 40%,rgba(148,237,239,0.98) 50%,rgba(145,232,239,1) 60%,rgba(132,211,239,1) 100%); /* W3C */
        filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#84d3ef', endColorstr='#84d3ef',GradientType=0 ); /* IE6-9 */
    }
    p.margin{
        margin: 16px 30px 0px 45px;
    }
    p {text-indent:45px;}
    h1{
        padding-left: 1em;
        font-size: 45px;
        line-height: 15px;
    }
    input{
        margin: 19px 1px 0px 47px;
        width: 266px;
        height: 30px;
        font-size: 100%;
        background-color: whitesmoke;
        resize: none;
        border: 2.4px solid #000000;
    }
    #add-more
    {
        margin: 1px 25px 0px 2px
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
        font-size:100%;
        font-weight:bold;
        padding:4px 9px;
        text-decoration:none;

        text-shadow:0px 1px 0px #ffffff;

    }
    .myButton:hover {

        background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #dfdfdf), color-stop(1, #ededed));
        background:-moz-linear-gradient(top, #dfdfdf 5%, #ededed 100%);
        background:-webkit-linear-gradient(top, #dfdfdf 5%, #ededed 100%);
        background:-o-linear-gradient(top, #dfdfdf 5%, #ededed 100%);
        background:-ms-linear-gradient(top, #dfdfdf 5%, #ededed 100%);
        background:linear-gradient(to bottom, #dfdfdf 5%, #ededed 100%);
        filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#dfdfdf', endColorstr='#ededed',GradientType=0);

        background-color:#dfdfdf;
    }
    .myButton:active {
        position:relative;
        top:1px;
    }
    .indent-click{
        margin: 4px 0px 0px 70px
    }
    #next{
        margin: 4px 45px 0px 30px
    }

    .error{
        background: rgb(255,0,0);
    }
    #error{
        font-family: Arial, Helvetica, sans-serif;
        padding-top: 35px;
        color:#FF0000;
    }
    </style>
</head>
<body>
<div class= "rounded-corners" id="main-container">
    <br>
    <br>
    <h1> Interests </h1>
    <br>
    <br>
    <form action= "unnamed" name = "interest-form" id= "interest-form" method="post">
        <table>
            <tr>
                <td>

                    <table>
                        <tr>
                            <td>
                                <input type="text" name="box1">
                                <br>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="text" name="box2">
                                <br>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="text" name="box3">
                                <br>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="text" name="box4">
                                <br>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="text" name="box5">
                                <br>
                            </td>
                        </tr>

                        <tr class="extra">
                            <td>
                                <input type="text" name="box6">
                                <br>
                            </td>
                        </tr>
                        <tr class="extra">
                            <td>
                                <input type="text" name="box7">
                                <br>
                            </td>
                        </tr>
                        <tr class="extra">
                            <td>
                                <input type="text" name="box8">
                                <br>
                            </td>
                        </tr>
                        <tr class="extra">
                            <td>
                                <input type="text" name="box9">
                                <br>
                            </td>
                        </tr>
                        <tr class="extra">
                            <td>
                                <input type="text" name="box10">
                                <br>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <br>
                                <div class="indent-click" id="add">
                                    Click to add more interests
                                    <a id="add-boxes" class="myButton" >+</a>
                                </div>
                                <div class="indent-click" id="minus" style="display: none;">
                                    Click to remove extra boxes
                                    <a id="minus-boxes" class="myButton" >-</a>
                                </div>
                            </td>
                        </tr>
                    </table>
                </td>
                <td>
                    <br>
                    <p class="margin"> Please enter your academic interests. These could be pedagogical interests, research interests, subjects you teach, ect. You can enter as many as you like.  These can be broad or specific. </p>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: right;">
                    <a id="next" class="myButton">Next</a>
                </td>
            </tr>
        </table>
    </form>
    <div id="error" style="display: none;">You must enter at least one interest.</div>
</div>
</body>
</html>