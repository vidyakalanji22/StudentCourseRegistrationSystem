var addCourseApp = angular.module("addCourseApp",[]);

//consumes add course web service
addCourseApp.controller("addCourseController", function($scope, $http) {
	
	$http({
		method : 'GET',
		//url : 'https://localhost:8080/StudentCourseRegistrationSystem/rest/schedules'
			url : 'rest/schedules'
	}).success(function(data, status, headers,config) {
												 $scope.schedules = data;
												 $scope.days = data.daysOfWeek;
												 console.log("data : "+data);
												 console.log("days : "+ data.daysOfWeek);
											}).error(
												function(data, status, headers,
												config) {
												alert("Something went wrong. Please try again");
												$scope.schedules = data;
											});
	
	$http({
		method : 'GET',
		//url : 'https://localhost:8080/StudentCourseRegistrationSystem/rest/curriculum'
			url : 'rest/curriculum'
	}).success(function(data, status, headers,config) {
												 $scope.curriculum = data;
												
											}).error(
												function(data, status, headers,
												config) {
												alert("Something went wrong. Please try again");
												$scope.curriculum = data;
											});
	
	
	$http({
		method : 'GET',
		//url : 'https://localhost:8080/StudentCourseRegistrationSystem/rest/books'
			url : 'rest/books'
	}).success(function(data, status, headers,config) {
												 $scope.books = data;
											}).error(
												function(data, status, headers,
												config) {
												alert("Something went wrong. Please try again");
												$scope.books = data;
											});
	
	$scope.selectedSchedule = [];
	$scope.toggleSelectionSchedule = function(scheduleId){
		var idx  = $scope.selectedSchedule.indexOf(scheduleId);
		if(idx>-1){
			$scope.selectedSchedule.splice(idx,1);
		}else{
			$scope.selectedSchedule.push({
				"scheduleId" : scheduleId
			});
		}
	}
	
	
	$scope.selectedCurriculum = [];
	$scope.toggleSelectionCurriculum = function(curriculumId){
		var idx  = $scope.selectedCurriculum.indexOf(curriculumId);
		if(idx>-1){
			$scope.selectedCurriculum.splice(idx,1);
		}else{
			$scope.selectedCurriculum.push({
				"curriculumId" : curriculumId
			});
		}
		
	}
	
	
	
	$scope.selectedBooks = [];
	$scope.toggleSelectionBooks = function(bookID){
		var idx  = $scope.selectedBooks.indexOf(bookID);
		
		if(idx>-1){
			$scope.selectedBooks.splice(idx,1);
		}else{
			$scope.selectedBooks.push({"bookID" : bookID});
		}
	}
	 
	$scope.updateData = function() {
		if(!$scope.courseForm.$valid){
			alert("Note: All fields are mandatory.\nEnter missing data and submit again.");
			return;
		}
		if($scope.selectedSchedule.length < 1 || $scope.selectedCurriculum.length < 1 || $scope.selectedBooks.length < 1 ){
			alert("Note : atleat one of the values must be selected for schedule , curriculum and book");
			return;
		}
		$http({
			method : 'POST',
			//url : 'https://localhost:8080/StudentCourseRegistrationSystem/rest/courses',
			url : 'rest/courses',
			data : { 
  				"courseName" :  $scope.coursename,
  				"courseDesc" :  $scope.coursedesc,
  				"courseAmount" : $scope.courseamount,
  				"scheduleList" : $scope.selectedSchedule,
  				"curriculumList":$scope.selectedCurriculum,
  				"booksList":    $scope.selectedBooks 
  			},
			headers : {
				'Content-Type' : 'application/json'
			}
			
		}).success(function(data, status, headers,config) {
			 alert("Record Created Successfully");
		}).error(
			function(data, status, headers,config) {
				alert("Something went wrong. Please try again");
			
		}) ;
		
		
	}
	
});