package dashboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class midtierMonitors {
	private String url_Defined;
	public String loadBalancerHealthCheck(String Server_Host_Name,String Server_Domain_Name)
	{	
		String return_Key="-1";
		url_Defined = "http://"+Server_Host_Name+"."+Server_Domain_Name+":8080/dashboard/ok.html";
		
		try {
			URL url_object = new URL(url_Defined);
			HttpURLConnection con = (HttpURLConnection) url_object.openConnection();
			con.setConnectTimeout(1000);
			con.setReadTimeout(1000);
			con.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				String temp = inputLine.trim();
				if(temp.matches("<status>ok</status>"))
				{
					
					return_Key = temp.substring(temp.indexOf(">")+1,temp.indexOf("/")-1);
				}
				else
				{
					
					return_Key = temp.substring(temp.indexOf(">")+1,temp.indexOf("/")-1);
				}
			}
			if(readMainConfig.EnableLog.equals("Yes"))
			{
				System.out.println("url_Defined:" + url_Defined);
				System.out.println("return_Key:" + return_Key);
			}
			in.close();
		}catch (SocketTimeoutException e){
			return_Key = "Not Reacheable";
			
		}catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return return_Key;
	}
	public String TomcatHealthCheck(String Server_Host_Name,String Server_Domain_Name)
	{	
		String return_Key="-1";
		url_Defined = "http://"+Server_Host_Name+"."+Server_Domain_Name+":8080/dashboard/200.html";
		try {
			URL url_object = new URL(url_Defined);
			HttpURLConnection con = (HttpURLConnection) url_object.openConnection();
			con.setConnectTimeout(1000);
			con.setReadTimeout(1000);
			con.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine_temp="",inputLine="";
			StringBuffer response = new StringBuffer();
			while ((inputLine_temp = in.readLine()) != null) {
				inputLine = inputLine + inputLine_temp;
			}
				if(inputLine.contains("200_OK"))
				{
					return_Key = "OK";
				}
				else
				{
					return_Key = "FAIL";
				}
				if(readMainConfig.EnableLog.equals("Yes"))
				{
					System.out.println("url_Defined:" + url_Defined);
					System.out.println("return_Key:" + return_Key);
				}
			in.close();
		}catch (SocketTimeoutException e){
			
			return_Key = "Not Reacheable";
		}
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return return_Key;
	}
}
