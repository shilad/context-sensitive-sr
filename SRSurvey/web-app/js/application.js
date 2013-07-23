if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}

var pageId = Math.floor(Math.random() * 1294967295) + 1;

function ajaxLog() {
    var message = pageId + "\t" + new Date().getTime();
    for (var i = 0; i < arguments.length; i++) {
        message += "\t" + arguments[i];
    }
    $.post('/SRSurvey/logging/append', { message : message })
        .fail(function () {
            console.log('logging failed for message ' + message);
        });

}

$().ready(function() {
    ajaxLog('load\t' + window.location.href);
});

/*
 * Makes sure that console.log will not generate an error on any browser.
 */
console = console || {};

if (!console.log) { console.log = function() {} }
