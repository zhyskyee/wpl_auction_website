$(document).ready(function() {

	$("#resetbutton").click(function(e) {
		e.preventDefault();
			
			var password = $("#password").val();
            var username = $("#username").val();
            var tourl = "http://localhost:8080/user/forgetpassword"+"?username="+username+"&password="+password;
			$.ajax({
				url : tourl,
				type : 'post',
				dataType : 'text',
				contentType : "application/json;charset=utf-8",

				success : function(data) {
					console.log(data);
					var datajson = JSON.parse(data);
					if (datajson.answer == "Success") {
					alert("successful");
                    window.location.href="http://localhost:8080"; 
					}
				},
				error : function(err) {
					alert("Error loading JS File"
							+ err);
				}
			});
	})
	
	//监听回车键
	$(document).keyup(function(event){
		  if(event.keyCode ==13){
		   $("#submitRegister").trigger("click");
		  }
	});
})