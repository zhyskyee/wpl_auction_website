$(document).ready(function(){
	
	    var indextime="";
		$("#search").click(function(e){
			   e.preventDefault();
		    var date = $("#auction_date1").val();
			$.ajax({
            url: "http://localhost:8080/item/timeslots",
            type: 'get',
            dataType: 'text',
            contentType :"application/json;charset=utf-8",
            data:({
                'date': date
             }),
             
            success: function(data) {
                  console.log(data);
                  var datajson = JSON.parse(data);
                  var data2 = eval(datajson);
                  var myselect=document.getElementById("myselector");
                  indextime=myselect.selectedIndex ;
              
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
        var myselect=document.getElementById("myselector");
        
        indextime=myselect.selectedIndex ;
        console.log(indextime);
		$("#indextime").val(indextime);
		console.log($("#indextime").val());
        $("#submitbutton").click(function(){

    		var indextime=$("#myselector option:selected").attr("id");
    		console.log(indextime);
    		$("#indextime").val(indextime);
//            var title = $("#title").val();
//            var date = $("#auctiondate").val();
//            var description= $("#description").val();
//            var address= $("#address").val();
//            var minimumprice= $("#minimumprice").val();
//            var date1= $("#auctiondate1").val();
//            console.log(date1);
//            var date = date1 + " "+"08:00:00";
//            $("#auctiondate").val(date);
//            var date = new Date();
    		var date1 = $("#auction_date1").val();
    		var date = date1 + " "+"08:00:00";
    		$("#auctiondate").val(date);
//            console.log($("#auctiondate").val());
			$("#postForm").submit();
			return true;
//            var indextime=$("#myselector option:selected").attr("id");
//            console.log($("#myselector option:selected").attr("id"));
            
//			function addURLParam(url, name, value){
//		        url +=  "?" + name + "=" + value;
//		        return url;
//		    }
            
 //           var tourl = addURLParam("http://localhost:8080/item/new", "indextime", indextime);
            //tourl = tourl + "&auction_date="+date;
//            $.ajax({
//            url: tourl,
//            type: 'post',
//            dataType: 'text',
//            contentType :"application/json;charset=utf-8",
//            data:JSON.stringify({
//                'title': title,
//                'address': address,
//                'description':description,
//                'min_price':minimumprice,
//                'auction_date':date,
//                'indextime':indextime
//            }),
//            success: function(data) {
//                  var datajson = JSON.parse(data);
//                  console.log(data);
//                  if(datajson.answer == "success"){
//                    alert("success");
//                    window.location.href="http://localhost:8080";
//                 }
//                
//
//                
//                        },
//            error: function(err) {
//                alert("Error loading JS File" + err);
//            }
//          });
        });


       
         



        });
        
        


