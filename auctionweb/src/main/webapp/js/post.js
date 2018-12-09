$(document).ready(function(){
		$("#search").click(function(e){
			   e.preventDefault();
		     var date = $(#auctiondate).val();
			   $.ajax({
            url: "http://localhost:8080/item/timeslots",
            type: 'get',
            dataType: 'text',
            contentType :"application/json;charset=utf-8",
            data:JSON.stringify({
                'date': date
             }),
            success: function(data) {
                  
                  var datajson = JSON.parse(data);
                  var data2 = eval(datajson);
                  var myselect=document.getElementById("myselector");
                  var index=myselect.selectedIndex ;

                  for(i=0;i<24;i++){
                       if(data2[i] == false){
                          
                           myselect.options[i].hide();
                       }


                  }

                
                        },
            error: function(err) {
                alert("Error loading JS File" + err);
            }
        });
		});


        $("#submitbutton").click(function(e){
            e.preventDefault();
            var title = $("#itemtitle").val();
            var date = $("#auctiondate").val();
            var description= $("#description").val();
            var address= $("#address").val();
            var minimumprice= $("#minimumprice").val();
            var date= $("#auctiondate").val();
            var indextime=$("myselector").selectedIndex;

            $.ajax({
            url: "http://localhost:8080/item/new",
            type: 'post',
            dataType: 'jsonp',
            contentType :"application/json;charset=utf-8",
            data:JSON.stringify({
                'title': title,
                'address': address,
                'description':description,
                'minimumprice':minimumprice,
                'date':date,
                'indextime':indextime

            }),
            success: function(data) {
                  var datajson1 = JSON.stringify(data);
                  var datajson = JSON.parse(datajson1);
                  if(datajson.answer == "successful"){
                    alert("successful");
                 }
                

                
                        },
            error: function(err) {
                alert("Error loading JS File" + err);
            }
        });
        });


       
         



        });
        
        


