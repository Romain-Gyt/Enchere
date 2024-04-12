$(document).ready(function() {
    $('.errors, .error_list').each(function() {
        if ($.trim($(this).text()) === '') {
            $(this).hide();
        }
    });
});