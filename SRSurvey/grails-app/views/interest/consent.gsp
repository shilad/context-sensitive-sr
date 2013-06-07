<%--
  Created by IntelliJ IDEA.
  User: Margaret
  Date: 6/4/13
  Time: 2:04 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Disclaimer</title>
    <meta name="layout" content="main"/>
    <r:require modules="core" />
    <style>

    p.margin
    {
        margin: auto 40px auto 40px;
    }
    p {text-indent:45px;}


      #main-container {
      height:500px;
      }


    </style>
</head>
<body>
<div class= "rounded-corners" id="main-container">
    <br>
    <br>
    <h1>Disclaimer  </h1>
    <br>
    <br>
<g:form controller="Interest" action="update" name = "consent-form" method="post">
    <table>
        <tr>
            <td>
                <p class= "margin">Het is al geruime tijd een bekend gegeven dat een lezer, tijdens het bekijken van de layout van een pagina, afgeleid wordt door de tekstuele inhoud. Het belangrijke punt van het gebruik van Lorem Ipsum is dat het uit een min of meer normale verdeling van letters bestaat, in tegenstelling tot "Hier uw tekst, hier uw tekst" wat het tot min of meer leesbaar nederlands maakt. Veel desktop publishing pakketten en web pagina editors gebruiken tegenwoordig Lorem Ipsum als hun standaard model tekst, en een zoekopdracht naar "lorem ipsum" ontsluit veel websites die nog in aanbouw zijn. Verscheidene versies hebben zich ontwikkeld in de loop van de jaren, soms per ongeluk soms expres (ingevoegde humor en dergelijke).</p>
            </td>
        </tr>
        <tr>
            <td style= "text-align: right; padding-right: 40;">
                <br>
                <g:submitButton name="Yes, I consent" class="myButton" title="submit"></g:submitButton>
            </td>
        </tr>
    </table>
</g:form>
</div>
</body>
</html>