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
    <title>Rate the relatedness of each term</title>

</head>
<body>
<div class="rounded-corners rating" id="main-container">
    <br/><h1>Rate the relatedness of each term (page ${page - roundBegPage + 1} of ${roundEndPage - roundBegPage + 1})</h1>
    <div id="instructions">
        Please rate how related each pair of terms is.
        When you finish rating all pairs, click "next".
    </div>
    <g:form action="save" name="rating-form" method="post" params="${[page: page]}">
        <div id="dontknow">I don't know<br/>this term</div>
        <div id="ratings">
            <g:each status="i" in="${questions}" var="q">
                <div class="row ${ (i % 2) == 0 ? 'odd' : 'even'} num${i}" id="${q.questionNumber}" >
                    <table>
                        <tbody>
                        <tr class="first">
                            <td class="interest">${q.interest1.text}</td>
                            <td class="checkbox">
                                <input type="checkbox"
                                       name="unknown_${q.id}_${q.interest1.id}"
                                       class="checks" value="${q.interest1.id}"
                                       <g:if test="${q.interest1Known == Boolean.FALSE}">checked</g:if> >
                            </td>
                            <td rowspan="2">
                                <div class="rounded-corners rating-bars">
                                    <table>
                                        <tr>
                                            <td>
                                                1 <input type="radio" name="${"radio_"+q.id}" value="1"
                                                <g:if test="${q.result == 1}">checked</g:if>/>
                                            </td>
                                            <td>
                                                2 <input type="radio" name="${"radio_"+q.id}" value="2"
                                                         <g:if test="${q.result == 2}">checked</g:if>/>
                                            </td>
                                            <td>
                                                3 <input type="radio" name="${"radio_"+q.id}" value="3"
                                                         <g:if test="${q.result == 3}">checked</g:if>/>
                                            </td>
                                            <td>
                                                4 <input type="radio" name ="${"radio_"+q.id}" value="4"
                                                         <g:if test="${q.result == 4}">checked</g:if>/>
                                            </td>
                                            <td>
                                                5 <input type="radio" name="${"radio_"+q.id}" value="5"
                                                         <g:if test="${q.result == 5}">checked</g:if>/>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                                <div class="no-assoc">
                                    No Association
                                </div>
                                <div class="str-assoc">
                                    Strong Association
                                </div>
                            </td>
                        </tr>
                        <tr class="second">
                            <td class="interest">${q.interest2.text}</td>
                            <td class="checkbox">
                                <input type="checkbox"
                                       name="unknown_${q.id}_${q.interest2.id}"
                                       class="checks"
                                       id="${q.interest2.id}"
                                       value="${q.interest2.id}"
                                       <g:if test="${q.interest2Known == Boolean.FALSE}">checked</g:if> >
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </g:each>
        </div>

        <div class="continue">
            <button href="#" class="myButton" id="continue-button">Next</button>
        </div>
    </g:form>

</div>

</body>
</html>

<r:script>

    $(document).ready(function() {
        $('form').on('submit', function(e) {
            var isComplete = true;
            $("div.row").each(function () {
                //console.log($(this).find('input:checked'));
                if ($(this).find('input:checked').length == 0) {
                    $(this).addClass("error");
                    isComplete = false;
                }
            });
            if (!isComplete) {
                alert('Please enter a rating for each row\nor check the "I don\'t know" box.');
            }
            return isComplete;
        });
        $("input[type='checkbox']").click(function () {
            var $this = $(this);
            if ($this.is(':checked')) {
                var row = $this.parents(".row");
                row.removeClass("error");
                row.find("input[type=radio]").prop('checked', false);
            }
        });
        $("input[type='radio']").click(function () {
            var $this = $(this);
            if ($this.is(':checked')) {
                var row = $this.parents(".row");
                row.removeClass("error");
                row.find("input[type=checkbox]").prop('checked', false);
            }
        });
    });
</r:script>

