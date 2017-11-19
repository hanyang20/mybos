package com.itheima.bos.service.realm;

import com.itheima.bos.bean.Function;
import com.itheima.bos.bean.User;
import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.dao.IUserDao;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BOSRealm extends AuthorizingRealm {
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IFunctionDao functionDao;
     //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        //获取当前用户对象
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Function> list=null;
        //根据当前用户对象，查询实际对应的权限
        if(user.getUsername().equals("ls")){
            DetachedCriteria criteria=DetachedCriteria.forClass(Function.class);
            //查找对应权限的集合，内置用户，拥有所有权限
            list=functionDao.findAll(criteria);
        }else{
            //部分权限
             list = functionDao.findFunctionListByUserId(user.getId());
        }
        for (Function function:list){
            info.addStringPermission(function.getCode());
        }
        return info;
    }
     //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("BOSRealm中的认证方法执行了。。。。。");
        UsernamePasswordToken mytoken = (UsernamePasswordToken) token;
        String username = mytoken.getUsername();
      //根据username查询用户
       User user = userDao.findUserByUserName(username);
       if(user==null){
           //用户名不存在
           return null;
       }
        AuthenticationInfo info=new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
        return info;
    }
}
