$(document).ready(function() {
	$("#selectbutton").click(
			function(e) {
				e.preventDefault();
				var start_time1 = $("#start_time").val();
//				var end_time1=(parseInt(start_time1))+1;
//				var end_time1 = start_time1.getFullYear()+"-" + (start_time1.getMonth()) + "-" + (1+ (start_time1.getDay())); 
				start_time = start_time1 +" "+"00:00:00";
//				console.log(start_time1.getDay());
//				end_time = (end_time1).toString() +" "+"00:00:00";
				console.log(start_time1);
				console.log(start_time);
//				console.log(end_time1);
//				console.log(end_time);
				function getNewDay(dateTemp, days) {
				    var dateTemp = dateTemp.split("-");
				    var nDate = new Date(dateTemp[1] + '-' + dateTemp[2] + '-' + dateTemp[0]); //转换为MM-DD-YYYY格式  
				    var millSeconds = Math.abs(nDate) + (days * 24 * 60 * 60 * 1000);
				    var rDate = new Date(millSeconds);
				    var year = rDate.getFullYear();
				    var month = rDate.getMonth() + 1;
				    if (month < 10) month = "0" + month;
				    var date = rDate.getDate();
				    if (date < 10) date = "0" + date;
				    return (year + "-" + month + "-" + date);
				}
			var end_time = (getNewDay(start_time1, 1)) +" "+"00:00:00" ;
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
				$.ajax({
		            url: "http://localhost:8080/item/timeslots",
		            type: 'get',
		            dataType: 'text',
		            contentType :"application/json;charset=utf-8",
		            data:({
		                'date': start_time1
		             }),
		             
		            success: function(data) {
		                  console.log(data)
		                  var datajson = JSON.parse(data);
		                  var data2 = eval(datajson);
		                  var myselect=document.getElementById("myselector");
		                  var index=myselect.selectedIndex ;
		                  console.log(index);
		                  for(i=0;i<24;i++){
		                       if(data2[i].answer == 'false'){    
		                    	   $("#"+i).hide();
		                       }
		                  }
		                
		                        },
		            error: function(err) {
		                alert("Error loading JS File" + err);
		            }
		        });

			});
	
	$("#submitbutton").click(
			function(e) {
				e.preventDefault();
				var itemid = $("#selectitemid").val();
				var start_time1 = $("#start_time").val();
				var myselect=document.getElementById("myselector");
		        var indextime=myselect.selectedIndex ;
				console.log(itemid);
				var start_time1 = $("#start_time").val();
				console.log(start_time1);
				start_time1 = start_time1+" "+"08:00:00";
				console.log(start_time1);
				$.ajax({
					url : "http://localhost:8080/item/resetdate",
					type : 'post',
					dataType : 'text',
					data :JSON.stringify({
						'auction_date' : start_time1,
						'itemid':itemid,
						'indextime':indextime
						
					}),
					contentType : "application/json;charset=utf-8",

					success : function(data) {
						alert("success!");

			            
			       
					},
					error : function(err) {
						alert("Error loading JS File"
								+ err);
					}
				});

			});
	
})