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
    <title>Interest</title>
    <meta name="layout" content="main"/>
    <r:require modules="core" />
    <style>

    p.margin{
        margin: 16px 30px 0px 45px;
    }
    p {text-indent:45px;}

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


        $(".extra").hide();
        $("#add-boxes").click(function( e ) {
            $(".extra").toggle();
            $(".indent-click").toggle();

        });
        $("#minus-boxes").click(function( e ) {
            $(".extra").toggle();
            $(".indent-click").toggle();

        });

        $('form#interest-form input[name=box1]').bind('click keyup', function( e ) {
            if ( $('input[name=box1]', '#interest-form').hasClass('error') ) {
                $('input[name=box1]', '#interest-form').removeClass('error');
            }
        });

        $('form#interest-form').bind('keyup', function( e ) {

            if($('input[name=box1]', '#interest-form').val()==""&&
                    $('input[name=box2]', '#interest-form').val()==""&&
                    $('input[name=box3]', '#interest-form').val()==""&&
                    $('input[name=box4]', '#interest-form').val()==""&&
                    $('input[name=box5]', '#interest-form').val()==""&&
                    $('input[name=box6]', '#interest-form').val()==""&&
                    $('input[name=box7]', '#interest-form').val()==""&&
                    $('input[name=box8]', '#interest-form').val()==""&&
                    $('input[name=box9]', '#interest-form').val()==""&&
                    $('input[name=box10]', '#interest-form').val()==""){

                $('input[name=box1]', '#interest-form').addClass("error");

            }

        });
        $('#next').bind('click', function( e ) {

            if($('input[name=box1]', '#interest-form').val()==""&&
                    $('input[name=box2]', '#interest-form').val()==""&&
                    $('input[name=box3]', '#interest-form').val()==""&&
                    $('input[name=box4]', '#interest-form').val()==""&&
                    $('input[name=box5]', '#interest-form').val()==""&&
                    $('input[name=box6]', '#interest-form').val()==""&&
                    $('input[name=box7]', '#interest-form').val()==""&&
                    $('input[name=box8]', '#interest-form').val()==""&&
                    $('input[name=box9]', '#interest-form').val()==""&&
                    $('input[name=box10]', '#interest-form').val()==""){

                $("input[name=box1]", "#interest-form").addClass("error");
                $.fancybox({
                    content: $('#error')
                });
            }
            else{       alert("what?");
                //submit form and move to rating page
            }

        });

    });
</r:script>
