package run.configurator;

class Mobile {
	
	private String name;
	private String os;
	private String version;
	
	
	@Override
	public String toString() {
		return name + ", " + os + " " + version;
	}
	

	//************************************************************************//
						//***-- GETTERs and SETTERs --***\\
	//************************************************************************//
		
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the os
	 */
	public String getOs() {
		return os;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param os the os to set
	 */
	public void setOs(String os) {
		this.os = os;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	//************************************************************************//
						//***-- Constructors --***\\
	//************************************************************************//
	public Mobile(String name, String os, String version) {
		this.name = name;
		this.os = os;
		this.version = version;
	}
}
