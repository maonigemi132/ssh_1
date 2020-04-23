package com.bdqn.Iistener;

import java.util.List;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.bdqn.thread.FileDeleteThread;

@WebListener
public class BlogSessionIistener implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("------创建会话"+arg0.getSession().getId());
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEv) {
		System.out.println("------创建销毁"+sessionEv.getSession().getId());
		List<String> list=(List<String>) sessionEv.getSession().getAttribute("tempArticleFname");
		if (list!=null&&list.size()>0) {
			FileDeleteThread thread=new FileDeleteThread(list, sessionEv.getSession());
			thread.start();
		}
		
		
	}

}
