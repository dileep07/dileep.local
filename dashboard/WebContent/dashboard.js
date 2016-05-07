
/*
 * 
 * [{"Environment":"PROD",
 * "Category":"MT-1",
 * "Status_TomcatHealth":"OK",
 * "Status_IISHealth":"OK",
 * "Server_Host_Name":"aude1ii000nabwi",
 * "Server_Domain_Name":"basdev.aurdev.national.com.au"}]
 * 
 * */

function clock()
{
	show("midtiermonitor","/dashboard/midtier?operation=display");
}
function createXMLHttpRequest() {
	if (typeof XMLHttpRequest == "undefined")
		XMLHttpRequest = function() {
			try {
				return new ActiveXObject("Msxml2.XMLHTTP.6.0")
			} catch (e) {
			}
			try {
				return new ActiveXObject("Msxml2.XMLHTTP.3.0")
			} catch (e) {
			}
			try {
				return new ActiveXObject("Msxml2.XMLHTTP")
			} catch (e) {
			}
			try {
				return new ActiveXObject("Microsoft.XMLHTTP")
			} catch (e) {
			}
			throw new Error("This browser does not support XMLHttpRequest.")
		};
	return new XMLHttpRequest();
}

var serverArrayList, i, j;
function show(caller, url) 
{
	var AJAX = createXMLHttpRequest();
	if (caller === "midtiermonitor") 
	{
		AJAX.onreadystatechange = function()
		{
			if (AJAX.readyState == 4 && AJAX.status == 200)
			{
				var jsonService = JSON.parse(AJAX.responseText);
				renderMidtierMonitors(jsonService);
				midtier_service(jsonService);
			} else if (AJAX.readyState == 4 && AJAX.status != 200)
			{
				document.getElementById("error_Remedy").innerHTML = document
					.getElementById("error_AO").innerHTML = document
					.getElementById("error_BPPM").innerHTML = "Something went wrong...Error:"
					+ AJAX.status;
			}
		};
		AJAX.open("GET", url,true);
	}
	
	AJAX.send("");
}

function midtier_service(jsonService)
{
var counter;
var service_status=0;
var intermittent = "NO";
	for(counter=0;counter<jsonService.length;counter++)
	{
		if(jsonService[counter].Status_IISHealth == "<status>ok</status>")
		{
			if(jsonService[counter].Status_TomcatHealth=="OK"){service_status=service_status+1;}
			else{intermittent = "YES";}
		}	
		
	
	}
var docObject_Service = document.getElementById("Midtier_Service");

var divpanel = document.createElement('div');
if(service_status > 0)
{
	if(intermittent == "NO")
	{
			divpanel.setAttribute("class","jumbotron alert alert-success");
			if(service_status == 1){
				divpanel.innerHTML= "<h3 align=\"center\">The Service is UP on "+service_status+" node.. !!</h3>";
			}
			else{
				divpanel.innerHTML= "<h3 align=\"center\">The Service is UP on "+service_status+" nodes.. !!</h3>";
			}
			
	}else{
			divpanel.setAttribute("class","jumbotron alert alert-warning");
			divpanel.innerHTML= "<h3 align=\"center\">The Service is UP with Issues.. !!</h3>";
	}
}
else
{
	divpanel.setAttribute("class","jumbotron alert alert-danger");
	divpanel.innerHTML= "<h3 align=\"center\">The Service is NOT Running.. !!</h3>";
}
docObject_Service.innerHTML = "";
docObject_Service.appendChild(divpanel);

	
}
function renderMidtierMonitors(jsonService)
{
	var counter;
	
	for(counter=0;counter<jsonService.length;counter++)
	{
		var temp_Environment = jsonService[counter].Environment;
		var temp_Category = jsonService[counter].Category;
		var temp_Status_TomcatHealth = jsonService[counter].Status_TomcatHealth;
		var temp_Status_IISHealth = jsonService[counter].Status_IISHealth;
		var temp_Server_Host_Name = jsonService[counter].Server_Host_Name;
		var temp_Server_Domain_Name = jsonService[counter].Server_Domain_Name;
		var docObject_Env_IIS_server = document.getElementById(temp_Environment+ "_IIS_"+temp_Server_Host_Name);
		var docObject_Env_Tomcat_server = document.getElementById(temp_Environment+ "_Tomcat_"+temp_Server_Host_Name);
		if(docObject_Env_IIS_server)
		{
			docObject_Env_IIS_server.innerHTML = temp_Server_Host_Name+" - "+ temp_Status_IISHealth;
			if(temp_Status_IISHealth == "<status>ok</status>"){docObject_Env_IIS_server.setAttribute("class" ,"label label-success");}
			else{docObject_Env_IIS_server.setAttribute("class" ,"label label-danger");}
		}else
		{
			var listitem = document.createElement('li');
			var spanpanel = document.createElement('span');
			spanpanel.id = temp_Environment+"_IIS_"+temp_Server_Host_Name;
			spanpanel.setAttribute("align", "center");
			spanpanel.innerHTML = temp_Server_Host_Name+ " - "+temp_Status_IISHealth;
			if(temp_Status_IISHealth == "<status>ok</status>"){spanpanel.setAttribute("class" ,"label label-success");}
			else{spanpanel.setAttribute("class" ,"label label-danger");}
			listitem.appendChild(spanpanel);
			document.getElementById(temp_Environment+"_IIS").appendChild(listitem);
		}
		if(docObject_Env_Tomcat_server)
		{
			docObject_Env_Tomcat_server.innerHTML = temp_Server_Host_Name+" - "+ temp_Status_TomcatHealth;
			if(temp_Status_TomcatHealth == "OK"){docObject_Env_Tomcat_server.setAttribute("class" ,"label label-success");}
			else{docObject_Env_Tomcat_server.setAttribute("class" ,"label label-danger");}
		}else
		{
			var listitem = document.createElement('li');
			var spanpanel = document.createElement('span');
			spanpanel.id = temp_Environment+"_Tomcat_"+temp_Server_Host_Name;
			spanpanel.setAttribute("align", "center");
			spanpanel.innerHTML = temp_Server_Host_Name+ " - "+temp_Status_TomcatHealth;
			if(temp_Status_TomcatHealth == "OK"){spanpanel.setAttribute("class" ,"label label-success");}
			else{spanpanel.setAttribute("class" ,"label label-danger");}
			listitem.appendChild(spanpanel);
			document.getElementById(temp_Environment+"_Tomcat").appendChild(listitem);
		}
		
		
	}
	

}


	function renderDashboardURL(serverJSON) {
		var serverlistul = document
				.getElementById(serverJSON[0].serverActualName
						+ serverJSON[0].servername + "list");
		var serverlist, urllist;
		serverlist = "<li><a>" + serverJSON[0].serverActualName
				+ "&nbsp</a></li>";
		urllist = "<li><a>" + serverJSON[0].url + "&nbsp</a></li>";
		serverlistul.innerHTML = serverlist + urllist;

	}

	function renderDashboardServer(serverJSON) {

		var serverlist = document.getElementById(serverJSON[0].servername
				+ "-list");
		var pinglist;
		var servicelist;
		var disklist;
		var Environment = serverJSON[0].Environment.replace(/ /g, "");
		var ServerNickName = serverJSON[0].ServerNickName.replace(/ /g, "");
		var ServerName = serverJSON[0].servername.replace(/ /g, "");

		if (serverJSON[0].ping === true) {
			pinglist = "<li><a>Ping&nbsp<span class=\"glyphicon glyphicon-thumbs-up\" aria-hidden=\"true\" style=\"color:green\"></span></a></li>";
		} else {
			pinglist = "<li><a>Ping&nbsp<span class=\"glyphicon glyphicon-thumbs-down\" aria-hidden=\"true\" style=\"color:red\"></span></a></li>";
		}
		if (serverJSON[0].servicestatus === true) {
			servicelist = "<li><a href=\"/ServiceMon/ServiceMonitor.html#"+Environment+"-"+ServerNickName+"\">Services&nbsp<span class=\"glyphicon glyphicon-thumbs-up\" aria-hidden=\"true\" style=\"color:green\"></span></a></li>";
		} else {
			servicelist = "<li><a href=\"/ServiceMon/ServiceMonitor.html#"+Environment+"-"+ServerNickName+"\">Services&nbsp<span class=\"glyphicon glyphicon-thumbs-down\" aria-hidden=\"true\" style=\"color:red\"></span></a></li>";
		}
		if (serverJSON[0].disk === true) {
			disklist = "<li><a href=\"/ServiceMon/PingMonitor.html#"+Environment+"-"+ServerNickName+"\">Disk&nbsp<span class=\"glyphicon glyphicon-thumbs-up\" aria-hidden=\"true\" style=\"color:green\"></span></a></li>";
		} else {
			disklist = "<li><a href=\"/ServiceMon/PingMonitor.html#"+Environment+"-"+ServerNickName+"\">Disk&nbsp<span class=\"glyphicon glyphicon-thumbs-down\" aria-hidden=\"true\" style=\"color:red\"></span></a></li>";
		}
		serverlist.innerHTML = "<li><a>" + serverJSON[0].servername
				+ "</a></li><li role=\"separator\" class=\"divider\"></li>"
				+ pinglist + servicelist + disklist;

	}

	
	function renderDashboard(dashboardJSON)
	{
		var counter1;
		
		for(counter1=0;counter1<dashboardJSON.length;counter1++)
		{
			var tempEnvironment = dashboardJSON[counter1].Environment.replace(/ /g,"");
			var tempStatus = dashboardJSON[counter1].Status;
			var tempServerNickName = dashboardJSON[counter1].ServerNickName.replace(/ /g,"");
			var tempServerName = dashboardJSON[counter1].ServerName.replace(/ /g,"");
			var button = document.getElementById(tempEnvironment+"-"+tempServerNickName+"-"+tempServerName);
				
			if(button)
			{
				if (tempStatus === true) {
					button.setAttribute("class",
							"btn btn-success btn-xs dropdown-toggle");
					button.innerHTML = "<span></p></span>";
				} else {
					button.setAttribute("class",
							"btn btn-danger btn-xs dropdown-toggle");
					button.innerHTML = "<span></p></span>";
				}	
			}
			else
			{
				var panelServerNickName = document.getElementById(tempEnvironment+"-"+tempServerNickName);
				if(panelServerNickName)
				{
					panelServerNickName.getElementsByClassName('panel-body')[0].innerHTML+="&nbsp"
					panelServerNickName.getElementsByClassName('panel-body')[0].appendChild(createServerButton(dashboardJSON[counter1]));
					
				} else {
					var panelEnvironment= document.getElementById(tempEnvironment);
					if(panelEnvironment)
					{
						var divpanel = document.createElement('div');
						divpanel.id = tempEnvironment+"-"+tempServerNickName;
						divpanel.setAttribute("class", "panel panel-info");
						divpanel.innerHTML = "<div class=\"panel-heading\">" + dashboardJSON[counter1].ServerNickName + "</div>";
						var temppanelbody = document.createElement('div');
						temppanelbody.setAttribute("class", "panel-body");
						temppanelbody.appendChild(createServerButton(dashboardJSON[counter1]));
						divpanel.appendChild(temppanelbody);
						panelEnvironment.appendChild(divpanel);
					}else{
						//donothingfornow
						
					}
					
					
				}
			}
		}
	}
	
	function createServerButton(dashboardJSON)
	{
		var tempEnvironment = dashboardJSON.Environment.replace(/ /g,"");
		var tempServerNickName = dashboardJSON.ServerNickName.replace(/ /g,"");
		var tempServerName = dashboardJSON.ServerName.replace(/ /g,"");
		var tempStatus = dashboardJSON.Status;
		var tempMonitor;
		var counter;
		for(counter=0;counter < environmentMonitorList.length ; counter++)
		{
			if(tempEnvironment===environmentMonitorList[counter][1]){
				tempMonitor = environmentMonitorList[counter][0];
			}
		}
		var div = document.createElement('div');
		div.setAttribute("class", "btn-group");
		var button = document.createElement('button');
		button.id = tempEnvironment +"-"+tempServerNickName +"-"+tempServerName;
		if (tempStatus === true) {
			button.setAttribute("class",
					"btn btn-success btn-xs dropdown-toggle");
			button.innerHTML = "<span></p></span>";
		} else {
			button.setAttribute("class",
					"btn btn-danger btn-xs dropdown-toggle");
			button.innerHTML = "<span></p></span>";
		}
		button.setAttribute("data-toggle", "dropdown");
		button.setAttribute("aria-haspopup", "true");
		button.setAttribute("aria-expanded", "false");
		var url =window.location.protocol+"//"+tempMonitor+"/ServiceMon/dashboardService?operation=server&environment="
						+ tempEnvironment
						+ "&servernickname="
						+ tempServerNickName
						+ "&servername=" + tempServerName;
		button.setAttribute("onclick","show(\"server\",\""+url+"\");");
		div.appendChild(button);
		div.innerHTML = div.innerHTML + "<ul id=\""+tempServerName+"-list\" class=\"dropdown-menu\"></ul>";
		
		return div;
	}
	
