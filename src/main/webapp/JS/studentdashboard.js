var studentDashboard = angular.module("studentDashboard",['ngCookies']);

//Consumes student course details web service
studentDashboard.controller("studentDashboardCtrl", function($scope, $http,$cookies) {
	$scope.status=0;
	///////
	$scope.studentId=$cookies.get('studentId');
	$scope.courseId=0;
	$scope.scheduleId=0;
	
	$http.get(
			
	"rest/students/session")
		.success(
				function(data, status, headers, config) {
					if (data == "") {
						$scope.loggedIn = false;
					}else{
						$scope.studentId = data.studentId;
					$scope.loggedIn = true;}
				}).error(function(data, status, headers, config) {
					alert(data);
				})
	
	/////////
	$http ({
		method : 'GET',
		url : 'rest/students/'+$scope.studentId+'/courses'
	})
	.success(function(data,status,headers,config){
		$scope.studentCourseSchedule = data;
		
		$scope.status = status;
	})
	.error(function(data, status, headers,config) {
		alert(data.errorMessage);
		$scope.firstName = $cookies.get('firstName');
		$scope.lastName = $cookies.get('lastName');
		$scope.email = $cookies.get('emailId');
		$scope.status = status;
	});
	
// Consumes logout web service
$scope.logout = function(){
	var cookies = $cookies.getAll();
	angular.forEach(cookies, function (v, k) {
	    $cookies.remove(k);
	});
	$http ({
		method : 'POST',
		url : 'rest/students/logout'
	}).success(function(data,status,headers,config){
		alert(data);
	}
			)
	window.location.href = "HTML/Login.html";
}

//redirects to home page
$scope.gotoCourses = function(){
	window.location.href = "#/";
}

//Redirects to edit page
$scope.editProfile = function(){
	window.location.href = "HTML/UpdateStudent.html";
}

//Consumes drop course web service
$scope.dropCourse = function(courseId,scheduleId){
	$http({
		method : 'DELETE',
		url : 'rest/students/drop?studentId='+$scope.studentId+'&courseId='+courseId+'&scheduleId='+scheduleId
	 }).success(function(data, status, headers, config) {
		 $scope.message = data;
		 $scope.status = status;
		 alert("Successfully dropped the course")
		 location.reload();
	 }).error(function(data, status, headers, config) {
		 alert(data.errorMessage);
		 $scope.status = status;
	 });
}
});