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

        #main-container{
            width: 900px !important;
        }

    </style>
   <r:script>

   </r:script>
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
        //$("#continue-button").hide();

        $(".fancybox").fancybox();
        $('#continue-button').click(function(e) {
            if($('input[name=field1]:checked', '#rating-form').val()==null&&($("#term1:checkbox").length!= $("#term1:checkbox:checked").length
                    &&$("#term2:checkbox").length!= $("#term2:checkbox:checked").length)){

                $("#field1").addClass("error");

                return false;
            }
            else{
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
    });
</r:script>
