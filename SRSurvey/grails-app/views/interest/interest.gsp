<%--
  Created by IntelliJ IDEA.
  User: zixiao
  Date: 6/5/13
  Time: 2:12 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Interest</title>
    <meta name="layout" content="main"/>
    <r:require modules="core" />
    <style>

    p.margin{
        margin: 16px 30px 0px 45px;
    }
    p {text-indent:45px;}

    strong {font-weight: bold !important}

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

        font-size:100%!important;
        padding:4px 9px!important;

    }
    .main-boxes{
        padding:5px;
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
    <form action= "/SRSurvey/interest/process" name = "interest-form" id= "interest-form" method="post">
        <table>
            <tr>
                <td>

                    <table>
                        <tr class="main-boxes">
                            <td>
                                <input type="text" name="interest_inputs" maxlength="100">
                                <br>
                            </td>
                        </tr>
                        <tr class="main-boxes">
                            <td>
                                <input type="text" name="interest_inputs" maxlength="100">
                                <br>
                            </td>
                        </tr>
                        <tr class="main-boxes">
                            <td>
                                <input type="text" name="interest_inputs" maxlength="100">
                                <br>
                            </td>
                        </tr>
                        <tr class="main-boxes">
                            <td>
                                <input type="text" name="interest_inputs" maxlength="100">
                                <br>
                            </td>
                        </tr>
                        <tr class="main-boxes">
                            <td>
                                <input type="text" name="interest_inputs" maxlength="100">
                                <br>
                            </td>
                        </tr>

                        <tr class="extra main-boxes">
                            <td>
                                <input type="text" name="interest_inputs" maxlength="100">
                                <br>
                            </td>
                        </tr>
                        <tr class="extra main-boxes">
                            <td>
                                <input type="text" name="interest_inputs" maxlength="100">
                                <br>
                            </td>
                        </tr>
                        <tr class="extra main-boxes">
                            <td>
                                <input type="text" name="interest_inputs" maxlength="100">
                                <br>
                            </td>
                        </tr>
                        <tr class="extra main-boxes">
                            <td>
                                <input type="text" name="interest_inputs" maxlength="100">
                                <br>
                            </td>
                        </tr>
                        <tr class="extra main-boxes">
                            <td>
                                <input type="text" name="interest_inputs" maxlength="100">
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
                    <p class="margin"> Please enter at least <strong>three</strong> of your academic interests. These could be pedagogical interests, research interests, subjects you teach, ect. You can enter as many as you like.  These can be broad or specific. </p>
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
<r:script>

    $(document).ready(function() {


        $(".extra").hide();
        $("#add-boxes").click(function( e ) {
            $(".extra").toggle();
            $(".indent-click").toggle();

        });
        $("#minus-boxes").click(function( e ) {
            $(".extra").toggle();
            $(".indent-click").toggle();

        });


        $('form#interest-form').bind('click keyup', function( e ) {
            var noError = false;
            $("tr.main-boxes").each(function () {
                if ($(this).find('input[type=text]').val() != "") {

                    noError=true


                }
            });
            if(noError==false){
                $('input[name=box1]', '#interest-form').addClass("error");
            }
            else{
                $('input[name=box1]', '#interest-form').removeClass('error');
            }

        });
        $('#next').bind('click', function( e ) {
            var validated = false;
            $("tr.main-boxes").each(function () {
                if ($(this).find('input').val() != "") {
                    validated=true;

                }

            });
            if(validated==true){
                $("#interest-form").submit();
                //submit form and move to rating page
            }
            else{
                $('input[name=box1]', '#interest-form').addClass("error");

                $.fancybox({
                    content: $('#error')
                });
            }

        });

    });
</r:script>