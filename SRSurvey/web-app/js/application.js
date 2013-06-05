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
    $("#continue-button").hide();
    $('form#rating-form').click( function( e ) {

        if($('input#field1:radio:checked').length<1&&($("#term1:checkbox").length!= $("#term1:checkbox:checked").length
            &&$("#term2:checkbox").length!= $("#term2:checkbox:checked").length)){


        }
        else{
            $("#continue-button").show();
        }


    });

    $(".fancybox").fancybox();


});

