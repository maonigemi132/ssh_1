package com.bdqn.untity;


import org.codehaus.jackson.map.annotate.JsonDeserialize;

/**
 * 解决方法返回多种结果
 * 模型类作为返回�??
 * @author Mr_Jin
 *
 */
public class Result<T> {
	
	private  boolean  rs=false; //标志执行成功还是失败  默认失败
	private  T  data; // 存储带回数据 
	private  String  content; //带回的文字信息内�?
	private   ResultType  resultType; //类型结果
	
	private Result(){}
	private Result(ResultType  resultType,boolean rs, T data, String content) {
		super();
		this.resultType=resultType;
		this.rs = rs;
		this.data = data;
		this.content = content;
	}

	/**
	 * 快�?�创建成功结�?
	 * @param data
	 * @param content
	 * @return
	 */
	public  static  <T> Result<T>     createSuccess (T data, String content) {
		return    new  Result(ResultType.SUCCESS,true,data,content);
	}
	/**
	 * 快�?�创建成功结�?
	 * @param data
	 * @param content
	 * @return
	 */
	public  static  <T> Result<T>     createSuccess (T data) {
		return    new  Result(ResultType.SUCCESS,true,data,null);
	}
	
	
	/**
	 * 快�?�创建成功结�?
	 * @param data
	 * @param content
	 * @return
	 */
	public  static  <T> Result<T>     createSuccess (String content) {
		return    new  Result(ResultType.SUCCESS,true,null,content);
	}
	
	/**
	 * 快�?�创建成功结�?
	 * @param data
	 * @param content
	 * @return
	 */
	public  static  <T> Result<T>     createSuccess () {
		return    new  Result(ResultType.SUCCESS,true,null,null);
	}
	
	
	
	/**
	 * 快�?�创建失败结�?
	 * @param data
	 * @param content
	 * @return
	 */
	public  static  <T> Result<T>     createError (T data, String content) {
		return    new  Result(ResultType.ERROR,false,data,content);
	}
	/**
	 * 快�?�创建失败结�?
	 * @param data
	 * @param content
	 * @return
	 */
	public  static  <T> Result<T>     createError (T data) {
		return    new  Result(ResultType.ERROR,false,data,null);
	}
	
	
	/**
	 * 快�?�创建失败结�?
	 * @param data
	 * @param content
	 * @return
	 */
	public  static  <T> Result<T>     createError ( String content) {
		return    new  Result(ResultType.ERROR,false,null,content);
	}
	
	/**
	 * 快�?�创建失败结�?
	 * @param data
	 * @param content
	 * @return
	 */
	public  static  <T> Result<T>     createError () {
		return    new  Result(ResultType.ERROR,false,null,null);
	}
	
	
	
	/**
	 * 自定义成功的结果类型
	 * @param data
	 * @param content
	 * @return
	 */
	public  static  <T> Result<T>     createTrueResult (ResultType resultType,T data, String content) {
		return    new  Result(resultType,true,data,content);
	}
	
	/**
	 * 自定义成功的结果类型
	 * @param data
	 * @param content
	 * @return
	 */
	public  static  <T> Result<T>     createTrueResult (ResultType resultType,String content) {
		return    new  Result(resultType,true,null,content);
	}
	
	/**
	 * 自定义成功的结果类型
	 * @param data
	 * @param content
	 * @return
	 */
	public  static  <T> Result<T>     createTrueResult (ResultType resultType,T data) {
		return    new  Result(resultType,true,data,null);
	}
	
	
	/**
	 * 自定义成功的结果类型
	 * @param data
	 * @param content
	 * @return
	 */
	public  static  <T> Result<T>     createTrueResult (ResultType resultType) {
		return    new  Result(resultType,true,null,null);
	}
	
	/**
	 * 自定义失败的结果类型
	 * @param data
	 * @param content
	 * @return
	 */
	public  static  <T> Result<T>     createFalseResult (ResultType resultType,T data, String content) {
		return    new  Result(resultType,false,data,content);
	}
	
	
	/**
	 * 自定义失败的结果类型
	 * @param data
	 * @param content
	 * @return
	 */
	public  static  <T> Result<T>     createFalseResult (ResultType resultType,T data) {
		return    new  Result(resultType,false,data,null);
	}
	
	
	/**
	 * 自定义失败的结果类型
	 * @param data
	 * @param content
	 * @return
	 */
	public  static  <T> Result<T>     createFalseResult (ResultType resultType, String content) {
		return    new  Result(resultType,false,null,content);
	}
	
	/**
	 * 自定义失败的结果类型
	 * @param data
	 * @param content
	 * @return
	 */
	public  static  <T> Result<T>     createFalseResult (ResultType resultType) {
		return    new  Result(resultType,false,null,null);
	}
	
	

	/**
	 * 判断操作是成功还是失�?
	 * @return  true  成功  false 失败
	 */
	public boolean isSuccess() {
		return rs;
	}
	
	/**
	 * 获得带回的数�?
	 * @return
	 */
	public T getData() {
		return data;
	}
	/**
	 * 设置带回的数�?
	 * @param data
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * 获取文字内容
	 * @return
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置文字内容
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取结果类型
	 * @return
	 */
	public ResultType getResultType() {
		return resultType;
	}
	
	/**
	 * 设置结果类型
	 * @param resultType
	 */
	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}
	
	/**
	 * 获取结果类型的代�?
	 * @return
	 */
	//把resultType 枚举转换成想要的单词和代�?
	public String getCode() {
		if(getResultType()!=null){
			return  getResultType().getValue();
		}
		return null;
	}
	
	/**
	 * 获得 getResultType().tostring()字符�?
	 * @return
	 */
	@JsonDeserialize
	public  String getResultStr(){
		return   getResultType().toString();
	}
	
	
}
