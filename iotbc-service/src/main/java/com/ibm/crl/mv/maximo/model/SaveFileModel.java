package com.ibm.crl.mv.maximo.model;

public class SaveFileModel {
	
	private String file_name;
	private byte[] file_binary;
	private String file_path;
	
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public byte[] getFile_binary() {
		return file_binary;
	}
	public void setFile_binary(byte[] file_binary) {
		this.file_binary = file_binary;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	

}
