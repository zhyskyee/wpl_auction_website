localStorage.setItem("state", 0);
localStorage.setItem("username", "");

$(document).ready(function() {
				var meg = $("#alertholder");
				if (meg.html() == "") {
					meg.hide();
				}


                $.ajax({
				url : "http://localhost:8080/item/curitem",
				type : 'get',
				dataType : 'text',
				contentType : "application/json;charset=utf-8",
				data : JSON.stringify({
							}),
					success : function(data) {
						console.log(data);
							var datajson = JSON.parse(data);
                            $("#itemname").val(datajson.title);
                            $("#bidprice").val(datajson.price);
                            $("#itemdescription").val(datajson.description);
								
											},
					error : function(err) {
						alert("Error loading JS File"+ err);
											}
										});


				$("#submitLogin").click(
								function(e) {
									e.preventDefault();
									var username = $("#username").val();
									var password = $("#password").val();
									if (username.trim().length == 0
											|| password.trim().length == 0) {
										meg.show();
										meg
												.html("  Please complete your input.");
										return false;
									}
									$.ajax({
											url : "http://localhost:8080/user/login",
											type : 'post',
											dataType : 'text',
											contentType : "application/json;charset=utf-8",
											data : JSON.stringify({
												'username' : username,
												'password' : password
											}),
											success : function(data) {
												console.log(data);
												var datajson = JSON.parse(data);
												if (datajson.answer == "Success") {
													$("#profile").css('display','block');
													$("#sell").css('display','block');
													$("#order").css('display','block');
													$("#signinform").css('display','none');
													$("#signout").css('display','block');
													$("#showusername").text(username);
													$("#signout").css('display','block');
													localStorage.setItem("state",1);
													localStorage.setItem("username",username);
												}
											},
											error : function(err) {
												alert("Error loading JS File"
														+ err);
											}
										});
								});

				$(document).keyup(function(event) {
					if (event.keyCode == 13) {
						$("#submitLogin").trigger("click");
					}
				});

//				$("#signoutbutton").click(function(e) {
//					               e.preventDefault();
//					               $.ajax({
//											url : "http://localhost:8080/user/logout",
//											type : 'post',
//											dataType : 'text',
//											contentType : "application/json;charset=utf-8",
//											data : JSON.stringify({
//											}),
//											success : function(data) {
//												console.log(data);
//												var datajson = JSON.parse(data);
//												if (datajson.answer == "Success") {
//													localStorage.setItem("state",0);
//													localStorage.deleteItem("username");
//												}
//											},
//											error : function(err) {
//												alert("Error loading JS File"
//														+ err);
//											}
//									      });
//				});

				$("#bidbutton").click(function(e) {
									e.preventDefault();
									if (localStorage.getItem("state") == 1) {
										$.ajax({
												url : "http://localhost:8080/item/bid",
												type : 'post',
												dataType : 'text',
												contentType : "application/json;charset=utf-8",
												data : JSON.stringify({'username' : username}),
												success : function(data) {
													//var itemjson1 = JSON.stringify(data);
													var itemjson = JSON.parse(data);
													if (itemjson.answer == "Successful") {
														$("#bidprice").val(itemjson.price);
														$("#bidmessage").val("you bid successful! Your price is:");
														$("mybidprice").val(itemjson.price);
													}
												},
												error : function(err) {alert("Error loading JS File" + err);
												}

												});
									     }
									 else{
										   alert("Please sign in first!");
										  }
								
								})


				window.onload=function(){
			     // 定时发送请求
			       setInterval(function() {
				     var now = (new Date()).toLocaleString();
				       $.ajax({
				          url: "http://localhost:8080/item/bid",
				          type: 'post',
				          dataType: 'text',
				          contentType :"application/json;charset=utf-8",
				          data:JSON.stringify({
				          'request': now,
				           }),
				                  success: function(data) {
				                         
				                         var itemjson = JSON.parse(data);
				                             if(itemjson.answer == "Success"){
				                             $("#bidprice").val(itemjson.price);
				                             $("#bidmessage").val("you bid successful! Your price is:");
				                             $("mybidprice").val(itemjson.price);
				                             }
				
				               
				                       },
				                  error: function(err) {
				                      alert("Error loading JS File" + err);
				                       }
				       	
				       	 });
				         }, 5000);
				                }

			})