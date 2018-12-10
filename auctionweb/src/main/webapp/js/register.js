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

	var img = document.getElementById('photo');
	var start = document.getElementById('start');
	start.addEventListener('click', startt);  
	/*转换函数*/  
	var base64 = null;
    function startt() {  
        var imgFile = new FileReader();  
        console.log(typeof img.files[0]);
        imgFile.readAsDataURL(img.files[0]);  
        imgFile.onload = function () {  
        	base64 = this.result; //base64数据    
        	console.log(base64);
//            imgShow.setAttribute('src', imgData);  
//            conte.value = imgData;  
//            len.innerHTML += imgData.length;  
        }  
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
//
// function addURLParam(url, name, value){
// url += (url.indexOf("?") == -1 ? "?" : "&");
// url += encodeURICompontent(name) + "=" + encodeURICompontent(value);
// return url;
// }
//                function base64(file, callback)
//                    {
//                    var coolFile = {};
//                    function readerOnload(e)
//                    {
//                     var base64 = btoa(e.target.result);
//                     coolFile.base64 = base64;
//                     callback(coolFile)
//                      };
//
//                    var reader = new FileReader();
//                    reader.onload = readerOnload;
//
//                    var file = file[0].photo[0];
//                    coolFile.filetype = file.type;
//                    coolFile.size = file.size;
//                    coolFile.filename = file.name;
//                    reader.readAsBinaryString(file);
//                    } 

//				function getImageBase64(img, ext) {
//				    var canvas = document.createElement("canvas");   //创建canvas DOM元素，并设置其宽高和图片一样
//				    canvas.width = img.width;
//				    canvas.height = img.height;
//				    var ctx = canvas.getContext("2d");
//				    ctx.drawImage(img, 0, 0, img.width, img.height); //使用画布画图
//				    var dataURL = canvas.toDataURL("image/" + ext);  //返回的是一串Base64编码的URL并指定格式
//				    canvas = null; //释放
//				    return dataURL;
//				}
//				

//				var img = $("#photo").files[0];
//				console.log(img);
//				var image = new Image();
//				image.src = img;
//				var base64 = null;
//				image.onload = function() {
//				    //这样就获取到了文件的Base64字符串
//				    base64 = getBase64Image(image);
//				    //Base64字符串转二进制
////				    var file = dataURLtoBlob(base64);
//				}					

				function addURLParam(url, name, value){
			        url +=  "?" + name + "=" + value;
			        return url;
			    }
                var tourl = addURLParam("http://localhost:8080/user/register", "confirmPass", confirmPass);
                console.log("reg:::", tourl)
//                base64($('input[type="file"]'), function(data)
//                 {
                console.log(base64);
				    $.ajax({
					url : tourl,
					type : 'post',
					dataType : 'text',
					contentType : "application/json;charset=utf-8",
					data : JSON.stringify({
						'username' : username,
						'password' : password,
						'email':email,
						'phone':phone,
						'photo':base64
					}),
					// console.log(data);
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
//			   });

			});
	
	// 监听回车键
	$(document).keyup(function(event){
		  if(event.keyCode ==13){
		   $("#submitRegister").trigger("click");
		  }
	});
})