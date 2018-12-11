<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Super User</title>
    <script src="vendor/jquery/jquery.min.js"></script>
    <!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> -->
    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap core JavaScript -->
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap4.min.css">

    <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js"></script>
<script type="text/javascript" src="/js/manage.js"></script>
    <script>
      $(document).ready(function () {
         // Setup - add a text input to each footer cell
          $('#dtBasic thead tr').clone(true).appendTo( '#dtBasic thead' );
          $('#dtBasic thead tr:eq(1) th').each( function (i) {
              var title = $(this).text();
              $(this).html('<input type="text" placeholder="Search '+title+'">');
              
              $( 'input', this ).on( 'keyup change', function () {
                  if ( table.column(i).search() !== this.value ) {
                      table
                          .column(i)
                          .search( this.value )
                          .draw();
                  }
              } );
          } );
       
          var table = $('#dtBasic1').DataTable( {
              orderCellsTop: true,
              fixedHeader: true,
              scrollX: true
          } );
          $('.dataTables_length').addClass('bs-select');
          
          
          
          $('#dtBasic1 thead tr').clone(true).appendTo( '#dtBasic1 thead' );
          $('#dtBasic1 thead tr:eq(1) th').each( function (i) {
              var title = $(this).text();
              $(this).html('<input type="text" placeholder="Search '+title+'">');
              
              $( 'input', this ).on( 'keyup change', function () {
                  if ( table.column(i).search() !== this.value ) {
                      table
                          .column(i)
                          .search( this.value )
                          .draw();
                  }
              } );
          } );
       
          var table = $('#dtBasic1').DataTable( {
              orderCellsTop: true,
              fixedHeader: true,
              scrollX: true
          } );
          $('.dataTables_length').addClass('bs-select');
      });
    </script>
    <!-- Custom CSS -->
    <style>
      body {
        padding-top: 70px;
        /* Required padding for .navbar-fixed-top. Remove if using .navbar-static-top. Change if height of navigation changes. */
      }

      .othertop{margin-top:10px;}
    </style>
  </head>

  <body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
      <div class="container">
        <a class="navbar-brand" href="#">Super User</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item ">
              <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="${pageContext.request.contextPath}/profile">Profile</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="${pageContext.request.contextPath}/order">Order<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="${pageContext.request.contextPath}/sell">Sell</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="${pageContext.request.contextPath}/contact">Contact</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  <section>
    <div class="container">
      <h2>Super User</h1>
      <hr>
      <div class="row">
        <!-- left column -->
        <div class="col-md-3">
          <div class="text-center">
            <img src="images/thumbnails.png" class="img-reponsive img-circle" alt="avatar">
            <h5>Super User</h5>
          </div>
        </div>
        <div>
        <form id="all">
         <div class="col-lg-12" style=""><h1>Select a Time</h1></div>
             <div class="form-group">
              <label class="col-12 control-label">Select an date</label>
              <div class="col-8">
                  <div class="inline-block">
                      <input id="start_time" class="form-control" type="date" />
                  </div>
              </div>
             </div>
             <div class="form-group" style="display:none;">
              <label class="col-12 control-label">Select an end date</label>
              <div class="col-8">
                  <div class="inline-block">
                      <input id="end_time" class="form-control" type="date" />
                  </div>
              </div>
             </div>
         <button class="btn btn-danger" id="selectbutton">Search!</button>
      </form>
        </div>

        <!-- edit form column -->
        <div class="col-md-12 table-responsive">
          <h3>Order info</h3>
           <table id="dtBasic" class="display datatable table-striped table-bordered table-sm" cellspacing="0" width="100%">
           <thead>
              <tr>
                <th class="th-sm">Itemid
                  <i class="fa fa-sort" aria-hidden="true"></i>
                </th>
                <th class="th-sm">Title
                  <i class="fa fa-sort" aria-hidden="true"></i>
                </th>
                <th class="th-sm">Ownerid
                  <i class="fa fa-sort" aria-hidden="true"></i>
                </th>
                <th class="th-sm">Address
                  <i class="fa fa-sort" aria-hidden="true"></i>
                </th>
                <th class="th-sm">Description
                  <i class="fa fa-sort" aria-hidden="true"></i>
                </th>
                <th class="th-sm">Auction Date
                  <i class="fa fa-sort" aria-hidden="true"></i>
                </th>
                <th class="th-sm">Minimum Price
                  <i class="fa fa-sort" aria-hidden="true"></i>
                </th>
              </tr>
            </thead>
            <tbody id="tableAjax">

            </tbody>
            <tfoot>
              <tr>
                <th></th>
                <th></th>
                <th></th>
                <th></th><th></th><th></th>
                <th></th>
              </tr>
            </tfoot>
          </table>
          <br><br><br>
          
          

      </div>
          
          
       <div class="col-md-12 table-responsive">
          <div class="form-group">
             <label class="col-12 control-label">Select an Item Id and Time</label>
              <div class="col-8">
                  <div class="inline-block">
                      <input id="selectitemid" class="form-control" type="text" />
                  </div>
              </div>
              <button class="btn btn-danger" id="selectbutton1">Show!</button>
             </div>
             <div class="col-lg-12">
                <input id="itemid" class="" type="text" />
             </div>
              <div class="col-lg-12" id="selecttime" style="display: block;">
                        <select id="myselector">
                          <option id="0">8:00 - 8:20</option>
                          <option id="1">8:20 - 8:40</option>
                          <option id="2">8:40 - 9:00</option>
                          <option id="3">9:20 - 9:40</option>
                          <option id="4">9:40 - 10:00</option>
                          <option id="5">10:00 - 10:20</option>
                          <option id="6">10:20 - 10:40</option>
                          <option id="7">10:40 - 11:00</option>
                          <option id="8">11:00 - 11:20</option>
                          <option id="9">11:20 - 11:40</option>
                          <option id="10">11:40- 12:00</option>
                          <option id="11">12:00 - 12:20</option>
                          <option id="12">12:20 - 12:40</option>
                          <option id="13">12:40 - 13:00</option>
                          <option id="14">13:00 - 13:20</option>
                          <option id="15">13:20 - 13:40</option>
                          <option id="16">13:40 - 14:00</option>
                          <option id="17">14:00 - 14:20</option>
                          <option id="18">14:20 - 14:40</option>
                          <option id="19">14:40 - 15:00</option>
                          <option id="20">15:00 - 15:20</option>
                          <option id="21">15:20 - 15:40</option>
                          <option id="22">15:40 - 16:00</option>
                          <option id="23">16:00 - 16:20</option>
                        </select>


                      </div>
                      <div class="col-lg-12"><button class="btn btn-info" id="submitbutton">Submit</button></div>
  

    
          
          

      </div>
    </div>
  </div>
  </section>
    
  </body>

</html>
