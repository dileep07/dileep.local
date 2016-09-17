package dashboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class midtier extends HttpServlet implements Runnable {
	private static final long serialVersionUID = 1L;
	private static String Requested_Operation;
	public static ArrayList<midtierStore> midtier_Array_List = new ArrayList<midtierStore>();
	Thread th = new Thread(this);
     /**
     * @see HttpServlet#HttpServlet()
     */
    public midtier() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		Requested_Operation = request.getParameter("operation");
		if(Requested_Operation.equalsIgnoreCase("display"))
		{
			JSONArray midtier_List_JSONArray = new JSONArray();
			for(int i=0;i<midtier_Array_List.size();i++)
			{
				JSONObject midtier_List_JSONObject = new JSONObject();
				midtier_List_JSONObject.put("Environment",midtier_Array_List.get(i).getEnvironment());
				midtier_List_JSONObject.put("Category",midtier_Array_List.get(i).getCategory());
				midtier_List_JSONObject.put("Server_Host_Name",midtier_Array_List.get(i).getServer_Host_Name());
				midtier_List_JSONObject.put("Server_Domain_Name",midtier_Array_List.get(i).getServer_Domain_Name());
				midtier_List_JSONObject.put("Status_IISHealth",midtier_Array_List.get(i).getStatus_IISHealth());
				midtier_List_JSONObject.put("Status_TomcatHealth",midtier_Array_List.get(i).getStatus_TomcatHealth());
				midtier_List_JSONObject.put("Mapped_ARSHostName", midtier_Array_List.get(i).getMapped_ARSHostName());
				midtier_List_JSONArray.add(midtier_List_JSONObject);
			}
		out.println(midtier_List_JSONArray);
		}
		
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	public void init(ServletConfig config) throws ServletException {
		
		LoadServer(readMainConfig.Midtier_List_Path);
		th.start();
	}

	private void LoadServer(String path) {
		Path file = Paths.get(path);
		System.out.println(file)
;		try {
			BufferedReader reader = Files.newBufferedReader(file,StandardCharsets.UTF_8);
			String line;
			while ((line = reader.readLine()) != null) {
				if(line.trim().charAt(0)!="#".charAt(0)){
				String result[] = line.split(",");
				/*		String environment,
						String category,
						String server_Host_Name,
						String server_Domain_Name,
						String status_IISHealth,
						String status_TomcatHealth,
						String mapped_ARSHostName,
						String iisHealth_URL,
						String tomcatHealth_URL
				*/
				midtier_Array_List.add(new midtierStore(result[0],result[1],result[2],result[3],"NA","NA","NA",result[4],result[5]));
				}
			}
			System.out.println("Added Objects: "+midtier_Array_List.size());
		} catch (UnknownHostException e) {
		} catch (NoSuchFileException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		midtierMonitors midtierMonitor = new midtierMonitors();
		for(;;)
		{
			
			int i = 0;
			String IISStatus_temp= "-1";
			String TomcatStatus_temp = "-1";
			for (i = 0; i < midtier_Array_List.size(); i++) {
				IISStatus_temp=midtierMonitor.loadBalancerHealthCheck(midtier_Array_List.get(i).getServer_Host_Name(),
						midtier_Array_List.get(i).getServer_Domain_Name(),midtier_Array_List.get(i).getIISHealth_URL());
				TomcatStatus_temp = midtierMonitor.TomcatHealthCheck(midtier_Array_List.get(i).getServer_Host_Name(),
						midtier_Array_List.get(i).getServer_Domain_Name(),
						midtier_Array_List.get(i).getTomcatHealth_URL());
				if(IISStatus_temp.equals("-1"))
				{
					midtier_Array_List.get(i).setStatus_IISHealth("Not Recheable");
				}else
				{
					midtier_Array_List.get(i).setStatus_IISHealth(IISStatus_temp);
				}
				if(TomcatStatus_temp.equals("OK"))
				{
					midtier_Array_List.get(i).setStatus_TomcatHealth("OK");
				}else if (TomcatStatus_temp.equals("FAIL")){
					midtier_Array_List.get(i).setStatus_TomcatHealth("FAIL");
				}else
				{
					midtier_Array_List.get(i).setStatus_TomcatHealth(TomcatStatus_temp);
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
