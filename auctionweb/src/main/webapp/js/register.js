$(document).ready(function() {
	var meg = $("#td-meg-div");
	// 说明message没有值，那么就隐藏。
	if (meg.html() == "") {
		meg.hide();
	}
	function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
	}
	function validatePhone(phone) {
		var re = /^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/;
		return re.test(String(phone).toLowerCase());
	}

	$("#submitregister").click(
			function(e) {
				e.preventDefault();
				var username = $("#username").val();
				var password = $("#password").val();
				var confirmPass = $("#confirmPass").val();
				var email = $("#email").val();
				var phone = $("#phone").val();
				if (username.trim().length == 0 || password.trim().length == 0
						|| confirmPass.trim().length == 0) {
					meg.show();
					meg.html("Please complete your inputs");
					return false;
				}
				if (username.length > 20 || username.length < 3) {
					meg.show();
					meg.html("Username length 3~20");
					return false;
				}
				if (password.length > 16 || password.length < 3) {
					meg.show();
					meg.html("Password length 3~16");
					return false;
				}
				if (confirmPass != password) {
					meg.show();
					meg.html("Password doesn't match!");
					return false;
				}
				if (!validateEmail(email)) {
					meg.show();
					meg.html("You have entered an invalid email address!");
					return false;
				}

				if (!validatePhone(phone)) {
					meg.show();
					meg.html("Invalid phone number!");
					return false;
				}

				function addURLParam(url, name, value){
				    url += (url.indexOf("?") == -1 ? "?" : "&");
				    url += encodeURICompontent(name) + "=" + encodeURICompontent(value);
				    return url;
				}
                var tourl = addURLParam("http://localhost:8080/user/register", "confirmPass=", confirmPass);
                console.log("reg:::", tourl)
				$.ajax({
					url : tourl,
					type : 'post',
					dataType : 'text',
					contentType : "application/json;charset=utf-8",
					data : JSON.stringify({
						'username' : username,
						'password' : password,
						'email':email,
						'phone':phone
					}),
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

			});
	
	//监听回车键
	$(document).keyup(function(event){
		  if(event.keyCode ==13){
		   $("#submitRegister").trigger("click");
		  }
	});
})