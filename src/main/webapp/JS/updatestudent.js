var updateStudentApp = angular.module("updateStudentApp",['ngCookies']);
updateStudentApp.controller("updateStudentCtrl", function($scope, $http, $cookies) {
	
	$scope.passwordData = '';
	$scope.passwordEntered = '';

//	$scope.firstName = '';
//	$scope.lastName = '';
//	$scope.emailId = '';
	$scope.etag = '';

	$http({
		method : 'GET',
		url : 'rest/students/'+$cookies.get('studentId'),
		headers : {
			'If-None-Match' : $scope.etag
		}
	}).success(function(data, status, headers,config) {
		if($scope.etag=='' || $scope.etag!=headers()['etag']){
			$scope.etag = headers()['etag'];
			$scope.studentsData = data;
			$scope.firstName = data.firstName;
			$scope.lastName = data.lastName;
			$scope.emailId = data.emailId;
			$scope.passwordData = data.password;
		}

	}).error(
			function(data, status, headers,config) {
				alert(data.errorMessage);
			});


	$scope.updateUser = function() {
		$scope.passwordEntered = $scope.oldPassword;
//		if($scope.passwordEntered != $scope.passwordData){
//			alert("Please enter the correct password to change the password");
//			$scope.oldPassword ='';
//			return;
//		}else{
		$http({
			method : 'PUT',
			url : 'rest/students/'+$cookies.get('studentId'),
			data : { 
				"firstName" : $scope.firstName,
				"lastName" :  $scope.lastName,
				"emailId" :   $scope.emailId,
				"password" :  $scope.password,
				"userFlag" :  "Student"

			},
			headers : {
				'Content-Type' : 'application/json',
					'If-Match' : $scope.etag
			}

		}).success(function(data, status, headers,config) {
			alert("Record Updated Successfully");
			window.location.href = "HTML/Dashboard.html";
		}).error(function(data, status, headers,config) {
			alert("Something went wrong. Please try again");
			return;

		}) ;
//		}
	}

$scope.cancel = function(){
	window.location.href = "HTML/Dashboard.html";
}

});