package com.ibm.crl.mv.maximo.model;

public class SaveFileModel {
	
	private String fileName;
	private byte[] fileBinary;
	private String filePath;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public byte[] getFileBinary() {
		return fileBinary;
	}
	public void setFileBinary(byte[] fileBinary) {
		this.fileBinary = fileBinary;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	

}
