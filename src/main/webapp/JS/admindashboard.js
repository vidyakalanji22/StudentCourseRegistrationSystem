var adminDashboard = angular.module("adminDashboard",['ngCookies']);


//consumes getCourses, getBooks, getSchedules, getCurriculum web services
adminDashboard.controller("addCourseController", function($scope, $http, $cookies) {
	
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
			 location.reload();
		}).error(
			function(data, status, headers,config) {
				alert("Something went wrong while creating the course. Please try again");
			
		}) ;
		
		
	}
	
});

//consumes disable course web service
adminDashboard.controller("disableCourseController", function($scope, $http) {
	$http({
		method : 'GET',
		//url : 'https://localhost:8080/StudentCourseRegistrationSystem/rest/courses'
			url : 'rest/courses'
	 }).success(function(data, status, headers, config) {
		 $scope.courseList = data;
		 $scope.status = status;
	 }).error(function(data, status, headers, config) {
		 alert("Something went wrong while displaying the course. Please try again");
		 $scope.status = status;
	 });
	$scope.disable = function(courseId){
	$http({
		method : 'DELETE',
		//url : 'https://localhost:8080/StudentCourseRegistrationSystem/rest/courses/'+courseId
		url : 'rest/courses/'+courseId
	 }).success(function(data, status, headers, config) {
		 $scope.message = data;
		 $scope.status = status;
		 alert("Successfully disabled the course")
		 location.reload();
	 }).error(function(data, status, headers, config) {
		 alert("Something went wrong while disabling the course. Please try again");
		 $scope.status = status;
	 });
	}
});

//consumes delete student web service
adminDashboard.controller("deleteStudentController", function($scope, $http, $cookies) {
	$http({
		method : 'GET',
		//url : 'https://localhost:8080/StudentCourseRegistrationSystem/rest/students'
			url : 'rest/students'
	 }).success(function(data, status, headers, config) {
		 $scope.studentList = data;
		 $scope.status = status;
	 }).error(function(data, status, headers, config) {
		 alert("error : "+status)
		 alert("Something went wrong while displaying the students. Please try again");
		 $scope.status = status;
	 });
	
	$scope.deleteStudent = function(studentId){
		$http({
			method : 'DELETE',
			//url : 'https://localhost:8080/StudentCourseRegistrationSystem/rest/students/'+studentId
			url : 'rest/students/'+studentId
		 }).success(function(data, status, headers, config) {
			 $scope.showMessage = true;
			 $scope.message = data;
			 $scope.status = status;
			 alert(data);
			 location.reload();
		 }).error(function(data, status, headers, config) {
			 if(data.studentId == studentId){
				 $scope.showMessage = true;
				 $scope.courseList = data.courseList;
				 $scope.message = "Cannot delete student with ID "+studentId +" as the student has enrolled for courses : ";
				 for (var i = 0; i < $scope.courseList.length; i++) {
					$scope.message = $scope.message.concat($scope.courseList[i].courseName +" ");
				}
				 $scope.link = data.links[1].toString().split(';')[0];
		 }else{
			 $scope.message = "Something went wrong while deleting student";
		 }
			 $scope.status = status;
		 });
	}
	
	//consumes logout web service
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
	
	//redirects to home page
	$scope.viewCourses = function(){
		//window.location.href = "https://localhost:8080/StudentCourseRegistrationSystem/#/";
		window.location.href = "#/";
	}
})