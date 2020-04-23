package com.bdqn.untity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

public class KaptchaUtil {

	static Producer   captchaProducer;
	
	static{
		if(captchaProducer==null){
			captchaProducer=defaultKaptcha();
		}
	}
	
	public static DefaultKaptcha  defaultKaptcha(){
		try {
			DefaultKaptcha  defaultKaptcha =new DefaultKaptcha();
			Properties  properties=new Properties();
			properties.load(KaptchaUtil.class.getClassLoader().getResourceAsStream("kaptcha.properties"));
			Config  config=new Config(properties);
			defaultKaptcha.setConfig(config);
			return defaultKaptcha;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  null;
	}
	
	
	/**
	 * 响应验证码图�?
	 * @param request
	 * @param response
	 */
	public static  void  responseImg(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        // 生成验证�?
        String capText = captchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        // 向客户端写出
        ServletOutputStream out = null;
        try {
            BufferedImage bi = captchaProducer.createImage(capText);
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
}
