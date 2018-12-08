$(document).ready(function(){
		var meg = $("#alertholder");
		if(meg.html()==""){
			meg.hide();
		}
	
		$("#submitLogin").click(function(e){
			e.preventDefault();
			var username = $("#username").val();
			var password = $("#password").val();
			if(username.trim().length==0 || password.trim().length==0){
				meg.show();
				meg.html("  Please complete your input.");
				return false;
			}
			$.ajax({
            url: "http://localhost:8080/user/login",
            type: 'post',
            dataType: 'text',
            contentType :"application/json;charset=utf-8",
            data:JSON.stringify({
                'username': username,
                'password': password
            }),
            success: function(data) {
            		console.log(data);
                
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText , "---", textStatus , " ----", errorThrown);
            }
        });
		});
		
		//监听回车键
		$(document).keyup(function(event){
			  if(event.keyCode ==13){
			   $("#submitLogin").trigger("click");
			  }
		});
	})