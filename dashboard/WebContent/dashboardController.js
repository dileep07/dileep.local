var app = angular.module('dashboardApp', []);
app.controller('dashboardController', function($scope, $http ,$interval) {
  	
	var dashboardData;
	
	var httpRequest = function()
	{
		$http.get("/dashboard/midtier?operation=display")
		  .then(function(response) {
		      $scope.dashboardData = response.data;
		  	var midtierStatus,upCount = 0;
			  angular.forEach($scope.dashboardData,function(value){
				  if(value.Status_IISHealth.toLowerCase() == "ok")
					{
					  if(value.Status_TomcatHealth.toLowerCase() == "ok"){ upCount = upCount + 1;}
					  else{ midtierStatus = -1;}
					} 
			  });
			  if(upCount >= 0)
			  {
			  	if(midtierStatus == -1 || upCount == 0){
			  		$scope.midtierStatusClass = "alert alert-danger";
			  		$scope.midtierStatusMesg = "The service is running with issues ..!!";
			  	}
			  	else
			  		{
			  		$scope.midtierStatusClass = "alert alert-success";
			  		$scope.midtierStatusMesg = "The service is running on " +upCount+" nodes";
			  		}
			  }
		      
		      
		      
		  });
	}
	httpRequest();
	
   $scope.setLabel = function(status)
  {
	  if(status.toLowerCase() =="ok"){
		return "label label-success";  
	  }else
	  {
		return "label label-danger";  
	  }
  }
  
  $interval(httpRequest,5000);
});

