$(document).ready(function(){
		//---------------控制显示message的div--------------------
		var meg = $("#alertholder");
		// alert(message);
		//说明message没有值，那么就隐藏。
		if(meg.html()==""){
			meg.hide();
		}
		
		//------------------提交表单-----------------
		$("#submitLogin").click(function(){
			var username = $("#username").val();
			var password = $("#password").val();
			if(username.trim().length==0 || password.trim().length==0){
				meg.show();
				meg.html("  Please complete your input.");
				return false;
			}
			console.log(username+" : "+ password);
			$("#loginForm").submit(); 
			return true;
			 
		});
		
		//监听回车键
		$(document).keyup(function(event){
			  if(event.keyCode ==13){
			   $("#submitLogin").trigger("click");
			  }
		});
	})