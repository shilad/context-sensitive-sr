<%--
  Created by IntelliJ IDEA.
  User: jesse
  Date: 6/4/13
  Time: 9:31 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name="layout" content="main"/>
   <r:require modules="core" />
  <title>Similarity Ratings</title>
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
            width: 900px;
            background: -moz-linear-gradient(top,  rgba(132,211,239,1) 0%, rgba(145,232,239,1) 40%, rgba(148,237,239,0.98) 50%, rgba(145,232,239,1) 60%, rgba(132,211,239,1) 100%); /* FF3.6+ */
            background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,rgba(132,211,239,1)), color-stop(40%,rgba(145,232,239,1)), color-stop(50%,rgba(148,237,239,0.98)), color-stop(60%,rgba(145,232,239,1)), color-stop(100%,rgba(132,211,239,1))); /* Chrome,Safari4+ */
            background: -webkit-linear-gradient(top,  rgba(132,211,239,1) 0%,rgba(145,232,239,1) 40%,rgba(148,237,239,0.98) 50%,rgba(145,232,239,1) 60%,rgba(132,211,239,1) 100%); /* Chrome10+,Safari5.1+ */
            background: -o-linear-gradient(top,  rgba(132,211,239,1) 0%,rgba(145,232,239,1) 40%,rgba(148,237,239,0.98) 50%,rgba(145,232,239,1) 60%,rgba(132,211,239,1) 100%); /* Opera 11.10+ */
            background: -ms-linear-gradient(top,  rgba(132,211,239,1) 0%,rgba(145,232,239,1) 40%,rgba(148,237,239,0.98) 50%,rgba(145,232,239,1) 60%,rgba(132,211,239,1) 100%); /* IE10+ */
            background: linear-gradient(to bottom,  rgba(132,211,239,1) 0%,rgba(145,232,239,1) 40%,rgba(148,237,239,0.98) 50%,rgba(145,232,239,1) 60%,rgba(132,211,239,1) 100%); /* W3C */
            filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#84d3ef', endColorstr='#84d3ef',GradientType=0 ); /* IE6-9 */
        }
        #rating-bars{
            right: 0%;
            width: 500px;
            float: right;
            margin-right: 50px;
            clear: left;
            background: rgb(255,255,255); /* Old browsers */
            background: -moz-linear-gradient(left,  rgba(255,255,255,1) 0%, rgba(5,206,38,1) 100%); /* FF3.6+ */
            background: -webkit-gradient(linear, left top, right top, color-stop(0%,rgba(255,255,255,1)), color-stop(100%,rgba(5,206,38,1))); /* Chrome,Safari4+ */
            background: -webkit-linear-gradient(left,  rgba(255,255,255,1) 0%,rgba(5,206,38,1) 100%); /* Chrome10+,Safari5.1+ */
            background: -o-linear-gradient(left,  rgba(255,255,255,1) 0%,rgba(5,206,38,1) 100%); /* Opera 11.10+ */
            background: -ms-linear-gradient(left,  rgba(255,255,255,1) 0%,rgba(5,206,38,1) 100%); /* IE10+ */
            background: linear-gradient(to right,  rgba(255,255,255,1) 0%,rgba(5,206,38,1) 100%); /* W3C */
            filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#ffffff', endColorstr='#05ce26',GradientType=1 ); /* IE6-9 */
        }
        h5{
            font-size: 16px;
            line-height: 15px;
            margin: 0px auto;
            font-family: Arial, Helvetica, sans-serif;
            font-weight: bold;
        }
         tr.main > td{



            padding-bottom: 1.5em;
        }

        tr.even{
            background: rgb(124,213,237);
        }
        tr.odd{
            background: rgb(43,205,249);
        }
        .error{
            background: rgb(255,0,0);
        }
        h1{
            font-size: 45px;
            padding-left: 1em;
            font-weight: bold;
            margin-bottom: 25px;
        }
        .no-assoc{
            float: left;
            padding-left: 15px;
            font-size: 12px;
        }
        .str-assoc{
            float: right;
            padding-right: 55px;
            font-size: 12px;
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
    </style>

</head>
<body>
    <div class="rounded-corners" id="main-container">
        <br/><h1>Similarity Ratings</h1>
        <form action="" name="rating-form" id="rating-form" method="post">
        <table style="width: 100%;border-collapse: collapse;">
            <tr class="main">
                <td style="text-align: right !important;width: 30%;">&nbsp;</td>
                <td style="text-align: center;width: 8%;font-size: 13px;">I don't know<br/>this term</td>
                <td style="width: 62%;">&nbsp;</td>
            </tr>
            <tr class="main odd" id="field1">
                <td style="text-align: right !important;width: 30%;">
                    <h5>Experimental Methodology</h5>
                    <br/>
                    <h5>Scientific Methods</h5>
                </td>
                <td style="width: 8%;padding-left: 3%;padding-right: 3%;" >
                    <input type="checkbox" name="term1" id="term1" value="term1">
                    <br/><br/>
                    <input type="checkbox" name="term2" id="term2" value="term2">
                </td>
                <td style="width: 62%;">
                    <div class="rounded-corners" id="rating-bars">
                        <table style="width: 100%;text-align: center;">
                            <tr>
                                <td>
                                    1 <input type="radio" name="field1" value="1"/>
                                </td>
                                <td>
                                    2 <input type="radio" name="field1" value="2"/>
                                </td>
                                <td>
                                    3 <input type="radio" name="field1" value="3"/>
                                </td>
                                <td>
                                    4 <input type="radio" name="field1" value="4"/>
                                </td>
                                <td>
                                    5 <input type="radio" name="field1" value="5"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <br/>
                    <div class="no-assoc">
                        No Association
                    </div>
                    <div class="str-assoc">
                        Strong Association
                    </div>
                </td>
            </tr>
            <tr class="main even">
                <td style="text-align: right !important;width: 30%;">
                    <h5>Experimental Methodology</h5>
                    <br/>
                    <h5>Scientific Methods</h5>
                </td>
                <td style="width: 8%;padding-left: 3%;padding-right: 3%;" >
                    <input type="checkbox" name="term3" value="term3">
                    <br/><br/>
                    <input type="checkbox" name="term4" value="term4">
                </td>
                <td style="width: 62%;">
                    <div class="rounded-corners" id="rating-bars">
                        <table style="width: 100%;text-align: center;">
                            <tr>
                                <td>
                                    1 <input type="radio" name="field2" value="1"/>
                                </td>
                                <td>
                                    2 <input type="radio" name="field2" value="2"/>
                                </td>
                                <td>
                                    3 <input type="radio" name="field2" value="3"/>
                                </td>
                                <td>
                                    4 <input type="radio" name="field2" value="4"/>
                                </td>
                                <td>
                                    5 <input type="radio" name="field2" value="5"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <br/>
                    <div class="no-assoc">
                        No Association
                    </div>
                    <div class="str-assoc">
                        Strong Association
                    </div>
                </td>
            </tr>
            <tr class="main odd">
                <td style="text-align: right !important;width: 30%;">
                    <h5>Experimental Methodology</h5>
                    <br/>
                    <h5>Scientific Methods</h5>
                </td>
                <td style="width: 8%;padding-left: 3%;padding-right: 3%;" >
                    <input type="checkbox" name="term5" value="term5">
                    <br/><br/>
                    <input type="checkbox" name="term6" value="term6">
                </td>
                <td style="width: 62%;">
                    <div class="rounded-corners" id="rating-bars">
                        <table style="width: 100%;text-align: center;">
                            <tr>
                                <td>
                                    1 <input type="radio" name="field3" value="1"/>
                                </td>
                                <td>
                                    2 <input type="radio" name="field3" value="2"/>
                                </td>
                                <td>
                                    3 <input type="radio" name="field3" value="3"/>
                                </td>
                                <td>
                                    4 <input type="radio" name="field3" value="4"/>
                                </td>
                                <td>
                                    5 <input type="radio" name="field3" value="5"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <br/>
                    <div class="no-assoc">
                        No Association
                    </div>
                    <div class="str-assoc">
                        Strong Association
                    </div>
                </td>
            </tr>
            <tr class="main even">
                <td style="text-align: right !important;width: 30%;">
                    <h5>Experimental Methodology</h5>
                    <br/>
                    <h5>Scientific Methods</h5>
                </td>
                <td style="width: 8%;padding-left: 3%;padding-right: 3%;" >
                    <input type="checkbox" name="term7" value="term7">
                    <br/><br/>
                    <input type="checkbox" name="term8" value="term8">
                </td>
                <td style="width: 62%;">
                    <div class="rounded-corners" id="rating-bars">
                        <table style="width: 100%;text-align: center;">
                            <tr>
                                <td>
                                    1 <input type="radio" name="field4" value="1"/>
                                </td>
                                <td>
                                    2 <input type="radio" name="field4" value="2"/>
                                </td>
                                <td>
                                    3 <input type="radio" name="field4" value="3"/>
                                </td>
                                <td>
                                    4 <input type="radio" name="field4" value="4"/>
                                </td>
                                <td>
                                    5 <input type="radio" name="field4" value="5"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <br/>
                    <div class="no-assoc">
                        No Association
                    </div>
                    <div class="str-assoc">
                        Strong Association
                    </div>
                </td>
            </tr>
            <tr class="main odd">
                <td style="text-align: right !important;width: 30%;">
                    <h5>Experimental Methodology</h5>
                    <br/>
                    <h5>Scientific Methods</h5>
                </td>
                <td style="width: 8%;padding-left: 3%;padding-right: 3%;" >
                    <input type="checkbox" name="term9" value="term9">
                    <br/><br/>
                    <input type="checkbox" name="term10" value="term10">
                </td>
                <td style="width: 62%;">
                    <div class="rounded-corners" id="rating-bars">
                        <table style="width: 100%;text-align: center;">
                            <tr>
                                <td>
                                    1 <input type="radio" name="field5" value="1"/>
                                </td>
                                <td>
                                    2 <input type="radio" name="field5" value="2"/>
                                </td>
                                <td>
                                    3 <input type="radio" name="field5" value="3"/>
                                </td>
                                <td>
                                    4 <input type="radio" name="field5" value="4"/>
                                </td>
                                <td>
                                    5 <input type="radio" name="field5" value="5"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <br/>
                    <div class="no-assoc">
                        No Association
                    </div>
                    <div class="str-assoc">
                        Strong Association
                    </div>
                </td>
            </tr>
            <tr class="main">
                <td style="width: 30%;">&nbsp;</td>
                <td style="width: 8%;">&nbsp;</td>
                <td style="width: 62%;text-align: right; padding-right: 1em;">


                    <a class="fancybox myButton" id="continue-button">Next</a>

                </td>
            </tr>
        </table>
        </form>
    <div style="display:none; margin: auto auto auto auto;" id="continue">
        <h5>Would you like to continue rating?</h5>
        <p style="text-align: center;font-family: Arial, Helvetica, sans-serif;">
            <a href="reload_this_page"><button>Yes, I would like to rate some more</button></a>
            <br/><br/>
            or
            <br/>  <br/>
            <a href="comments"><button>No, I'd like to finish the survey</button></a>

        </p>

    </div>
    </div>

</body>
</html>

