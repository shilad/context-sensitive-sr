<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Macademia Survey: Thank you</title>
    <meta name="layout" content="main"/>
    <r:require modules="core" />

</head>
<body>
<div class="thanks rounded-corners" id="main-container">
    <br>
    <h1>Thank You!</h1>
    <p>
        Thanks for participating in our study.
        Over the coming months we will analyze the data you have provided to determine whether systematic differences exist in people's ratings.
        We believe this work will advance scholarly knowledge and improve Macademia.
    </p>
    <p>
        <input id="foo" type="checkbox" name="foo">&nbsp;&nbsp;Please email the results of this study when they are published. &nbsp;&nbsp;&nbsp;
        <span class="saved">Your response has been saved!</span>
    </p>
    <p>
        Thanks for your help!
    </p>

    <p>
        Shilad Sen (<a href="mailto:ssen@macalester.edu">ssen@macalester.edu</a>)<br/>
        Brent Hecht (<a href="mailto:ssen@macalester.edu">bhect@cs.umn.edu</a>)
    </p>
    <p>
        The Macademia Team:</br>
        <r:img uri="/images/team.jpg"/><br/>
    Front row: Zixiao Wang, Yulun Li, Margaret Giesel, Jesse Russell<br/>
    Back row: Matt Lesicko, Ari Wieland, Ben Hillman, Shilad Sen, Sam Naden, Rebecca Gold<br/>
    </p>
</div>
<r:script>
$().ready(function() {
    $(".saved").hide();
    $("#foo").click(function() {
        var checked = ($(this).attr("checked") == 'checked');
        $(".saved").fadeIn().delay(1000).fadeOut();
        ajaxLog('emailPublication\t' + checked);

    });
});
</r:script>
</body>
</html>