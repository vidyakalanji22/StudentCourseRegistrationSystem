var courseListApp = angular.module("courseListApp", ['ngRoute','courseDetailModule','ngCookies']);
courseListApp.config([ '$routeProvider', function($routeProvider) {
	// set up routes
	$routeProvider.when('/', {
		templateUrl : 'HTML/ListCourses.html',
		controller : 'studentController',
		css : 'ListCourses.css'
	}).when('/coursedetails/:id', {
		templateUrl : 'HTML/CourseDetails.html',
		controller : 'courseDetController'
	})
	.otherwise({
		redirectTo : 'HTML/ListCourses.html'
	});

} ]);

courseListApp.controller('studentController',function($scope, $http, $cookies) {
	
	$http.get(
			
"rest/students/session")
	.success(
			function(data, status, headers, config) {
				if (data == "") {
					$scope.loggedIn = false;
				}else{
				$scope.loggedIn = true;}
			}).error(function(data, status, headers, config) {
				alert(data);
			})
	/*		
	$scope.loggedIn = $cookies.get('loggedIn');
	 alert(JSON.stringify($cookies));
	if(!$scope.loggedIn)
		$scope.loggedIn = false;*/
					$http({
							method : 'GET',
								url : 'rest/courses'
						 }).success(function(data, status, headers, config) {
							 $scope.courseList = data;
							 $scope.status = status;
						 }).error(function(data, status, headers, config) {
							 alert(data.errorMessage);
							 $scope.status = status;
						 });

						$scope.selectedSchedule = [];
						$scope.toggleSelectionSchedule = function(scheduleId) {
						var idx = $scope.selectedSchedule.indexOf(scheduleId);
						if (idx > -1) {
							$scope.selectedSchedule.splice(idx, 1);
						} else {
							$scope.selectedSchedule.push({
								"scheduleId" : scheduleId
							});
						}
					}

					$scope.getRangeCourses = function() {

						if ($scope.singleSelect == "date") {
							$http({
									method : 'GET',
									url : 'rest/courses?startDate='+$scope.start+'&endDate='+ $scope.end
									})
									.success(function(data, status, headers,config) {
												$scope.courseList = data;
												$scope.status = status;
											})
									.error(
											function(data, status, headers,config) {
												$scope.status = status;
													if(status == 404){
														alert("No courses found for this search criteria");
													}else{
														alert(data.errorMessage);
													}
											});

						} else if ($scope.singleSelect == "amount") {

							$http({
									method : 'GET',
									url : 'rest/courses?startAmount='+$scope.start+'&endAmount='+$scope.end
								})
									.success(function(data, status, headers,config) {
												$scope.courseList = data;
												$scope.status = status;
											})
									.error(
											function(data, status, headers,config) {
												if (status == 404) {
													alert("No courses found for this search criteria");
													$scope.status = status;
													$scope.courseList = data;
												} else {
													alert(data.errorMessage);
												}
											});

						}

					}

					$scope.register = function() {
						window.location.href = "HTML/RegisterStudent.html";
					}

					$scope.login = function() {
						window.location.href = "HTML/Login.html";
					}
					$scope.back = function(){
						//window.location.href = "https://localhost:8080/StudentCourseRegistrationSystem/";
						 window.history.back();
					}
					$scope.profile = function() {
						window.location.href = "HTML/Dashboard.html";
					}
				});


	