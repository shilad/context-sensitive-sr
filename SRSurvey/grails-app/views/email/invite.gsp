<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Macademia study: Context-sensitive semantic relatedness</title>
</head>
<body>

<p>
    Dear ${name},
</p>

<p>
We are writing to invite you to participate in a <b>15 minute</b> online research study that will improve <b>Macademia's algorithms</b> and advance scholarly knowledge in the fields of <b>computational linguistics</b> and <b>human-computer interaction</b>.
<g:if test="${inDept}">
    Biologists, historians, and psychologists are particularly important to the study, so we especially need help from you!
</g:if>
</p>

<p>Take the Macademia survey: <a href="${baseUrl}?email=${email.encodeAsURL()}">${baseUrl}</a></p>

<p>
    Our study will ask you to rate the "relatedness" of concept pairs (e.g. "collar" and "dog").
    Computational linguists have developed algorithms that estimate the semantic relatedness between two phrases.
    These algorithms have become critical components of many technologies, including those that search the web, present visualizations of “big data”, and help computers understand natural language.
    Your ratings will help computers produce semantic relatedness estimates that better correspond to those of real people.
</p>

<p>We <b>thank you</b> in advance for your help!</p>

<p>
Shilad Sen (<a href="mailto:ssen@macalester.edu">ssen@macalester.edu</a>)</br>
Brent Hecht (<a href="mailto:bhecht@cs.umn.edu">bhecht@cs.umn.edu</a>)</br>
</p>

<p><a href="${baseUrl}?email=${email.encodeAsURL()}">Link to survey</a></p>

<p>
The Macademia Team:<br>
    <img src="http://macademia.macalester.edu/SRSurvey/static/images/team.jpg" width="300px" height="200px"/>
</p>

<p>Please email <a href="mailto:ssen@macalester.edu">Shilad Sen</a> to unsubscribe from future Macademia emails.</p>

</body>
</html>
