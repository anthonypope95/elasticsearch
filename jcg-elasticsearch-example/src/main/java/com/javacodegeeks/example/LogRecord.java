package com.javacodegeeks.example;

import java.io.Serializable;

public class LogRecord  implements Serializable{
	
	
	private int port;
	private String protocol;
	private double ts;
	private String macaddress;
	private String repositoryname;
	private String entity;
	private String severityname;
	private String dns;
	private String ipaddress;
	private String solution;
	private String familyname;
	private String familyid;
	private String osname;
	private String pluginname;
	private String netbios;
	private String connectorname;
	private int severityid;
	private String description;
	private String synopsis;
	private int pluginid;
	private String plugin_output;
	private int repositoryid;
	private String host;
	private String type;
	private String path;
	private String timestamp;
	private int version;
    private static final long serialVersionUID = 1L;
    
    
    public LogRecord() {
		super();
	}

	/*public LogRecord(String ipaddress, String pluginname, String connectorname, String familyname,String description,String type, String familyid, String path, String protocol,String solution,String host, int repositoryid, String macaddress,String osname,String netbios,String timestamp, int pluginid,String plugin_output,String dns, String synopsis, String severityname, int version,   String repositoryname, int port, int severityid, String entity,double ts) {
		this.port = port;
		this.protocol = protocol;
		this.ts = ts;
		this.macaddress = macaddress;
		this.repositoryname = repositoryname;
		this.entity = entity;
		this.severityname = severityname;
		this.dns = dns;
		this.ipaddress = ipaddress;
		this.solution = solution;
		this.familyname = familyname;
		this.familyid = familyid;
		this.osname = osname;
		this.pluginname = pluginname;
		this.netbios = netbios;
		this.connectorname = connectorname;
		this.severityid = severityid;
		this.description = description;
		this.synopsis = synopsis;
		this.pluginid = pluginid;
		this.plugin_output = plugin_output;
		this.repositoryid = repositoryid;
		this.host = host;
		this.type = type;
		this.path = path;
		this.timestamp = timestamp;
		this.version = version;
	}
	*/
	public int getPort() {
		return port;
	}
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public double getTs() {
		return ts;
	}

	public void setTs(double ts) {
		this.ts = ts;
	}

	public String getMacAddress() {
		return macaddress;
	}

	public void setMacAddress(String macaddress) {
		this.macaddress = macaddress;
	}

	public String getRepositoryname() {
		return repositoryname;
	}

	public void setRepositoryname(String repositoryname) {
		this.repositoryname = repositoryname;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getSeverityname() {
		return severityname;
	}

	public void setSeverityname(String severityname) {
		this.severityname = severityname;
	}

	public String getDns() {
		return dns;
	}

	public void setDns(String dns) {
		this.dns = dns;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getFamilyname() {
		return familyname;
	}

	public void setFamilyname(String familyname) {
		this.familyname = familyname;
	}

	public String getFamilyid() {
		return familyid;
	}

	public void setFamilyid(String familyid) {
		this.familyid = familyid;
	}

	public String getOsname() {
		return osname;
	}

	public void setOsname(String osname) {
		this.osname = osname;
	}

	public String getPluginname() {
		return pluginname;
	}

	public void setPluginname(String pluginname) {
		this.pluginname = pluginname;
	}

	public String getNetbios() {
		return netbios;
	}

	public void setNetbios(String netbios) {
		this.netbios = netbios;
	}

	public String getConnectorname() {
		return connectorname;
	}

	public void setConnectorname(String connectorname) {
		this.connectorname = connectorname;
	}

	public int getSeverityid() {
		return severityid;
	}

	public void setSeverityid(int severityid) {
		this.severityid = severityid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public int getPluginid() {
		return pluginid;
	}

	public void setPluginid(int pluginid) {
		this.pluginid = pluginid;
	}

	public String getPlugin_output() {
		return plugin_output;
	}

	public void setPlugin_output(String plugin_output) {
		this.plugin_output = plugin_output;
	}

	public int getRepositoryid() {
		return repositoryid;
	}

	public void setRepositoryid(int repositoryid) {
		this.repositoryid = repositoryid;
	}
	
	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getPath() {
		return path;
	}



	public void setPath(String path) {
		this.path = path;
	}



	public String getTimestamp() {
		return timestamp;
	}



	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getMacaddress() {
		return macaddress;
	}
	
	
	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public String toString() {
		return "LogRecord [port=" + port + ", protocol=" + protocol + ", ts=" + ts + ", macaddress=" + macaddress
				+ ", repositoryname=" + repositoryname + ", entity=" + entity + ", severityname=" + severityname
				+ ", dns=" + dns + ", ipaddress=" + ipaddress + ", solution=" + solution + ", familyname=" + familyname
				+ ", familyid=" + familyid + ", osname=" + osname + ", pluginname=" + pluginname + ", netbios="
				+ netbios + ", connectorname=" + connectorname + ", severityid=" + severityid + ", description="
				+ description + ", synopsis=" + synopsis + ", pluginid=" + pluginid + ", plugin_output=" + plugin_output
				+ ", repositoryid=" + repositoryid + ", host=" + host + ", type=" + type + ", path=" + path
				+ ", timestamp=" + timestamp + ", version=" + version + "]";
	}

}
