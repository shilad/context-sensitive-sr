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
  <title></title>
    <style>
        .rounded-corners {
            -moz-border-radius: 20px;
            -webkit-border-radius: 20px;
            -khtml-border-radius: 20px;
            border-radius: 20px;
            margin:5px;
        }
        #main-container{
            margin: 2em 1em 1.25em 18em;
            width: 800px;
            height: 800px;
            background: -moz-linear-gradient(top,  rgba(132,211,239,1) 0%, rgba(145,232,239,1) 40%, rgba(148,237,239,0.98) 50%, rgba(145,232,239,1) 60%, rgba(132,211,239,1) 100%); /* FF3.6+ */
            background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,rgba(132,211,239,1)), color-stop(40%,rgba(145,232,239,1)), color-stop(50%,rgba(148,237,239,0.98)), color-stop(60%,rgba(145,232,239,1)), color-stop(100%,rgba(132,211,239,1))); /* Chrome,Safari4+ */
            background: -webkit-linear-gradient(top,  rgba(132,211,239,1) 0%,rgba(145,232,239,1) 40%,rgba(148,237,239,0.98) 50%,rgba(145,232,239,1) 60%,rgba(132,211,239,1) 100%); /* Chrome10+,Safari5.1+ */
            background: -o-linear-gradient(top,  rgba(132,211,239,1) 0%,rgba(145,232,239,1) 40%,rgba(148,237,239,0.98) 50%,rgba(145,232,239,1) 60%,rgba(132,211,239,1) 100%); /* Opera 11.10+ */
            background: -ms-linear-gradient(top,  rgba(132,211,239,1) 0%,rgba(145,232,239,1) 40%,rgba(148,237,239,0.98) 50%,rgba(145,232,239,1) 60%,rgba(132,211,239,1) 100%); /* IE10+ */
            background: linear-gradient(to bottom,  rgba(132,211,239,1) 0%,rgba(145,232,239,1) 40%,rgba(148,237,239,0.98) 50%,rgba(145,232,239,1) 60%,rgba(132,211,239,1) 100%); /* W3C */
            filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#84d3ef', endColorstr='#84d3ef',GradientType=0 ); /* IE6-9 */
        }
        #rating-bars{
            width: 600px;
            background: rgb(255,255,255); /* Old browsers */
            background: -moz-linear-gradient(top,  rgba(255,255,255,1) 0%, rgba(5,206,38,1) 100%); /* FF3.6+ */
            background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,rgba(255,255,255,1)), color-stop(100%,rgba(5,206,38,1))); /* Chrome,Safari4+ */
            background: -webkit-linear-gradient(top,  rgba(255,255,255,1) 0%,rgba(5,206,38,1) 100%); /* Chrome10+,Safari5.1+ */
            background: -o-linear-gradient(top,  rgba(255,255,255,1) 0%,rgba(5,206,38,1) 100%); /* Opera 11.10+ */
            background: -ms-linear-gradient(top,  rgba(255,255,255,1) 0%,rgba(5,206,38,1) 100%); /* IE10+ */
            background: linear-gradient(to bottom,  rgba(255,255,255,1) 0%,rgba(5,206,38,1) 100%); /* W3C */
            filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#ffffff', endColorstr='#05ce26',GradientType=0 ); /* IE6-9 */
        }
    </style>
</head>
<body>
    <div class="rounded-corners" id="main-container">

                           <p>hello</p>
    </div>

</body>
</html>