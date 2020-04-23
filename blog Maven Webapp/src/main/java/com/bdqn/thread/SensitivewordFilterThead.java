package com.bdqn.thread;

import java.util.Set;
import java.util.UUID;

import javax.transaction.Transaction;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.bdqn.component.ArticleStatus;
import com.bdqn.component.SensitivewordFilter;
import com.bdqn.dao.ArticleCheckMapper;
import com.bdqn.dao.ArticleMapper;
import com.bdqn.entity.ArticleCheck;
import com.bdqn.entity.ArticleWithBLOBs;
import com.bdqn.untity.StatusType;

public class SensitivewordFilterThead extends Thread{

	private SensitivewordFilter sensitivewordFilter;//敏感词组件
	
	private ArticleWithBLOBs article;//被过滤文章对象
	
	private Integer originStatus;//原来文章状态
	private ArticleCheckMapper articleCheckMapper;
	private ArticleMapper articleMapper;
	
	private DataSourceTransactionManager transactionManager;
	@Override
	public void run() {
		
		try {
			System.out.println("-------正在读取词库3秒后执行任务");
			sensitivewordFilter.reloadInitKeyWord();
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("----------------------------------------任务开始");
		
		ArticleCheck ac= new ArticleCheck();
		ac.setUuid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
		ac.setArticleUuid(article.getUuid());
		//过滤标题
		Set<String> result=sensitivewordFilter.getSensitiveWord(article.getTitle(), 2);
		//过滤内容
		Set<String> result1=sensitivewordFilter.getSensitiveWord(article.getContent(), 2);
		//过滤摘要
		Set<String> result2=sensitivewordFilter.getSensitiveWord(article.getSummary(), 2);
		
		//获得事务对象
		DefaultTransactionDefinition transDefinition = new DefaultTransactionDefinition();    
		transDefinition.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus transStatus = transactionManager.getTransaction(transDefinition);
		try {
			ArticleWithBLOBs newArticle=new ArticleWithBLOBs();
			newArticle.setUuid(article.getUuid());
			int count=0;
			if ((result!=null&&result.size()>0)||(result1!=null&&result1.size()>0)||(result2!=null&&result2.size()>0)) {//未通过			
				newArticle.setStatus(ArticleStatus.NOT_PASS.getValue());
				count=articleMapper.updateByPrimaryKeySelective(newArticle);
				if(count>0){
					StringBuffer sb=new StringBuffer("[");
					if(result!=null&&result.size()>0){						
						for (String str : result) {
							sb.append(str+"、");
						}
						sb.append("]");
						ac.setEvent("【标题】包含以下字符:"+sb.toString());
					}else if (result1!=null&&result1.size()>0) {
						for (String str : result1) {
							sb.append(str+"、");
						}
						sb.append("]");
						ac.setEvent("【内容】包含以下字符:"+sb.toString());
					}else {
						for (String str : result2) {
							sb.append(str+"、");
						}
						sb.append("]");
						ac.setEvent("【摘要】包含以下字符:"+sb.toString());
					}
					ac.setStatus(ArticleStatus.NOT_PASS.getName());
				}
			}else {
				newArticle.setStatus(originStatus);
				ac.setStatus(ArticleStatus.PASS.getName());
				count=articleMapper.updateByPrimaryKeySelective(newArticle);				
			}
			if(count>0)
			if(articleCheckMapper.insertSelective(ac)>0){
				//提交事务
				transactionManager.commit(transStatus);
				return;
			}	
			transactionManager.rollback(transStatus);			
		} catch (Exception e) {
			e.printStackTrace();
			transactionManager.rollback(transStatus);
		}
	}
	public SensitivewordFilterThead(SensitivewordFilter sensitivewordFilter, ArticleWithBLOBs article,
			Integer originStatus, ArticleCheckMapper articleCheckMapper, ArticleMapper articleMapper,
			DataSourceTransactionManager transactionManager) {
		super();
		this.sensitivewordFilter = sensitivewordFilter;
		this.article = article;
		this.originStatus = originStatus;
		this.articleCheckMapper = articleCheckMapper;
		this.articleMapper = articleMapper;
		this.transactionManager = transactionManager;
	}
	
	


	
	
	

	
}
