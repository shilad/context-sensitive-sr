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
           //alert($('input[name=field1]:checked', '#rating-form').val());
        if($('input[name=field1]:checked', '#rating-form').val()==null&&($("#term1:checkbox").length!= $("#term1:checkbox:checked").length
            &&$("#term2:checkbox").length!= $("#term2:checkbox:checked").length)){

            $("#continue-button").hide();

        }
        else{
            $("#continue-button").show();
        }


    });

    $(".fancybox").fancybox();


});

