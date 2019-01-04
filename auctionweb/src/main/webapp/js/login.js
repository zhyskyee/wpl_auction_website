
$(document)
		.ready(
				function() {
					setInterval(showTime, 1000);
					function timer(obj, txt) {
						obj.text(txt);
					}
					function showTime() {
						var today = new Date();
						var weekday = new Array(7)
						weekday[0] = "Monday"
						weekday[1] = "Tuesday"
						weekday[2] = "Wednesday"
						weekday[3] = "Thursday"
						weekday[4] = "Friday"
						weekday[5] = "Saturday"
						weekday[6] = "Sunday"
						var y = today.getFullYear() + "-";
						var month = today.getMonth() + "-";
						var td = today.getDate() + " ";
						var d = weekday[today.getDay()];
						var h = today.getHours() + ":";
						var m = today.getMinutes() + ":";
						var s = today.getSeconds();
						timer($("#Y"), y);
						timer($("#MH"), month);
						timer($("#TD"), td);
						timer($("#D"), d);
						timer($("#H"), h);
						timer($("#M"), m);
						timer($("#S"), s);
					}

					var meg = $("#alertholder");
					if (meg.html() == "") {
						meg.hide();
					}
					if (localStorage.getItem("state") == 1) {
						$("#profile").css('display', 'block');
						$("#sell").css('display', 'block');
						$("#order").css('display', 'block');
						$("#signinform").css('display', 'none');
						$("#signout").css('display', 'block');
						$("#showusername").text(
								localStorage.getItem("username"));
						$("#signout").css('display', 'block');
					}

					$
							.ajax({
								url : "http://localhost:8080/item/curitem",
								type : 'get',
								dataType : 'text',
								contentType : "application/json;charset=utf-8",
								// data : JSON.stringify({
								// }),
								success : function(data) {
									console.log(data)
									if (data != null) {
										var datajson = JSON.parse(data);
										localStorage.setItem("title",
												datajson.title);
										$("#title").text(
												localStorage.getItem("title"));
										localStorage.setItem("itemid",
												datajson.itemid);
										$("#itemid").text(
												localStorage.getItem("itemid"));

										localStorage.setItem("address",
												datajson.address);
										$("#address")
												.text(
														localStorage
																.getItem("address"));
										localStorage.setItem("description",
												datajson.description);
										$("#description")
												.text(
														localStorage
																.getItem("description"));
										localStorage.setItem("min_price",
												datajson.min_price);
										$("#min_price").text(
												localStorage
														.getItem("min_price"));
										localStorage.setItem("deal_price",
												datajson.deal_Price);
										$("#deal_price").text(
												localStorage
														.getItem("deal_price"));
										console.log(localStorage
												.getItem("deal_price"));
										localStorage.setItem("auction_date",
												datajson.auction_date);
										$("#auction_date")
												.text(
														localStorage
																.getItem("auction_date"));
										localStorage.setItem("ownerid",
												datajson.ownerid);
										$("#ownerid")
												.text(
														localStorage
																.getItem("ownerid"));
									}
								},
								error : function(err) {
									console.log(err);
									// alert("Error loading JS File"+ err);
								}
							});

					$("#submitLogin")
							.click(
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
										$
												.ajax({
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
														var datajson = JSON
																.parse(data);
														if (datajson.answer == "Success") {
															$("#profile").css(
																	'display',
																	'block');
															$("#sell").css(
																	'display',
																	'block');
															$("#order").css(
																	'display',
																	'block');
															$("#signinform")
																	.css(
																			'display',
																			'none');
															$("#signout").css(
																	'display',
																	'block');
															$("#showusername")
																	.text(
																			username);
															$("#signout").css(
																	'display',
																	'block');
															localStorage
																	.setItem(
																			"state",
																			1);
															localStorage
																	.setItem(
																			"username",
																			username);
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

					$("#signoutbutton")
							.click(
									function(e) {
										e.preventDefault();
										$
												.ajax({
													url : "http://localhost:8080/user/logout",
													type : 'post',
													dataType : 'text',
													contentType : "application/json;charset=utf-8",
													data : JSON.stringify({}),
													success : function(data) {
														console.log(data);
														// var datajson =
														// JSON.parse(data);
														if (data == "redirect") {
															localStorage
																	.setItem(
																			"state",
																			0);
															localStorage
																	.removeItem("username");
															window.location.href = "http://localhost:8080";
														}
													},
													error : function(err) {
														alert("Error loading JS File"
																+ err);
													}
												});
									});

					$("#bidbutton")
							.click(
									function(e) {
										e.preventDefault();
										var price = $("#price").val();
										var min_price = parseInt(localStorage
												.getItem("min_price"));
										var deal_price = parseInt(localStorage
												.getItem("deal_price"));
										if ((localStorage.getItem("state") == 1)) {
											if ((price > min_price)
													&& (price > deal_price)) {
												$
														.ajax({
															url : "http://localhost:8080/item/bid",
															type : 'post',
															dataType : 'text',
															contentType : "application/json;charset=utf-8",
															data : JSON
																	.stringify({

																		'itemid' : localStorage
																				.getItem("itemid"),
																		'price' : price
																	}),
															success : function(
																	data) {
																// var itemjson1
																// =
																// JSON.stringify(data);
																var itemjson = JSON
																		.parse(data);
																if (itemjson.answer == "success") {
																	$(
																			"#bidprice")
																			.val(
																					itemjson.price);
																	$(
																			"#bidmessage")
																			.val(
																					"you bid successful! Your price is:");
																	$(
																			"mybidprice")
																			.val(
																					itemjson.price);
																}
															},
															error : function(
																	err) {
																alert("Error loading JS File"
																		+ err);
															}

														});
											} else {
												alert("The price is less than current price or minimum price!");
											}
										} else {
											alert("Please sign in first!");
										}

									})

				

				})
					window.onload = function() {
						// 定时发送请求
						setInterval(
								function() {
									var now = (new Date()).toLocaleString();
									if (localStorage.getItem("username") == "admin") {
										$("#manage").css('display', 'block');
									}
									var src ="/images/"+ localStorage.getItem("title")+".jpg";
									$("#itemimg").attr("src", src);
									$
											.ajax({
												url : "http://localhost:8080/item/curitem",
												type : 'get',
												dataType : 'text',
												contentType : "application/json;charset=utf-8",
												// data : JSON.stringify({
												// }),
												success : function(data) {
													console.log(data);
													if (data != null) {
														var datajson = JSON
																.parse(data);
														localStorage.setItem(
																"title",
																datajson.title);
														$("#title")
																.text(
																		localStorage
																				.getItem("title"));
														localStorage
																.setItem(
																		"itemid",
																		datajson.itemid);
														$("#itemid")
																.text(
																		localStorage
																				.getItem("itemid"));
														localStorage
																.setItem(
																		"address",
																		datajson.address);
														$("#address")
																.text(
																		localStorage
																				.getItem("address"));
														localStorage
																.setItem(
																		"description",
																		datajson.description);

														$("#description")
																.text(
																		localStorage
																				.getItem("description"));
														localStorage
																.setItem(
																		"min_price",
																		datajson.min_price);
														console
																.log(localStorage
																		.getItem("min_price"));
														$("#min_price")
																.text(
																		localStorage
																				.getItem("min_price"));
														localStorage
																.setItem(
																		"deal_price",
																		datajson.deal_Price);
														console.log("wa"+localStorage
																.getItem("deal_price"));
														console.log(datajson.deal_Price);

														$("#deal_price")
																.innerHTML(
																		localStorage
																				.getItem("deal_price"));
														console
																.log(localStorage
																		.getItem("deal_price"));
														localStorage
																.setItem(
																		"auction_date",
																		datajson.auction_date);
														$("#auction_date")
																.text(
																		localStorage
																				.getItem("auction_date"));
														localStorage
																.setItem(
																		"ownerid",
																		datajson.ownerid);
														$("#ownerid")
																.text(
																		localStorage
																				.getItem("ownerid"));
													}
												},
												error : function(err) {
													console.log(err);
													//		    						alert("Error loading JS File"+ err);
												}
											});
								}, 5000);
					}