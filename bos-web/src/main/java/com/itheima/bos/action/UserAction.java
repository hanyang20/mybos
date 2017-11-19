package com.itheima.bos.action;

import java.io.IOException;
import java.util.List;

import com.itheima.bos.utils.PageBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.hql.internal.ast.tree.IntoClause;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.bean.User;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.crm.Customer;
import com.itheima.crm.ICustomerService;

/**
 * ClassName:UserAction <br/>
 * Function: <br/>
 * Date: Oct 27, 2017 7:29:41 PM <br/>
 */
@Controller("userAction")
@Scope(scopeName="prototype")
public class UserAction extends BaseAction<User> {
    private String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
   
    @Autowired
    private IUserService iUserService;
   //使用shiro框架完成，用户登录
    public String login() {
        String vcode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
        // 判断验证码是否正确
        if (StringUtils.isNotBlank(checkcode) && vcode.equals(checkcode)) {
            Subject subject= SecurityUtils.getSubject();
            AuthenticationToken taken=new UsernamePasswordToken(model.getUsername(),model.getPassword());
            try{
                subject.login(taken);

            }catch (UnknownAccountException e){
                this.addActionError("用户名错误！！");
                e.printStackTrace();
                return LOGIN;
            }catch (IncorrectCredentialsException e){
                this.addActionError("密码错误！！");
                e.printStackTrace();
                return LOGIN;
            }
            User user = (User) subject.getPrincipal();
            ServletActionContext.getRequest().getSession().setAttribute("user", user);
            return HOME;
        } else {
            this.addActionError("验证码错误！！");
            return LOGIN;
        }

    }
    //用户登录
    public String login_bak() {
        String vcode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
        // 判断验证码是否正确
        if (StringUtils.isNotBlank(checkcode) && vcode.equals(checkcode)) {
            // 判断用户是否登录
            User user = iUserService.login(model);
            if (user != null) {
                ServletActionContext.getRequest().getSession().setAttribute("user", user);
                return HOME;
            } else {
                this.addActionError("用户名或密码错误！！");
                return LOGIN;
            }
        } else {
            this.addActionError("验证码错误！！");
            return LOGIN;
        }

    }
    /**
     * 用户注销
     */
    public String exit() {
        
        ServletActionContext.getRequest().getSession().invalidate();//removeAttribute("user");
        return LOGIN;
    }
    /**
     * 修改密码
     * @throws IOException 
     */
    public String editPassword() throws IOException{
        String i="1";
        User user = BOSUtils.getUser();
        model.setId(user.getId());
        model.setUsername(user.getUsername());
        try {
            iUserService.editPassword(model);
        } catch (Exception e) {
              i="0";
            e.printStackTrace();  
        }
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        ServletActionContext.getResponse().getWriter().print(i);
        return NONE;
    }
    //添加用户，用户管理
    private List<String> roleIds;
    public  String save(){
        iUserService.save(model,roleIds);
        return "save_user";
    }
    //分页查询
    public String pageQuery(){
        PageBean<User> pageBean=iUserService.pageQuery(criteria,page,rows);
        java2Json(pageBean,new String[]{"totalPage","pageSize","currentPage","noticebills","roles"});
        return NONE;
    }
    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }
}
