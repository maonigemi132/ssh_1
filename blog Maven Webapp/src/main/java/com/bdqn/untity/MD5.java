package com.bdqn.untity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
 
	/**
	 * 对文件全文生成MD5摘要
	 * 
	 * @param file要加密的文件
	 * @return MD5摘要�?
	 */
	static char hexdigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	public static String md5(byte[] bytes) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(bytes);
			
			byte[] b = md.digest();
			
			return byteToHexString(b);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public static String getMD5(File file) {

		FileInputStream fis = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			fis = new FileInputStream(file);
			byte[] buffer = new byte[2048];
			int length = -1;
			while ((length = fis.read(buffer)) != -1) {
				md.update(buffer, 0, length);
			}

			// 32位加�?
			byte[] b = md.digest();
			return byteToHexString(b);

			// 16位加�?
			// return buf.toString().substring(8, 24);

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 把byte[]数组转换成十六进制字符串表示形式
	 * 
	 * @param tmp
	 *            要转换的byte[]
	 * @return 十六进制字符串表示形�?
	 */

	private static String byteToHexString(byte[] tmp) {
		String s;
		// 用字节表示就�? 16 个字�?
		char str[] = new char[16 * 2]; // 每个字节�? 16 进制表示的话，使用两个字符，
		// �?以表示成 16 进制�?�? 32 个字�?
		int k = 0; // 表示转换结果中对应的字符位置
		for (int i = 0; i < 16; i++) { // 从第�?个字节开始，�? MD5 的每�?个字�?
			// 转换�? 16 进制字符的转�?
			byte byte0 = tmp[i]; // 取第 i 个字�?
			str[k++] = hexdigits[byte0 >>> 4 & 0xf]; // 取字节中�? 4 位的数字转换,
			// >>> 为�?�辑右移，将符号位一起右�?
			str[k++] = hexdigits[byte0 & 0xf]; // 取字节中�? 4 位的数字转换
		}
		s = new String(str); // 换后的结果转换为字符�?
		return s;
	}

}
