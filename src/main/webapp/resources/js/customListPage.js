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
function check() {
  d=document;
  myArray = [];
  for (h=0;h<32;h++) {
    myArray[h] = d.getElementById('t'+(h+1)).value;
  }
  for (a=0;a<32;a++) {
    for (b=1;b<33;b++) {
    d.getElementById('t'+(a+1)).options[b].style.display = "block";
      for (c=0;c<32;c++) {
        if(d.getElementById('t'+(a+1)).options[b].value == myArray[c]) {
          d.getElementById('t'+(a+1)).options[b].style.display = "none";
        }
      }
    }
  }
}