package com.bdqn.untity;

import java.io.File;

/**
 * 文件上传的结果
 * @author RP
 *
 */
public class FileResult{
	private String fileName;
	private File file;
	private String httpUrl;
	private boolean flag;
	
	public FileResult(){}
	public FileResult(String fileName, File file, String httpUrl, boolean flag) {
		super();
		this.fileName = fileName;
		this.file = file;
		this.httpUrl = httpUrl;
		this.flag = flag;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getHttpUrl() {
		return httpUrl;
	}
	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	
}
