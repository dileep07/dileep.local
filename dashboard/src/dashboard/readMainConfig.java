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

public class readMainConfig extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String Midtier_List_Path;
	public static String EnableLog = "No";
	public static int Web_Refresh_Interval = 11;
	public static int Monitor_Polling_Interval = 20;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public readMainConfig() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String Requested_Operation = request.getParameter("operation");
		if(Requested_Operation.equalsIgnoreCase("readconfig"))
		{
			JSONObject config_JSONObject = new JSONObject();
			config_JSONObject.put("Web_Refresh_Interval",Web_Refresh_Interval);
			out.println(config_JSONObject);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		String configFilePath = config.getServletContext().getInitParameter("config.properties");
		Midtier_List_Path = config.getServletContext().getInitParameter("Midtier-List");
		System.out.println(config.getServletContext().getRealPath(configFilePath));
		System.out.println(config.getServletContext().getRealPath(Midtier_List_Path));
		Midtier_List_Path = config.getServletContext().getRealPath(Midtier_List_Path);
		LoadServer(config.getServletContext().getRealPath(configFilePath));
	}

	private void LoadServer(String path) {
		// TODO Auto-generated method stub
		Path file = Paths.get(path);

		try {
			BufferedReader reader = Files.newBufferedReader(file,StandardCharsets.UTF_8);
			String line;
			while ((line = reader.readLine()) != null) {
			
				if(line.trim().charAt(0)!="#".charAt(0)){
				switch(line.substring(0,line.indexOf(":")).trim())
				{
				case "EnableLog":
					EnableLog = line.substring(line.indexOf(":")+1,line.length()).trim();
					break;
				case "Web-Refresh-Interval":
					Web_Refresh_Interval = Integer.parseInt(line.substring(line.indexOf(":")+1,line.length()).trim());
					break;
				case "Monitor-Polling-Interval":
					Monitor_Polling_Interval = Integer.parseInt(line.substring(line.indexOf(":")+1,line.length()).trim());
					break;
				}
			}
		}
			

		} catch (UnknownHostException e) {
		} catch (NoSuchFileException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
