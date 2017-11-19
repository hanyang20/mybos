package com.itheima.bos.interceptor;

import org.apache.struts2.ServletActionContext;

import com.itheima.bos.bean.User;
import com.itheima.bos.utils.BOSUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**  
 * ClassName:BOSLoginInterceptor <br/>  
 * Function:  <br/>  
 * Date:     Oct 28, 2017 11:17:49 AM <br/>       
 */
public class BOSLoginInterceptor extends MethodFilterInterceptor{

    @Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {
          User user = BOSUtils.getUser();
         if(user == null){
             return "to_login";
         }
        return invocation.invoke();
    }

}
  
