var loginApp = angular.module("loginApp", ['ngCookies','ngMessages'])

loginApp.controller("loginController", function($scope, $http,$cookies) {
	
	$scope.login = function(){

		
		$http ({
			method : 'POST',
			//url : 'https://localhost:8080/StudentCourseRegistrationSystem/rest/students/login',
			url : '../rest/students/login',
			data : { 
				"emailId" :   $scope.email,
				"password" :  $scope.password

			}
		})
		.success(function(data,status,headers,config){
			//$cookies.put('studentId', data.studentId);
			$scope.student = data;
			$scope.studentId = data.studentId;
			var now = new Date(),
		    // this will set the expiration to 6 months
		    exp = new Date(now.getFullYear(), now.getMonth()+6, now.getDate());
			$http.get("../rest/students/session").success(function(data, status, headers,config) {

				if($scope.studentId==data.studentId){
					$cookies.put('studentId',data.studentId,{expires:exp});
					$cookies.put('userFlag',data.userFlag,{expires:exp});
					$cookies.put('emailId', data.emailId);
					$cookies.put('password', data.password);
					$cookies.put('firstName', data.firstName);
					$cookies.put('lastName', data.lastName);
					$scope.loggedIn = true;
					$cookies.put('loggedIn',true);

					$scope.loginDetails = data;
					console.log("data : "+data)
					if(data.userFlag.trim().toLowerCase() == 'student'){
					window.location.href = "../HTML/Dashboard.html";
					}else{
						window.location.href = "../HTML/AdminDashboard.html";
					}
				}else{
					$scope.loggedIn = false;
					$cookies.put('loggedIn',false);
					alert("please login again!!");
					window.location.href = "HTML/Login.html";
				}
			}).error(function(data,status,headers,config){
				alert(data);
			})
			//$scope.firstName = $cookies.get('firstName');

		})
		.error(function(data, status, headers,config) {
			alert(data.errorMessage);
			window.location.href = "HTML/Login.html";
		});
	}
	
	$scope.register = function() {
		window.location.href = "HTML/RegisterStudent.html";
	}
})