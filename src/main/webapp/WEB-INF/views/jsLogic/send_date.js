/**
 * send_date js 
 */
(function ($) {
  "use strict";

  var bwBaseURL = "send_date.php";


  $("button.submit").click(function(e){
    e.preventDefault();

    var dateTime = $("#input-datetime").val(); 
    
    
    console.log(dateTime);
    
    $.ajax(bwBaseURL, {
      method: "GET",
      dataType: "json",
      data: {
    	  	"name":dateTime
      },

      success: function(response) {
    	  if(response.status === "success") {
  	  	alert("event added");
    	  }
      },
      error: function(xhr) {
        alert("Error " + xhr.responseText);
      }
      
    });
  });

})(jQuery);