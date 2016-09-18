package dashboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class midtierMonitors implements Runnable{
	private String url_Defined;
	public String loadBalancerHealthCheck(String Server_Host_Name,String Server_Domain_Name,String IISHealth_URL)
	{	
		String return_Key="-1";
		url_Defined = IISHealth_URL;
		
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
	public String TomcatHealthCheck(String Server_Host_Name,String Server_Domain_Name,String TomcatHealthURL)
	{	
		String return_Key="-1";
		url_Defined = TomcatHealthURL;
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
	
	public void run(){
		midtierMonitors midtierMonitor = new midtierMonitors();
		for(;;)
		{
			
			int i = 0;
			String IISStatus_temp= "-1";
			String TomcatStatus_temp = "-1";
			for (i = 0; i < midtier.midtier_Array_List.size(); i++) {
				IISStatus_temp=midtierMonitor.loadBalancerHealthCheck(midtier.midtier_Array_List.get(i).getServer_Host_Name(),
						midtier.midtier_Array_List.get(i).getServer_Domain_Name(),midtier.midtier_Array_List.get(i).getIISHealth_URL());
				TomcatStatus_temp = midtierMonitor.TomcatHealthCheck(midtier.midtier_Array_List.get(i).getServer_Host_Name(),
						midtier.midtier_Array_List.get(i).getServer_Domain_Name(),
						midtier.midtier_Array_List.get(i).getTomcatHealth_URL());
				if(IISStatus_temp.equals("-1"))
				{
					midtier.midtier_Array_List.get(i).setStatus_IISHealth("Not Recheable");
				}else
				{
					midtier.midtier_Array_List.get(i).setStatus_IISHealth(IISStatus_temp);
				}
				if(TomcatStatus_temp.equals("OK"))
				{
					midtier.midtier_Array_List.get(i).setStatus_TomcatHealth("OK");
				}else if (TomcatStatus_temp.equals("FAIL")){
					midtier.midtier_Array_List.get(i).setStatus_TomcatHealth("FAIL");
				}else
				{
					midtier.midtier_Array_List.get(i).setStatus_TomcatHealth(TomcatStatus_temp);
				}
			}
			try {
				Thread.sleep(readMainConfig.Monitor_Polling_Interval * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
