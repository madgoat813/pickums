//Hides and shows the buttons for add edit and delete
//depending on how many products are selected.
$(".checkBox").click(function() {
    var checkboxValues = $(":checkbox:checked").length;
    if (checkboxValues > 1) {
        $("#editButton").hide(500);
        $("#newButton").hide(500);
        $("#deleteButton").show(500);
    } else if (checkboxValues === 0 ) {
        $("#editButton").hide(500);
        $("#deleteButton").hide(500);
        $("#newButton").show(500);
    } else {
        $("#editButton").show(500);
        $("#deleteButton").show(500);
        $("#newButton").hide(500);
    }
});
