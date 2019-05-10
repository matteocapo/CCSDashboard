<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

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
    
    <!-- 	date	 -->
	<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
	
	
</head>
<body>
	

<div class="wrapper">
	<!-- 
	<div id="_mdspcontent">
	</div>			
	 -->
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
            <c:if test="${assets.size() > 1}">
            	<ul class="nav">
	                <li class="active">
	                    <a style="position: inherit; margin-left: 23px; margin-right: 23px;">
	                      <p>
	                    	<div class="btn-group btn-group-toggle" data-toggle="buttons">
								 <label class="btn btn-secondary active" style ="height: 40px; width: 90.55px; padding-left: 5px; padding-right: 5px;">
								 	<input type="radio" name="options" id="option1" autocomplete="off" onchange = "showSingleMachine()" checked>
								 		<marquee behavior="alternate" direction="left" scrollamount="1">Single Machine</marquee>
								 </label>
								 <label class="btn btn-secondary" style ="height: 40px; width: 94.33px; padding-left: 5px; padding-right: 5px;">
								 	<input type="radio" name="options" id="option2" autocomplete="off" onchange = "showCompareMachines()">
								 		<marquee behavior="alternate" direction="left" scrollamount="1">Compare Machines</marquee>
								 </label>
							</div>
	                      </p>
	                    </a>
	                </li>
	            </ul>
		    </c:if>
            <ul class="nav">
                <li class="active">
                    <a href="${pageContext.request.contextPath}">
                        <i class="pe-7s-date"></i>
                        <p>Set interval time</p>
                    </a>
                </li>
             </ul>
             <div id ="lateral-assets">
	             <c:forEach items="${assets}" var="food"> 
		             	<ul class="nav">
		             			<label class="block" for="${food.key}">
					                <li class="active">
						                	<a>
						                        <i class="pe-7s-graph2"></i>
						                        <p>${food.key}<input type="radio" id="${food.key}" name="asset" value="${food.value}" style="float: right;margin-top: 8px;" form = "indexprovatime" checked></p>
						                    </a>
						             </li>
						        </label>     
			             </ul>
			     </c:forEach>
		     </div>
             
            <!-- 
            <c:forEach items="${asset_name}" var="val_name">
	            <ul class="nav">
	                <li class="active">
	                    <a href="${pageContext.request.contextPath}">
	                        <i class="pe-7s-look"></i>
	                        <p>${val_name}</p>
	                    </a>
	                </li>
	             </ul>
	   		</c:forEach>
	   		 --> 
    	</div>
    </div>

    <div class="main-panel">
    
        <nav class="navbar navbar-default navbar-fixed">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navigation-example-2">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">Ccs Dashboard</a>
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
            	<div id="single-machine">
	                <div class="row">               
	                    <div class="center-block col-md-5" style="float:none;">
	                        <div class="card">
	                          <div class="header">
	                                <h4 class="title">Choose interval time for get datas</h4>
	                            </div>
	                            <div class="content">
		                            <form action="${pageContext.request.contextPath}/indexprova" method="get" id="indexprovatime">
		                            	<input class="form-control" type="text" name="datetimes" style = "cursor: default;" readonly>
		                                <div class="footer">
		                                    <hr>
		                                    <input type="submit" value="Submit interval time"/>
		                                </div>
		                            </form>
	                            </div>
	                        </div>
	                    </div>
	                </div>
           		</div>
           		<div id="compare-machines" style="display:none;">
	                <div class="row">               
	                    <div class="center-block col-md-5" style="float:none;">
	                        <div class="card">
	                          <div class="header">
	                                <h4 class="title">Choose the machines to compare</h4>
	                            </div>
		                            <div class="content">
			                            <c:forEach items="${assets}" var="food"> 
			                            	<div>
				                            	<input type="checkbox" id="${food.value}" name="${food.key}" value="${food.value}" form="indexprovatime2">
											  	<label for="${food.value}">${food.key}</label>
									        </div>
										</c:forEach>
		                                <div class="footer">
		                                    <hr>
		                                </div>
	                            	</div>
	                       		</div>
	                    	</div>
	               		</div>
		                <div class="row">               
		                    <div class="center-block col-md-5" style="float:none;">
		                        <div class="card">
		                          <div class="header">
		                                <h4 class="title">Choose interval time for get machine perfomance datas</h4>
		                            </div>
		                            <div class="content">
			                            <form action="${pageContext.request.contextPath}/comparemachines" method="get" id="indexprovatime2" onsubmit="return numAssets()">
			                            	<input class="form-control" type="text" name="datetimes" style = "cursor: default;" readonly>
			                                <div class="footer">
			                                    <hr>
			                                    <input type="submit" value="Submit interval time"/>
			                                </div>
			                            </form>
		                            </div>
		                        </div>
		                    </div>
		                </div>
           			</div>
       			</div>
			</div>
		
        


        <footer class="footer">
            <div class="container-fluid">
            <!--
            	<h1>${auth}</h1>
            	
            	<select id="food" name="fooditems">
				    <c:forEach items="${assets}" var="food">
				        <option value="${food.key}">
				            ${food.value}
				        </option>
				    </c:forEach>
				</select>
            	
            	  
	           	<c:forEach items="${asset_id}" var="val_id">
	            		<h1>${val_id}</h1>
	   			</c:forEach>
	   			
	   			<c:forEach items="${asset_name}" var="val_name">
	            		<h1>${val_name}</h1>
	   			</c:forEach>
            	
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
		
	<script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>
	<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
	<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
	
	<!-- MS OS bar-->
    <script src="https://static.eu1.mindsphere.io/osbar/v4/js/main.min.js"></script>
	
	
	<!-- 	send date -->
	

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
	</script>
	
	<!-- script data -->
	<script>
	$(function() {
	  $('input[name="datetimes"]').daterangepicker({
	    timePicker: true,
	    startDate: moment().startOf('hour'),
	    endDate: moment().startOf('hour').add(32, 'hour'),
	    timePicker24Hour: true,
	    ignoreReadonly: true,
	    allowInputToggle: true,
	    locale: {
	      format: 'M/DD/YYYY hh:mm A '
	    }
	  });
	});
	</script>
	
	<!-- script Raw Material -->
	<script type="text/javascript">
		function showSingleMachine() {
			  var x = document.getElementById("single-machine");
			  var y = document.getElementById("lateral-assets");
			  
			  if(y.style.display === "none"){
				y.style.display = "block";
			  } else {
			    y.style.display = "none";
			  }
			  
			  if (x.style.display === "none") {
			    x.style.display = "block";
			  } else {
			    x.style.display = "none";
			  }
			  
			  var x = document.getElementById("compare-machines");
			  if (x.style.display === "none") {
			    x.style.display = "block";
			  } else {
			    x.style.display = "none";
			  }
		}	
	</script>
	
	<!-- script performance -->
	<script type="text/javascript">
		function showCompareMachines() {
			  var x = document.getElementById("single-machine");
			  var y = document.getElementById("lateral-assets");
			  
			  if(y.style.display === "none"){
				y.style.display = "block";
			  } else {
			    y.style.display = "none";
			  }
			  
			  if (x.style.display === "none") {
			    x.style.display = "block";
			  } else {
			    x.style.display = "none";
			  }
			  
			  var x = document.getElementById("compare-machines");
			  if (x.style.display === "none") {
			    x.style.display = "block";
			  } else {
			    x.style.display = "none";
			  }
		}
	</script>
	
	<!-- script per la gestione del numero di assets -->
	<script type="text/javascript">
		function numAssets() {
			var x = 0;
			<c:forEach items="${assets}" var="food"> 
				if (document.getElementById("${food.value}").checked){
					x = x+1;
				}        	
			</c:forEach>
			if(x<2){
				alert("Please, select al least 2 machines");
			    return false;
			}
		}
	</script>
	
	<!-- script os bar -->
	<script type="text/javascript">
		_mdsp.init({
		    title: "CCS Dashboard",
		    appId: "_mdspcontent",
		    appInfoPath: "${pageContext.request.contextPath}/assets/app-info.json"
		});
	</script>
	
		
</html>
