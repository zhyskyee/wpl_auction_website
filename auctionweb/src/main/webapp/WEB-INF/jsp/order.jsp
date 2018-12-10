<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>User order summary page</title>
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
<script type="text/javascript" src="/js/order.js"></script>
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
       
          var table = $('#dtBasic').DataTable( {
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
        <a class="navbar-brand" href="#">My Order</a>
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
            <li class="nav-item active">
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
      <h2>User Order Summary</h1>
      <hr>
      <div class="row">
        <!-- left column -->
        <div class="col-md-3">
          <div class="text-center">
            <img src="images/thumbnails.png" class="img-reponsive img-circle" alt="avatar">
            <h5>Your order's details</h5>
          </div>
        </div>

        <!-- edit form column -->
        <div class="col-md-9 table-responsive">
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
          
          
          <input class="form-control" type="number" min="0" id="itemiddelete">
          <button id="deleteitem" class="btn btn-danger">Delete</button>
      </div>
    </div>
  </div>
  </section>
    
  </body>

</html>
