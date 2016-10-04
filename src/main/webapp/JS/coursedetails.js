var coursedetailModule = angular.module("courseDetailModule", [ 'ngCookies' ]);

coursedetailModule
.controller(
		"courseDetController",
		function($scope, $http, $routeParams, $cookies) {
			$scope.courseId = $routeParams.id;

			$http.get(
					//"https://localhost:8080/StudentCourseRegistrationSystem/rest/students/session")
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

							$http(
									{
										method : 'GET',
										//url : 'https://localhost:8080/StudentCourseRegistrationSystem/rest/courses/'
											url : 'rest/courses/'
											+ $scope.courseId
									}).success(function(data, status, headers, config) {
										$scope.response = data;
									}).error(function(data, status, headers, config) {
										alert(data.errorMessage);
									});

			$scope.selectedSchedule = 0;
			$scope.toggleSelectionSchedule = function(scheduleId) {
				$scope.selectedSchedule = scheduleId;
			}

			$scope.enroll = function() {
				$scope.scheduleId = $scope.selectedSchedule;
				if($scope.scheduleId == ''){
					alert("Please select at least one schedule");
					return;
				}

				$scope.studentId = 0;
				$http
				.get(
						//"https://localhost:8080/StudentCourseRegistrationSystem/rest/students/session")
				"rest/students/session")
						.success(
								function(data, status, headers, config) {
									$scope.studentId = data.studentId;
									if (data == "") {
										$scope.loggedIn=false //vidya
										//window.location.href = "https://localhost:8080/StudentCourseRegistrationSystem/HTML/Login.html";
										window.location.href = "HTML/Login.html";
									} else {
										$scope.loggedIn=true; //vidya
										// alert("StudentID :
										// "+studentId);
										$http(
												{
													method : 'POST',
													//url : 'https://localhost:8080/StudentCourseRegistrationSystem/rest/students/'
														url : 'rest/students/'
														+ $scope.studentId
														+ '/enroll?courseId='
														+ $scope.courseId
														+ '&scheduleId='
														+ $scope.scheduleId,
														data : data,
														headers : {
															'Content-Type' : 'application/json'
														}

												})
												.success(
														function(
																data,
																status,
																headers,
																config) {
															 alert(data.errorMessage);
															//window.location.href = "https://localhost:8080/StudentCourseRegistrationSystem/HTML/Dashboard.html";
															window.location.href = "HTML/Dashboard.html";
														})
														.error(
																function(
																		data,
																		status,
																		headers,
																		config) {
																	alert(data.errorMessage);

																});
									}
								})
								.error(function(data, status, headers, config) {
									alert(data);
								})

			}

			$scope.register = function() {
				//window.location.href = "https://localhost:8080/StudentCourseRegistrationSystem/HTML/RegisterStudent.html";
				window.location.href = "HTML/RegisterStudent.html";
			}

			$scope.login = function() {
				//window.location.href = "https://localhost:8080/StudentCourseRegistrationSystem/HTML/Login.html";
				window.location.href = "HTML/Login.html";
			}
			$scope.back = function() {
				//window.location.href = "https://localhost:8080/StudentCourseRegistrationSystem/";
				window.location.href = "";
			}
			$scope.profile = function() {
				window.location.href = "HTML/Dashboard.html";
			}
			$scope.logout = function(){
				var cookies = $cookies.getAll();
				angular.forEach(cookies, function (v, k) {
					$cookies.remove(k);
				});
				$http ({
					method : 'POST',
					//url : 'https://localhost:8080/StudentCourseRegistrationSystem/rest/students/logout'
					url : 'rest/students/logout'
				}).success(function(data,status,headers,config){
					alert(data);
				}
				)
				//window.location.href = "https://localhost:8080/StudentCourseRegistrationSystem/HTML/Login.html";
				window.location.href = "HTML/Login.html";
			}

		});