package com.itheima.bos.utils;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.itheima.bos.bean.User;

/**  
 * ClassName:BOSUtils <br/>  
 * Function:  <br/>  
 * Date:     Oct 28, 2017 12:54:06 PM <br/>       
 */
public class BOSUtils {
public static HttpSession getSession(){
    return ServletActionContext.getRequest().getSession();
    
}
public static User getUser(){
    return (User) getSession().getAttribute("user");
    
}
}
  
