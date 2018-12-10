$(document).ready(function() {
	$.ajax({
		url : "http://localhost:8080/item/allpost",
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
            	var itemid = data2[i].itemid;
            	var title = data2[i].title;
            	var ownerid = (data2[i].ownerid);
            	var address = (data2[i].address);
            	var description = (data2[i].description);
            	var auction_date = (data2[i].auction_date);
            	var min_price = (data2[i].min_price);
//            	var ownerid = data2[i].ownerid;
            	
             tableStr = tableStr + "<tr>"
               +"<td>"+ itemid + "</td>"
               +"<td>"+ title+ "</td>"
               +"<td>"+ ownerid + "</td>"
               +"<td>"+address + "</td>"
               +"<td>"+ description + "</td>"
               +"<td>"+ auction_date + "</td>"
               +"<td>"+ min_price+ "</td>"

               +"</tr>";
//            		console.log( data2[i].itemid);
//            		console.log( data2[i].title);
//            		console.log( data2[i].ownerid);
//            		console.log( data2[i].address);
//            		console.log( data2[i].description);
//            		console.log( data2[i].auction_date);
//            		console.log( data2[i].min_price);
            }


            //添加到div中
            $("#tableAjax").html(tableStr);
//            for(i = 0; i < data2.lemgth();i++){
//
//                $("#dtBasic tr").eq(i).find("td").html(data2[i].itemid);
//                $("#dtBasic tr").eq(i).find("td").html(data2[i].title);
//                $("#dtBasic tr").eq(i).find("td").html(data2[i].ownerid);
//                $("#dtBasic tr").eq(i).find("td").html(data2[i].address);
//                $("#dtBasic tr").eq(i).find("td").html(data2[i].description);
//                $("#dtBasic tr").eq(i).find("td").html(data2[i].auction_date);
//                $("#dtBasic tr").eq(i).find("td").html(data2[i].min_price);
//              
//            }
		},
		error : function(err) {
			alert("Error loading JS File"
					+ err);
		}
	});
	
	$("#deleteitem").click(
			function(e) {
				e.preventDefault();
				var itemid = $("#itemiddelete").val();
				var tourl = "http://localhost:8080/item/"+ itemid;
				
				$.ajax({
					url : tourl,
					type : 'delete',
					dataType : 'text',
					contentType : "application/json;charset=utf-8",

					success : function(data) {
						console.log(data);
		                var datajson = JSON.parse(data);
                        alert("success!");
					},
					error : function(err) {
						alert("Error loading JS File"
								+ err);
					}
				});

			});
	
})
