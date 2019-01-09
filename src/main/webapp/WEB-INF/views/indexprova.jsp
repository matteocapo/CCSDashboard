<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/img/favicon.ico">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

	<title>Light Bootstrap Dashboard by Creative Tim</title>

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
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,700,300' rel='stylesheet' type='text/css'>
    <link href="${pageContext.request.contextPath}/assets/css/pe-icon-7-stroke.css" rel="stylesheet" />
    
    <!-- css grafo time series -->

</head>
<body>
<div class="wrapper">

    <div class="sidebar" data-color="azure" data-image="${pageContext.request.contextPath}/assets/img/sidebar-5.jpg">

    <!--

        Tip 1: you can change the color of the sidebar using: data-color="blue | azure | green | orange | red | purple"
        Tip 2: you can also add an image using data-image tag

    -->

    	<div class="sidebar-wrapper">
            <div class="logo">
            <a>
            <img src="${pageContext.request.contextPath}/assets/img/28_main.jpg" class ="simple-text" height="140" width="230">
            <!-- 
                <a href="http://www.creative-tim.com" class="simple-text">
                    Creative Tim
                </a>
             -->
            </a>
            </div>

            <ul class="nav">
                <li class="active">
                    <a href="${pageContext.request.contextPath}/indexprovatime">
                        <i class="pe-7s-note"></i>
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
    	<c:when test="${testalert == no}">
    	</c:when>
   		<c:otherwise>
       	 	<div class="bs-example">
    			<div class="alert alert-warning">
        			<a href="#" class="close" data-dismiss="alert">&times;</a>
        			<strong>Warning!</strong> update the correct oee click <a href="${pageContext.request.contextPath}/indexprova?datetimes=${testalert}">here</a>.
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
                <div class="row">
                    <div class="col-md-2" id="min-w-oee">
                        <div class="card ">
                            <div class="header">
                                <h4 class="title">OEE</h4>
                                <p class="category">Overall Equipment Effectiveness</p>
                            </div>
                            <div class="content" id="centering-oee">
                            	<div class="GaugeMeter gaugeMeter" id="PreviewGaugeMeter_2" data-percent="${oee}" data-append="%" data-size="200" data-theme="Red-Gold-Green" data-back="RGBa(0,0,0,.1)" data-animate_gauge_colors="1" data-animate_text_colors="1" data-width="15" data-label="OEE" data-style="Arch" data-label_color="#FFF" data-id="PreviewGaugeMeter_2" style="width: 200px;"></div>
                            </div>
                            <div class="footer">
                                    <hr>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-5">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Intermediate OEE</h4>
                            </div>
                            <div class="content">
                                <!-- <div id="chartHours" class="ct-chart"></div>  -->
                                <div id="" class="content">
                                	<canvas id="intOEE" width="600" height="400"></canvas>
                                </div>
                                <div class="footer">
                                    <hr>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-5">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Pieces & discard pieces</h4>
                            </div>
                            <div class="content">
                                <!-- <div id="chartHours" class="ct-chart"></div>  -->
                                <div id="" class="content">
                                	<canvas id="popChart" width="600" height="400"></canvas>
                                </div>
                                <div class="footer">
                                    <hr>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card">
                            <div class="header">
                            	<h4 class="title">Stop list</h4>
                            	<div id="stopCodes">
	                            	 <c:forEach items="${error_codice}" var="val">
								    	<p class="category">Stop Code: <c:out value="${val.getErrorCode()}"/> occured at <c:out value="${val.getTimestamp()}"/></p>
									</c:forEach>
                            	</div>
                            </div>
                            <div class="footer">
                                    <hr>
                            </div>
                        </div>
                    </div>
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
		    	data: [${domenica}, ${lunedì}],
		    	fill:false,
		    	backgroundColor: [
		        'rgba(255, 99, 132, 0.6)',
		        'rgba(54, 162, 235, 0.6)'
		        ]
		    }]
	
		  }		
		});
	</script> -->
	
	
	<script>
	new Chart(document.getElementById("popChart"),
			{"type":"bar",
			 "data":{
				 "labels":["Total", "Scrap"],
				 "datasets":[{
					"label":"Pieces",
					"data":[${produzioni}, ${scarti}],
					"fill":false,
					"backgroundColor":[
						"rgba(255, 159, 64, 0.2)",
						"rgba(255, 99, 132, 0.2)"
					],
					"borderColor":[
						"rgb(255, 159, 64)",
						"rgb(255, 99, 132)"						
						],
						"borderWidth":1
						}
				]
		},
		"options":{"scales":{"yAxes":[{"ticks":{"beginAtZero":true}}]}}}
	);</script>
	
	<!-- Grafico OEE intermedi -->
	
	<script type="text/javascript">
	new Chart(document.getElementById("intOEE"),
			{"type":"bar",
			"data":{
				"labels":[${oees_name}],
				"datasets":[{
					"label":"OEEs",
					"data":[${oees_value}],
					"fill":false,
					"backgroundColor":[
						"rgba(255, 159, 64, 0.2)",
						"rgba(255, 99, 132, 0.2)"
					],
					"borderColor":[
						"rgb(255, 159, 64)",
						"rgb(255, 99, 132)"						
						],
						"borderWidth":1
						}
				]
		},
		"options":{"scales":{"yAxes":[{"ticks":{"beginAtZero":true}}]}}}
	);</script>
	
	<!-- script grafo a torta
	<script type="text/javascript">
	var oilCanvas = document.getElementById("oilChart");
	var oilData = {
	    labels: [
	        "Discarded pieces",
	        "Produced pieces"
	    ],
	    datasets: [
	        {
	            data: [${scarti}, ${produzioni}],
	            backgroundColor: [
	                "#FF6384",
	                "#63FF84"
	            ]
	        }]
	};

	var pieChart = new Chart(oilCanvas, {
	  type: 'pie',
	  data: oilData
	});
	</script>
	-->
	
	<!-- script grafo velocità
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
	<!-- script odometro -->
	<script type="text/javascript">
	</script>
</html>
