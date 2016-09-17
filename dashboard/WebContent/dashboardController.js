var app = angular.module('dashboardApp', []);
app.controller('dashboardController', function($scope, $http ,$interval) {
  	
	var dashboardData;
	var web_refresh_interval;
	var setWindowRefresh = function(){
	$http.get("/dashboard/readMainConfig?operation=readconfig").then(function(response){
		web_refresh_interval = response.data.Web_Refresh_Interval;
		if(!web_refresh_interval)
			{
			web_refresh_interval = 10;
			
			}
		 $interval(httpRequest,web_refresh_interval*1000);
	});
	}
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
		      
		      
		      
		  },function(){
			  $scope.serverErrorMesgClass = "alert alert-danger";
			  $scope.serverErrorMesg = "Oh snap! Lost contact to the server!";
		  });
	}
	
	$scope.setLabel = function(status)
  {
	  if(status.toLowerCase() =="ok"){
		return "label label-success";  
	  }else
	  {
		return "label label-danger";  
	  }
  }
	httpRequest();
	setWindowRefresh();
   
 
});

