<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/img/favicon.ico">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

	<title>CCS Dashboard</title>

	<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
    <meta name="viewport" content="width=device-width" />


    <!-- Bootstrap core CSS     -->
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet" />

    <!-- Animation library for notifications   -->
    <link href="${pageContext.request.contextPath}/assets/css/animate.min.css" rel="stylesheet"/>

    <!--  Light Bootstrap Table core CSS    -->
    <link href="${pageContext.request.contextPath}/assets/css/light-bootstrap-dashboard.css?v=1.4.0" rel="stylesheet"/>


    <!--  CSS for Demo Purpose, don't include it in your project     -->
    <link href="${pageContext.request.contextPath}/assets/css/demo.css" rel="stylesheet" />


    <!--     Fonts and icons     -->
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    <link href='https://fonts.googleapis.com/css?family=Roboto:400,700,300' rel='stylesheet' type='text/css'>
    <link href="${pageContext.request.contextPath}/assets/css/pe-icon-7-stroke.css" rel="stylesheet" />
    
    <!-- css grafo time series -->

</head>
<body>
			
<div class="wrapper">

    <div class="sidebar" data-color="blue" data-image="${pageContext.request.contextPath}/assets/img/sidebar-6.JPG">

    <!--

        Tip 1: you can change the color of the sidebar using: data-color="blue | azure | green | orange | red | purple"
        Tip 2: you can also add an image using data-image tag

    -->

    	<div class="sidebar-wrapper">
            <div class="logo">
            <a>
            
            <img src="${pageContext.request.contextPath}/assets/img/28_main_r.png" class ="simple-text">
            <!-- 
                <a href="http://www.creative-tim.com" class="simple-text">
                    Creative Tim
                </a>
             -->
            </a>
            </div>

            <ul class="nav">
                <li class="active">
                    <a href="${pageContext.request.contextPath}/">
                        <i class="pe-7s-date"></i>
                        <p>Set interval time</p>
                    </a>
                </li>
                <!-- classi che definiscono i loghi:
                	class="pe-7s-user"
                	class="pe-7s-note2"
                	class="pe-7s-news-paper"
                	class="pe-7s-science"
                	class="pe-7s-map-marker"
                	class="pe-7s-bell"
                	class="pe-7s-rocket"
            	-->
            	</ul>
    	</div>
    </div>

    <div class="main-panel">
    <c:choose>
    	<c:when test="${testalert == 'no'}">
    	</c:when>
   		<c:otherwise>
       	 	<div class="bs-example">
    			<div class="alert alert-warning">
        			<a href="#" class="close" data-dismiss="alert">&times;</a>
        			<strong>Warning!</strong> update the correct oee click <a href="${pageContext.request.contextPath}/indexprova?asset=${asset}&datetimes=${testalert}">here</a>.
    			</div>
			</div>
    	</c:otherwise>
	</c:choose>
    
	        <nav class="navbar navbar-default navbar-fixed">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navigation-example-2">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand">Ccs Dashboard</a>
                    <a class="navbar-brand">${date}</a>
                </div>
                <div class="collapse navbar-collapse">
                
                	<!--  
                    <ul class="nav navbar-nav navbar-left">
                        <li>
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <i class="fa fa-dashboard"></i>
								<p class="hidden-lg hidden-md">Dashboard</p>
                            </a>
                        </li>
                        <li class="dropdown">
                              <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <i class="fa fa-globe"></i>
                                    <b class="caret hidden-lg hidden-md"></b>
									<p class="hidden-lg hidden-md">
										5 Notifications
										<b class="caret"></b>
									</p>
                              </a>
                              <ul class="dropdown-menu">
                                <li><a href="#">Notification 1</a></li>
                                <li><a href="#">Notification 2</a></li>
                                <li><a href="#">Notification 3</a></li>
                                <li><a href="#">Notification 4</a></li>
                                <li><a href="#">Another notification</a></li>
                              </ul>
                        </li>
                        <li>
                           <a href="">
                                <i class="fa fa-search"></i>
								<p class="hidden-lg hidden-md">Search</p>
                            </a>
                        </li>
                    </ul>
					-->
					
					
					<!-- 
					<ul class="nav navbar-nav navbar-right">
                        <li>
                           <a href="">
                               <p>Account</p>
                            </a>
                        </li>
                        <li class="dropdown">
                              <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <p>
										Dropdown
										<b class="caret"></b>
									</p>

                              </a>
                              <ul class="dropdown-menu">
                                <li><a href="#">Action</a></li>
                                <li><a href="#">Another action</a></li>
                                <li><a href="#">Something</a></li>
                                <li><a href="#">Another action</a></li>
                                <li><a href="#">Something</a></li>
                                <li class="divider"></li>
                                <li><a href="#">Separated link</a></li>
                              </ul>
                        </li>
                        <li>
                            <a href="#">
                                <p>Log out</p>
                            </a>
                        </li>
						<li class="separator hidden-lg"></li>
                    </ul>
                	-->
                	
                	
                </div>
            </div>
        </nav>


        <div class="content">
            <div class="container-fluid">
                
                <div class="row" id="classification">
                	<c:forEach items="${raw_data_model}" var="val">
                		<div class="col-md-2" id="min-w-oee">
	                        <div class="card ">
	                            <div class="header">
	                                <button type="button" class="btn btn-link" data-toggle="collapse" data-target="#collapseOne" style = "background-color: transparent; color: coral; float: right; border-color: transparent;"><i class="fa fa-minus"></i></button>
	                            
	                                <h4 class="title">${val.getName()}</h4>
	                                <p class="category">Raw Material consumption</p>
	                            </div>
	                            <div class="content" id="centering-oee">
	                            	<h4>Used: ${val.getMaterialeConsumato()} m</h4>
	                            	<h4>Scrap: ${val.getMaterialeScartato()} m</h4>
	                            </div>
	                            <div class="footer">
	                                    <!-- <hr> -->
	                            </div>
	                        </div>
	                    </div>
					</c:forEach>
                </div>
            </div>
        </div>


        <footer class="footer">
            <div class="container-fluid">
            	<!--  
                <nav class="pull-left">
                    <ul>
                        <li>
                            <a href="#">
                                Home
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                Company
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                Portfolio
                            </a>
                        </li>
                        <li>
                            <a href="#">
                               Blog
                            </a>
                        </li>
                    </ul>
                </nav>
                <!--  
                -->
                <!-- 
                <p class="copyright pull-right">
                    &copy; <script>document.write(new Date().getFullYear())</script> <a href="http://www.creative-tim.com">Creative Tim</a>, made with love for a better web
                </p>
                -->
            </div>
        </footer>

    </div>
</div>


</body>

    <!--   Core JS Files   -->
    <script src="${pageContext.request.contextPath}/assets/js/jquery.3.2.1.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/assets/js/GaugeMeter.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js" type="text/javascript"></script>

	<!--  Charts Plugin -->
	<script src="${pageContext.request.contextPath}/assets/js/chartist.min.js"></script>

    <!--  Notifications Plugin    -->
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap-notify.js"></script>

    <!--  Google Maps Plugin    -->
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=YOUR_KEY_HERE"></script>

    <!-- Light Bootstrap Table Core javascript and methods for Demo purpose -->
	<script src="${pageContext.request.contextPath}/assets/js/light-bootstrap-dashboard.js?v=1.4.0"></script>

	<!-- Light Bootstrap Table DEMO methods, don't include it in your project! -->
	<script src="${pageContext.request.contextPath}/assets/js/demo.js"></script>
	
	<!-- charts -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.bundle.js"></script>	
	

	<script type="text/javascript">
    	$(document).ready(function(){

        	demo.initChartist();

        	//$.notify({
            //	icon: 'pe-7s-gift',
            //	message: "Welcome to <b>Light Bootstrap Dashboard</b> - a beautiful freebie for every web developer."

            //},{
            //    type: 'info',
            //    timer: 4000
            //});

    	});
    	
    	$(".GaugeMeter").gaugeMeter();
	</script>
	
	<!-- script primo istogramma
	<script type="text/javascript">
	/**
	 * chart js 
	 */
	var popCanvas = document.getElementById("popChart");
	var popCanvas = $("#popChart");
	var popCanvas = document.getElementById("popChart").getContext("2d");
	var barChart = new Chart(popCanvas, {
		  type: 'bar',
		  data:{
		    labels: ["Total", "Discard"],
		    datasets: [{
		    	label: 'Pieces',
		    	data: [${domenica}, ${luned�}],
		    	fill:false,
		    	backgroundColor: [
		        'rgba(255, 99, 132, 0.6)',
		        'rgba(54, 162, 235, 0.6)'
		        ]
		    }]
	
		  }		
		});
	</script> -->
	
	
	<!-- script grafo velocit�
	<script type="text/javascript">
	var speedCanvas = document.getElementById("speedChart");

	var speedData = {
	  labels: ["10", "20", "30", "40", "50", "60"],
	  datasets: [{
	    label: "PPM",
	    data: [${min1}, ${min2}, ${min3}, ${min4}, ${min1}, ${min6}],
	  }]
	};

	var chartOptions = {
	  legend: {
	    display: true,
	    position: 'top',
	    labels: {
	      boxWidth: 80,
	      fontColor: 'black'
	    }
	  }
	};

	var lineChart = new Chart(speedCanvas, {
	  type: 'line',
	  data: speedData,
	  options: chartOptions
	});
	</script>
	-->	
	<!-- scripts collapse -->
	<script type="text/javascript">
	$(".header").click(function () {
		
	    $header = $(this);
	    //getting the next element
	    $content = $header.next();
	    //open up the content needed - toggle the slide- if visible, slide up, if not slidedown.
	    $content.slideToggle(500, function () {
	        //execute this after slideToggle is done
	        //change text of header based on visibility of content div
	        $header.text(function () {
	            //change text based on condition
	            
	            //return $content.is(":visible") ? "Collapse" : "Expand";
	        });
	    });

	});
	</script>
	
	<script type="text/javascript">
	$(".header").click(function () {
		$(this).find("i").toggleClass('fa fa-plus');
		$(this).find("i").toggleClass('fa fa-minus');
	});
	</script>
</html>
