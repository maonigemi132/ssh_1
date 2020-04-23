package com.bdqn.thread;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.bdqn.component.BlogConstants;
import com.bdqn.untity.FileUtil;

public class FileDeleteThread extends Thread{

	private  List<String> fileNameList;
	private HttpSession session;
	public FileDeleteThread(List<String> fileNameList,HttpSession session){
		this.fileNameList=fileNameList;
		this.session=session;
	}
	
	
	@Override
	public void run() {
		
		if(fileNameList!=null&&fileNameList.size()>0)
		for (String fileName : fileNameList) {
			System.out.println("-----------删除了"+fileName);
			FileUtil.deleteFile(session, fileName, BlogConstants.ARTICLE_SAVE_PATH);
		}
		
		super.run();
	}

	
}
