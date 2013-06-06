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

    $(".extra").hide();
    $("#add-boxes").click(function( e ) {
        $(".extra").toggle();
        $(".indent-click").toggle();

    });
    $("#minus-boxes").click(function( e ) {
        $(".extra").toggle();
        $(".indent-click").toggle();

    });

    $('form#interest-form input[name=box1]').bind('click keyup', function( e ) {
        if ( $('input[name=box1]', '#interest-form').hasClass('error') ) {
            $('input[name=box1]', '#interest-form').removeClass('error');
        }
    });

    $('form#interest-form').bind('keyup', function( e ) {

        if($('input[name=box1]', '#interest-form').val()==""&&
            $('input[name=box2]', '#interest-form').val()==""&&
            $('input[name=box3]', '#interest-form').val()==""&&
            $('input[name=box4]', '#interest-form').val()==""&&
            $('input[name=box5]', '#interest-form').val()==""&&
            $('input[name=box6]', '#interest-form').val()==""&&
            $('input[name=box7]', '#interest-form').val()==""&&
            $('input[name=box8]', '#interest-form').val()==""&&
            $('input[name=box9]', '#interest-form').val()==""&&
            $('input[name=box10]', '#interest-form').val()==""){

            $('input[name=box1]', '#interest-form').addClass("error");

        }

    });
    $('#next').bind('click', function( e ) {

        if($('input[name=box1]', '#interest-form').val()==""&&
            $('input[name=box2]', '#interest-form').val()==""&&
            $('input[name=box3]', '#interest-form').val()==""&&
            $('input[name=box4]', '#interest-form').val()==""&&
            $('input[name=box5]', '#interest-form').val()==""&&
            $('input[name=box6]', '#interest-form').val()==""&&
            $('input[name=box7]', '#interest-form').val()==""&&
            $('input[name=box8]', '#interest-form').val()==""&&
            $('input[name=box9]', '#interest-form').val()==""&&
            $('input[name=box10]', '#interest-form').val()==""){

            $("input[name=box1]", "#interest-form").addClass("error");
            $.fancybox({
                content: $('#error')
            });
        }
        else{       alert("what?");
            //submit form and move to rating page
        }

    });

});

