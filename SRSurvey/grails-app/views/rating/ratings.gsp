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
        font-size: 16px !important;
        line-height: 15px !important;
        margin: 0px auto !important;
        font-family: Arial, Helvetica, sans-serif !important;
        font-weight: bold !important;
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
        background: rgb(255,0,0) !important;
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
                        <input type="checkbox" name="checks" class="checks" id="${q.interest1.id}" value="${q.interest1.id}">
                        <br/><br/>
                        <input type="checkbox" name="checks" class="checks" id="${q.interest2.id}" value="${q.interest2.id}">
                    </td>

                    <td style="width: 62%;">
                        <div class="rounded-corners rating-bars">
                            <table style="width: 100%;text-align: center;">
                                <tr>
                                    <td>
                                        1 <input type="radio" name="${"radio_"+q.id}" value="1"/>
                                    </td>
                                    <td>
                                        2 <input type="radio" name="${"radio_"+q.id}" value="2"/>
                                    </td>
                                    <td>
                                        3 <input type="radio" name="${"radio_"+q.id}" value="3"/>
                                    </td>
                                    <td>
                                        4 <input type="radio" name ="${"radio_"+q.id}" value="4"/>
                                    </td>
                                    <td>
                                        5 <input type="radio" name="${"radio_"+q.id}" value="5"/>
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


                    <a href="#" class="myButton" id="continue-button">Next</a>

                </td>
            </tr>
        </table>

        <div style="display:none; margin: auto auto auto auto;" id="continue">
            <h5>Would you like to continue rating?</h5>
            <p style="text-align: center;font-family: Arial, Helvetica, sans-serif;">
                <form action="" method="post"/>
                    <input id="more" type="submit" name="submit" value="Yes, I would like to rate some more"/>
                    <br/><br/>
                or
                    <br/>  <br/>
                    <input id="comments" type="submit" name="submit" value="No, I'd like to finish the survey"/>

                </form>
            </p>

        </div>
    </g:form>

</div>

</body>
</html>

<r:script>

    $(document).ready(function() {


        $(".fancybox").fancybox();
        $('#continue-button').click(function(e) {
            var isValidated=true;
            $("tr.main").each(function () {
                //console.log($(this).find('input:checked'));
                if ($(this).find('input:checked').length == 0) {
                    $(this).addClass("error");
                    isValidated= false;
                    //console.log(isValidated)

                }

            });
            if(isValidated==true){
                //alert(isValidated);
                $('#continue-button').addClass("fancybox");
                $.fancybox({
                    content: $('#continue')
                });
                return false;
            }

        });
        $(".checks").live('click', function( e ) {
            $("tr.main").each(function () {

                if ($(this).find('input[type=checkbox]:checked').length > 0) {

                    $(this).find(".rating-bars").fadeTo(500,.5);
                    $(this).find("input[type=radio]").attr('disabled','disabled');
                    $(this).find("input[type=radio]").prop('checked',false);
                    //console.log($(this).find("input[type=radio]"));


                }
                else{
                    $(this).find(".rating-bars").fadeTo(500,1);
                    $(this).find("input[type=radio]").removeAttr('disabled','disabled');
                    //console.log($(this).find("input[type=radio]"));
                }

            });

        });
        $("tr").live('click', function( e ) {
            if ( $(this).hasClass('error') ) {
                $(this).removeClass('error');
            }

        });
        $("#comments").click(function(e) {
            var input = $("<input>").attr("type", "hidden").attr("name", "nextLocation").val("comments");
            $('#rating-form').append($(input));
            $("#rating-form").submit();
        });
        $("#more").click(function(e) {
            var input = $("<input>").attr("type", "hidden").attr("name", "nextLocation").val("reload");
            $('#rating-form').append($(input));
            $("#rating-form").submit();
        });
    });
</r:script>

