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
    .rating-bars{
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
        font-weight: bold !important;
    }
    tr.main > td{
        padding-bottom: 1.5em;
    }
    tr.main-top > td{
            padding-bottom: 1.5em;
        }
    tr.even{
        background: rgb(124,213,237);
    }
    tr.odd{
            background: rgb(43,205,249);
        }
    .error{
            background: #FF0000 !important;
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
%{--<form action="" name="rating-form" id="rating-form" method="post">--}%
    <g:form url="[action:'processForm', controller:'Rating']" name="rating-form" method="post">
    %{--<form action="/SRSurvey/rating/processForm/" method="post" id='rating-form'>--}%
        <table style="width: 100%;border-collapse: collapse;">
            <tr class="main-top">
                <td style="text-align: right !important;width: 30%;">&nbsp;</td>
                <td style="text-align: center;width: 8%;font-size: 13px;">I don't know<br/>this term</td>
                <td style="width: 62%;">&nbsp;</td>
            </tr>

            <g:each status="i" in="${questions}" var="q">
                <tr class="main ${ (i % 2) == 0 ? 'odd' : 'even'}" id="${q.questionNumber}">

                    <td style="text-align: right !important;width: 30%;">
                        <h5>${q.interest1.text}</h5>
                        <br/>
                        <h5>${q.interest2.text}</h5>
                    </td>

                    <td style="width: 8%;padding-left: 3%;padding-right: 3%;" >
                        <input type="checkbox" name="checks" id="${q.interest1.id}" value="${q.interest1.id}">
                        <br/><br/>
                        <input type="checkbox" name="checks" id="${q.interest2.id}" value="${q.interest2.id}">
                    </td>

                    <td style="width: 62%;">
                        <div class="rounded-corners rating-bars">
                            <table style="width: 100%;text-align: center;">
                                <tr>
                                    <td>
                                        1 <input type="radio" name="radio_${q.id}" value="1"/>
                                    </td>
                                    <td>
                                        2 <input type="radio" name="radio_${q.id}" value="2"/>
                                    </td>
                                    <td>
                                        3 <input type="radio" name="radio_${q.id}" value="3"/>
                                    </td>
                                    <td>
                                        4 <input type="radio" name="radio_${q.id}" value="4"/>
                                    </td>
                                    <td>
                                        5 <input type="radio" name="radio_${q.id}" value="5"/>
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
            </g:each>

            <tr class="main-top">
                <td style="width: 30%;">&nbsp;</td>
                <td style="width: 8%;">&nbsp;</td>
                <td style="width: 62%;text-align: right; padding-right: 1em;">


                    <a href="#continue" class="myButton" id="continue-button">Next</a>

                </td>
            </tr>
        </table>

        <div style="display:none; margin: auto auto auto auto;" id="continue">
            <h5>Would you like to continue rating?</h5>
            <p style="text-align: center;font-family: Arial, Helvetica, sans-serif;">
                <a href="reload_this_page"><button>Yes, I would like to rate some more</button></a>
                <br/><br/>
                or
                <br/>  <br/>
                <form action="" method="post"/>
                <input id="comments" type="submit" name="submit" value="No, I'd like to finish the survey"/>

                    </form></p>

        </div>
    </g:form>

</div>

</body>
</html>

<r:script>
    if (typeof jQuery !== 'undefined') {
        (function($) {
            $('#spinner').ajaxStart(function() {
                $(this).fadeIn();
            }).ajaxStop(function() {
                        $(this).fadeOut();
                    });
        })(jQuery);
    }
    $(document).ready(function() {


        $(".fancybox").fancybox();
        $('#continue-button').click(function(e) {
            var bool=true;
            $("tr.main").each(function () {
                //console.log($(this).find('input:checked'));
                if ($(this).find('input:checked').length == 0) {
                    $(this).addClass("error");
                    bool= false;
                    //console.log(bool)

                }

            });
            if(bool==true){
                //alert(bool);
                $('#continue-button').addClass("fancybox");
                $.fancybox({
                    content: $('#continue')
                });
                return false;
            }

        });

        $("tr").live('click', function( e ) {
            if ( $(this).hasClass('error') ) {
                $(this).removeClass('error');
            }

        });
        $("#comments").click(function(e) {
            $("#rating-form").submit();
        });
    });
</r:script>

