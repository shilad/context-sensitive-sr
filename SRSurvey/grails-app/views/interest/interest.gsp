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
</head>
<body>
<div class= "interest rounded-corners" id="main-container">
    <h1>Research interests</h1>
    <form action="save" name="interest-form" id="interest-form" method="post">
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
                <td id="instructions">
                    Please enter at least <strong>THREE</strong> of your academic research interests.
                    These could be broad or specific pedagogical or research interests.

                    <br/>
                    <br/>
                    <button class="myButton">Next</button>
                </td>
            </tr>
        </table>
    </form>
    <div id="error" style="display: none;">You must enter at least three interests.</div>
</div>
</body>
</html>
<r:script>

    function numInterests() {
        var n = 0;
        $("tr.main-boxes").each(function () {
            if ($(this).find('input[type=text]').val() != "") {
                n++;
            }
        });
        return n;
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

        // remove errors after user enters enough interests
        $('form#interest-form').bind('click keyup', function( e ) {
            if(numInterests() >= 3){
                $("tr.main-boxes").each(function () {
                   $(this).find('input[type=text]').removeClass("error");
                });
            }
        });

        $('form').on('submit', function( e ) {
            if(numInterests() >= 3){
                return true;
            } else{
                var i=0;
                $("tr.main-boxes").each(function () {
                    if(i++<3){
                        $(this).find('input[type=text]').addClass("error");
                    }
                });
                $.fancybox({
                    content: $('#error')
                });
                return false;
            }
        });
    });
</r:script>