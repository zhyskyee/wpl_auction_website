$(function() {

	$("#resetbutton").click(
			function() {
				var username = $("#username").val();
				var resetpassword = $("#resetpassword").val();
				var phone = $("#phone").val();
				
				$("#forgetpasswordForm").submit();
				return true;
			});
	
	//监听回车键
	$(document).keyup(function(event){
		  if(event.keyCode ==13){
		   $("#submitRegister").trigger("click");
		  }
	});
})