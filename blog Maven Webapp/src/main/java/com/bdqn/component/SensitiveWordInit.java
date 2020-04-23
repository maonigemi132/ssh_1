package com.bdqn.component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 初始化敏感词库，将敏感词加入到HashMap中，构建DFA算法模型
 * @author jinge
 *  2018年05月01日21:13:27
 */
public class SensitiveWordInit {
	private String ENCODING = "UTF-8";    //字符编码
	@SuppressWarnings("rawtypes")
	public HashMap sensitiveWordMap;
	
	public SensitiveWordInit(){
		super();
	}
	private   String sensitiveFilePath;  //词库文件路径


	public String getSensitiveFilePath() {
		return sensitiveFilePath;
	}

	public void setSensitiveFilePath(String sensitiveFilePath) {
		this.sensitiveFilePath = sensitiveFilePath;
	}

	/**
	 * @author jinge 
	 * @date 2014年4月20日 下午2:28:32
	 * @version 1.0
	 */
	@SuppressWarnings("rawtypes")
	public Map initKeyWord(){
		try {
			//读取敏感词库
			Set<String> keyWordSet = readSensitiveWordFile();
			//将敏感词库加入到HashMap中
			addSensitiveWordToHashMap(keyWordSet);
			//spring获取application，然后application.setAttribute("sensitiveWordMap",sensitiveWordMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sensitiveWordMap;
	}

	/**
	 * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br>
	 * 中 = {
	 *      isEnd = 0
	 *      国 = {<br>
	 *      	 isEnd = 1
	 *           人 = {isEnd = 0
	 *                民 = {isEnd = 1}
	 *                }
	 *           男  = {
	 *           	   isEnd = 0
	 *           		人 = {
	 *           			 isEnd = 1
	 *           			}
	 *           	}
	 *           }
	 *      }
	 *  五 = {
	 *      isEnd = 0
	 *      星 = {
	 *      	isEnd = 0
	 *      	红 = {
	 *              isEnd = 0
	 *              旗 = {
	 *                   isEnd = 1
	 *                  }
	 *              }
	 *      	}
	 *      }
	 * @author jinge 
	 * @date 2014年4月20日 下午3:04:20
	 * @param keyWordSet  敏感词库
	 * @version 1.0
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
		sensitiveWordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
		String key = null;  
		Map nowMap = null;
		Map<String, String> newWorMap = null;
		//迭代keyWordSet
		Iterator<String> iterator = keyWordSet.iterator();
		while(iterator.hasNext()){
			key = iterator.next();    //关键字
			nowMap = sensitiveWordMap;
			for(int i = 0 ; i < key.length() ; i++){
				char keyChar = key.charAt(i);       //转换成char型
				Object wordMap = nowMap.get(keyChar);       //获取
				
				if(wordMap != null){        //如果存在该key，直接赋值
					nowMap = (Map) wordMap;
				}
				else{     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
					newWorMap = new HashMap<String,String>();
					newWorMap.put("isEnd", "0");     //不是最后一个
					nowMap.put(keyChar, newWorMap);
					nowMap = newWorMap;
				}
				
				if(i == key.length() - 1){
					nowMap.put("isEnd", "1");    //最后一个
				}
			}
		}
	}

	/**
	 * 读取敏感词库中的内容，将内容添加到set集合中
	 * @author jinge 
	 * @date 2014年4月20日 下午2:31:18
	 * @return
	 * @version 1.0
	 * @throws Exception 
	 */
	@SuppressWarnings("resource")
	private Set<String> readSensitiveWordFile() throws Exception{
		Set<String> set = null;
		String path2 = Thread.currentThread().getContextClassLoader().getResource(File.separator).getPath();  
		if(!getSensitiveFilePath().toLowerCase().startsWith("classpath:")){
			throw  new IOException("无法识别文件路径："+getSensitiveFilePath());
		}
		path2=path2+getSensitiveFilePath().substring("classpath:".length());
		File   tempFile=new File(path2);
		if(!tempFile.exists()){
			throw  new IOException(tempFile.getAbsolutePath()+"  路径不存在");
		}
		File  wordFiles[]=null;
		if(tempFile.isDirectory()){
			wordFiles=tempFile.listFiles();
			
		}else{
			wordFiles=new File[]{tempFile};
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		if(wordFiles==null||wordFiles.length==0){
			throw  new IOException(tempFile.getAbsolutePath()+"  路径中没有文件");
		}else{
			set = new HashSet<String>();
			   for (File file : wordFiles) {
					InputStreamReader read = new InputStreamReader(new FileInputStream(file),ENCODING);
					try {
						if(file.isFile() && file.exists()){      //文件流是否存在
							BufferedReader bufferedReader = new BufferedReader(read);
							String txt = null;
							while((txt = bufferedReader.readLine()) != null){    //读取文件，将文件内容放入到set中
								if(txt.contains("|")){
									String newstr=txt.replace("|", "-");
									String words[]=newstr.split("-");
									if(words!=null)
									for (String s : words) {
										Matcher matcher = pattern.matcher(s);
									    if(!matcher.matches()){
									    		set.add(s);
									    }
									}
									continue;
								}
								Matcher matcher = pattern.matcher(txt);
							    if(!matcher.matches()){
									set.add(txt);
							    }
						    }
						}
						else{         //不存在抛出异常信息
							throw new Exception("敏感词库文件不存在");
						}
					} catch (Exception e) {
						throw e;
					}finally{
						read.close();     //关闭文件流
					}
			   }
		}
		return set;
	}
}
