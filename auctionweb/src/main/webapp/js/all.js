$(document).ready(function() {
	$("#selectbutton").click(
			function(e) {
				e.preventDefault();
				var start_time1 = $("#start_time").val();
				var end_time1 = $("#end_time").val();
				start_time = start_time1 +" "+"00:00:00";
				end_time = end_time1 +" "+"00:00:00";
				console.log(start_time1);
				
				$.ajax({
					url : "http://localhost:8080/item/all",
					type : 'get',
					dataType : 'text',
					contentType : "application/json;charset=utf-8",
					data : ({
						'start_time' : start_time,
						'end_time' : end_time
					}),
					success : function(data) {
						console.log(data);
						var data2 = $.parseJSON(data);
			            var tableStr = "";
			            var len = data2.length;
			            console.log(data[1]);
			            for ( var i = 0; i < len; i++) {
			            	var itemid = data2[i].itemid;
			            	var title = data2[i].title;
			            	var ownerid = (data2[i].ownerid);
			            	var address = (data2[i].address);
			            	var description = (data2[i].description);
			            	var auction_date = (data2[i].auction_date);
			            	var min_price = (data2[i].min_price);
//			            	var ownerid = data2[i].ownerid;
			            	
			             tableStr = tableStr + "<tr>"
			               +"<td>"+ itemid + "</td>"
			               +"<td>"+ title+ "</td>"
			               +"<td>"+ ownerid + "</td>"
			               +"<td>"+address + "</td>"
			               +"<td>"+ description + "</td>"
			               +"<td>"+ auction_date + "</td>"
			               +"<td>"+ min_price+ "</td>"

			               +"</tr>";
//			            		console.log( data2[i].itemid);
//			            		console.log( data2[i].title);
//			            		console.log( data2[i].ownerid);
//			            		console.log( data2[i].address);
//			            		console.log( data2[i].description);
//			            		console.log( data2[i].auction_date);
//			            		console.log( data2[i].min_price);
			            }
			            $("#tableAjax").html(tableStr);
					},
					error : function(err) {
						alert("Error loading JS File"
								+ err);
					}
				});

			});
	$("#selectbutton1").click(
			function(e) {
				e.preventDefault();
				var itemid = $("#selectitemid").val();
				
				console.log(itemid);
				var tourl =  "http://localhost:8080/item/allbids" +  "?"+"itemid="+itemid;
				console.log(tourl);
				$.ajax({
					url : tourl,
					type : 'get',
					dataType : 'text',
					contentType : "application/json;charset=utf-8",

					success : function(data) {
						console.log(data);
						var data2 = $.parseJSON(data);
			            var tableStr = "";
			            var len = data2.length;
			            console.log(data[1]);
			            for ( var i = 0; i < len; i++) {
			            	var bidderid = data2[i].bidderid;
			            	var price = data2[i].price;
			            	
			             tableStr = tableStr + "<tr>"
			               +"<td>"+ bidderid + "</td>"
			               +"<td>"+  price+ "</td>"
			               +"</tr>";
			            		console.log( data2[i].bidderid);
			            		console.log( data2[i].price);
//			            		console.log( data2[i].ownerid);
//			            		console.log( data2[i].address);
//			            		console.log( data2[i].description);
//			            		console.log( data2[i].auction_date);
//			            		console.log( data2[i].min_price);
			            }
			            $("#tableAjax1").html(tableStr);
					},
					error : function(err) {
						alert("Error loading JS File"
								+ err);
					}
				});

			});
	
})