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
    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap core JavaScript -->
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap4.min.css">

    <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js"></script>

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
        <a class="navbar-brand" href="#">Auction website</a>
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
                <th class="th-sm">Description
                  <i class="fa fa-sort" aria-hidden="true"></i>
                </th>
                <th class="th-sm">Address
                  <i class="fa fa-sort" aria-hidden="true"></i>
                </th>
                <th class="th-sm">Auction Date
                  <i class="fa fa-sort" aria-hidden="true"></i>
                </th>
                <th class="th-sm">Bd
                  <i class="fa fa-sort" aria-hidden="true"></i>
                </th>
                <th class="th-sm">Ba
                  <i class="fa fa-sort" aria-hidden="true"></i>
                </th>
                <th class="th-sm">SqFt
                  <i class="fa fa-sort" aria-hidden="true"></i>
                </th>
                <th class="th-sm">Closing Bid
                  <i class="fa fa-sort" aria-hidden="true"></i>
                </th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>House 1</td>
                <td>917 E KINGSWAY DRIVE Gretna, LA 70056</td>
                <td>2011/04/25</td>
                <td>3</td><td>2</td><td>1,350</td>
                <td>$320,800</td>
              </tr>
              <tr>
                <td>House 2</td>
                <td>728 OLIVE ST Coatesville, PA 19320</td>
                <td>2011/07/25</td>
                <td>3</td><td>1</td><td>1,221</td>
                <td>$170,750</td>
              </tr>
              <tr>
                <td>House 3</td>
                <td>3394 LOWNESDALE RD East Cleveland, OH 44112</td>
                <td>2009/01/12</td>
                <td>3</td><td>2</td><td>1,241</td>
                <td>$186,000</td>
              </tr>
              <tr>
                <td>Ashton Cox</td>
                <td>355 MAPLE RD Amherst, NY 14221</td>
                <td>2009/01/12</td>
                <td>2</td><td>2</td><td>1,100</td>
                <td>$286,000</td>
              </tr>
              <tr>
                <td>Cedric Kelly</td>
                <td>1936 TOMMY LEE COOK RD Palmetto, GA 30268</td>
                <td>2012/03/29</td>
                <td>3</td><td>3</td><td>1,227</td>
                <td>$433,060</td>
              </tr>
              <tr>
                <td>Airi Satou</td>
                <td>3260 CLARKE ST Choctaw, OK 73020</td>
                <td>2008/11/28</td>
                <td>2</td><td>2</td><td>1,224</td>
                <td>$162,700</td>
              </tr>
              <tr>
                <td>Brielle Williamson</td>
                <td>97 BIRGE ST Torrington, CT 06790</td>
                <td>2012/12/02</td>
                <td>3</td><td>3</td><td>2,100</td>
                <td>$372,000</td>
              </tr>
              <tr>
                <td>Herrod Chandler</td>
                <td>5464 N 56TH ST Milwaukee, WI 53218</td>
                <td>2012/08/06</td>
                <td>3</td><td>1</td><td>982</td>
                <td>$137,500</td>
              </tr>
              <tr>
                <td>Rhona Davidson</td>
                <td>35563 WICK RD Romulus, MI 48174</td>
                <td>2010/10/14</td>
                <td>3</td><td>1</td><td>1,232</td>
                <td>$127,900</td>
              </tr>
              <tr>
                <td>Colleen Hurst</td>
                <td>1817 HUBBARD RD Madison, OH 44057</td>
                <td>2009/09/15</td>
                <td>4</td><td>3</td><td>2,294</td>
                <td>$205,500</td>
              </tr>
              <tr>
                <td>Sonya Frost</td>
                <td>402 E GEORGIA AVE Bessemer City, NC 28016</td>
                <td>2008/12/13</td>
                <td>3</td><td>2</td><td>2,164</td>
                <td>$303,600</td>
              </tr>
              <tr>
                <td>Jena Gaines</td>
                <td>16662 SEASHORE HWY Georgetown, DE 19947</td>
                <td>2008/12/19</td>
                <td>2</td><td>2</td><td>1,662</td>
                <td>$190,560</td>
              </tr>
              <tr>
                <td>Quinn Flynn</td>
                <td>2654 S PALMETTO AVE Sanford, FL 32773</td>
                <td>2013/03/03</td>
                <td>2</td><td>2</td><td>1,221</td>
                <td>$342,000</td>
              </tr>
              <tr>
                <td>Charde Marshall</td>
                <td>674 SILVER LAKE ST Athol, MA 01331</td>
                <td>2008/10/16</td>
                <td>3</td><td>2</td><td>1,791</td>
                <td>$470,600</td>
              </tr>
            </tbody>
            <tfoot>
              <tr>
                <th>Description</th>
                <th>Address</th>
                <th>Auction Date</th>
                <th>Bd</th><th>Ba</th><th>SqFt</th>
                <th>Closing Bid</th>
              </tr>
            </tfoot>
          </table>
      </div>
    </div>
  </div>
  </section>
    
  </body>

</html>
