package com.shiro;

import com.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class ShiroUtil {

    //加密算法
    public final static String hashAlgorithmName = "SHA-256";

//    public final static String hashAlgorithmName = "SHA-512";
    //循环次数
    public final static int hashIterations = 16;

    public static String sha256(String password, String salt) {
        return new SimpleHash(hashAlgorithmName, password, salt, hashIterations).toString();
    }

    //获取Shiro Session
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    //获取Shiro Subject
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    //获取Shiro中的真正主体
    public static User getUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    public static Integer getUserId() {
        return getUser().getId();
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    	public static void main(String[] args) {
		String password="123";
		//System.out.println(SaltUtil.getSalt());//3ed974b043399957bb2db035c2679e92
		System.out.println(ShiroUtil.sha256(password, "YzcmCZNvbXocrsz9dm8e"));
	}

}

