package dashboard;

public class midtierStore {
	private String Environment;
	private String Category;
	private String Server_Host_Name;
	private String Server_Domain_Name;
	private String Status_IISHealth;
	private String Status_TomcatHealth;
	private String Mapped_ARSHostName;
	private String IISHealth_URL;
	private String TomcatHealth_URL;
	/**
	 * @param environment
	 * @param category
	 * @param server_Host_Name
	 * @param server_Domain_Name
	 * @param status_IISHealth
	 * @param status_TomcatHealth
	 * @param mapped_ARSHostName
	 */
	public midtierStore(String environment,
						String category,
						String server_Host_Name,
						String server_Domain_Name,
						String status_IISHealth,
						String status_TomcatHealth,
						String mapped_ARSHostName,
						String iisHealth_URL,
						String tomcatHealth_URL) {
		Environment = environment;
		Category = category;
		Server_Host_Name = server_Host_Name;
		Server_Domain_Name = server_Domain_Name;
		Status_IISHealth = status_IISHealth;
		Status_TomcatHealth = status_TomcatHealth;
		Mapped_ARSHostName = mapped_ARSHostName;
		IISHealth_URL = iisHealth_URL;
		TomcatHealth_URL = tomcatHealth_URL;
	}
	/**
	 * @return the environment
	 */
	public String getEnvironment() {
		return Environment;
	}
	/**
	 * @param environment the environment to set
	 */
	public void setEnvironment(String environment) {
		Environment = environment;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return Category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		Category = category;
	}
	/**
	 * @return the server_Host_Name
	 */
	public String getServer_Host_Name() {
		return Server_Host_Name;
	}
	/**
	 * @param server_Host_Name the server_Host_Name to set
	 */
	public void setServer_Host_Name(String server_Host_Name) {
		Server_Host_Name = server_Host_Name;
	}
	/**
	 * @return the server_Domain_Name
	 */
	public String getServer_Domain_Name() {
		return Server_Domain_Name;
	}
	/**
	 * @param server_Domain_Name the server_Domain_Name to set
	 */
	public void setServer_Domain_Name(String server_Domain_Name) {
		Server_Domain_Name = server_Domain_Name;
	}
	/**
	 * @return the status
	 */
	public String getStatus_IISHealth() {
		return Status_IISHealth;
	}
	/**
	 * @param status_IISHealth the status to set
	 */
	public void setStatus_IISHealth(String status_IISHealth) {
		Status_IISHealth = status_IISHealth;
	}
	/**
	 * @return the status_TomcatHealth
	 */
	public String getStatus_TomcatHealth() {
		return Status_TomcatHealth;
	}
	/**
	 * @param status_TomcatHealth the status_TomcatHealth to set
	 */
	public void setStatus_TomcatHealth(String status_TomcatHealth) {
		Status_TomcatHealth = status_TomcatHealth;
	}
	/**
	 * @return the mapped_ARSHostName
	 */
	public String getMapped_ARSHostName() {
		return Mapped_ARSHostName;
	}
	/**
	 * @param mapped_ARSHostName the mapped_ARSHostName to set
	 */
	public void setMapped_ARSHostName(String mapped_ARSHostName) {
		Mapped_ARSHostName = mapped_ARSHostName;
	}
	
	/**
	 * @return the iISHealth_URL
	 */
	public String getIISHealth_URL() {
		return IISHealth_URL;
	}
	/**
	 * @param iISHealth_URL the iISHealth_URL to set
	 */
	public void setIISHealth_URL(String iISHealth_URL) {
		IISHealth_URL = iISHealth_URL;
	}
	/**
	 * @return the tomcatHealth_URL
	 */
	public String getTomcatHealth_URL() {
		return TomcatHealth_URL;
	}
	/**
	 * @param tomcatHealth_URL the tomcatHealth_URL to set
	 */
	public void setTomcatHealth_URL(String tomcatHealth_URL) {
		TomcatHealth_URL = tomcatHealth_URL;
	}
	
}
