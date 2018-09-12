package com.ibm.crl.mv.maximo.model;

public class SRvsDocs {
	
	private String $class;
	private String file_name;
	private String record_id;
	private String file_href;
	private String hash;
	private Boolean vs;
	
	
	public String get$class() {
		return $class;
	}
	public void set$class(String $class) {
		this.$class = $class;
	}
	
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getRecord_id() {
		return record_id;
	}
	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}
	public String getFile_href() {
		return file_href;
	}
	public void setFile_href(String file_href) {
		this.file_href = file_href;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public Boolean getVs() {
		return vs;
	}
	public void setVs(Boolean vs) {
		this.vs = vs;
	}
	

}
