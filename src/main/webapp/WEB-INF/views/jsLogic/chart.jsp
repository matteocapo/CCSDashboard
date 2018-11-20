/**
 * chart js 
 */
new Chart(document.getElementById("popChart"),
		{"type":"bar",
		"data":{
			"labels":["Total", "Discard"],
			"datasets":[{
				"label":"Pieces",
				"data":[${domenica}, ${lunedì}],
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
);