package dashboard;

public class midtierStore {
	private String Environment;
	private String Category;
	private String Server_Host_Name;
	private String Server_Domain_Name;
	private String Status_IISHealth;
	private String Status_TomcatHealth;
	
	/**
	 * @param environment
	 * @param category
	 * @param server_Host_Name
	 * @param server_Domain_Name
	 * @param status
	 */
	public midtierStore(String environment, String category,
			String server_Host_Name, String server_Domain_Name, String status_IISHealth,String status_TomcatHealth) {
		Environment = environment;
		Category = category;
		Server_Host_Name = server_Host_Name;
		Server_Domain_Name = server_Domain_Name;
		Status_IISHealth = status_IISHealth;
		Status_TomcatHealth = status_TomcatHealth;
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
	
}
