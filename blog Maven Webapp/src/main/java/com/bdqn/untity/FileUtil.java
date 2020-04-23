package com.bdqn.untity;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;



public class FileUtil {

	/**
	 * 获得当前请求的根URL
	 * @param request
	 * @return
	 */
	public static String getHttpRootUrl(HttpServletRequest request){
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		return basePath;
		
	}
	
	/**
	 * 保存文件
	 * @param request
	 * @param file
	 * @param savePath
	 * @param mappingUrl
	 * @return
	 */
	public static FileResult  saveFileToPath(HttpServletRequest request,CommonsMultipartFile file, String savePath,String mappingUrl){
		return saveFileToPath(request, file, savePath, mappingUrl,null);
	}
	
	
	
	/**
	 * 保存文件
	 * @param request
	 * @param file
	 * @param savePath
	 * @param mappingUrl 指定新文件
	 * @return
	 */
	public static FileResult  saveFileToPath(HttpServletRequest request,CommonsMultipartFile file, String savePath,String mappingUrl,String newFileName){
		File savFile=null;
		boolean falg=false;
		try {
			if (file!=null&&!file.isEmpty()) {
				String originalName=file.getOriginalFilename();
				String newName=(StringUtils.isNotBlank(newFileName)?newFileName:System.currentTimeMillis())+originalName.substring(originalName.lastIndexOf("."));
				
		
				savFile=getRealFile(request, savePath, newName);
				file.transferTo(savFile);	
				falg=true;
				
				String httpUrl=getHttpRootUrl(request);
				if (!httpUrl.endsWith("/")) httpUrl+="/";
				
				//去除/
				if (mappingUrl.startsWith("/")) mappingUrl=mappingUrl.substring(1);
				
				httpUrl+=mappingUrl;
				if (!httpUrl.endsWith("/")) httpUrl+="/";
				httpUrl+=newName;
				
				return new FileResult(newName, savFile, httpUrl, falg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (savFile!=null&&savFile.exists()) savFile.delete();			
		}
		return null;
	}
	/**
	 * 获得项目根路径
	 * @param request
	 * @return
	 */
	public static String getRootPath(HttpServletRequest request){		
		return getRootPath(request.getSession());
	}	
	public static String getRootPath(HttpSession session){		
		//当前项目部署所在位置根路径
		String rootPath=session.getServletContext().getRealPath("");
		if (StringUtils.isBlank(rootPath)) {
			rootPath=session.getServletContext().getRealPath(File.separator);
		}
		return rootPath;
	}
	
	
	/**
	 * 获取文件对象
	 * @param request
	 * @param savePath
	 * @param fileName
	 * @return
	 */
	public static File getRealFile(HttpServletRequest request,String savePath,String fileName){
		return getRealFile(request.getSession(),savePath,fileName);
	}
	
	public static File getRealFile(HttpSession session,String savePath,String fileName){
		//当前项目部署所在位置根路径
		String rootPath=getRootPath(session);
		//去除/
		if (savePath.startsWith(File.separator)) savePath=savePath.substring(1);
		//保存文件的绝对地址
		savePath=rootPath+savePath;
		if (!savePath.endsWith(File.separator)) savePath+=File.separator;
		return new File(savePath+fileName);
	}
	
	
	/**
	 * 删除文件
	 * @param request
	 * @param fileName
	 * @param savePath
	 * @return
	 */
	public static boolean deleteFile(HttpServletRequest request,String fileName, String savePath){
		return deleteFile(request.getSession(),fileName,savePath);
	}
	
	
	
	public static boolean deleteFile(HttpSession session,String fileName, String savePath){
		try {
			//当前项目部署所在位置根路径
			String rootPath=getRootPath(session);
			if (fileName.startsWith("http://")||fileName.startsWith("https://")) {
				fileName=fileName.substring(fileName.lastIndexOf("/")+1);
			}
			File file=getRealFile(session, savePath, fileName);
			if(file.exists()) return file.delete();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
