package com.itheima.bos.service;

import com.itheima.bos.bean.User;
import com.itheima.bos.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

/**  
 * ClassName:IUserService <br/>  
 * Function:  <br/>  
 * Date:     Oct 27, 2017 9:52:53 PM <br/>       
 */
public interface IUserService {
    /**
     * 用户登陆
     * login:. <br/>  
     *  
     * @param model
     * @return
     */
    public User login(User model);
    /**
     * 修改当前用户密码
     * editPassword:. <br/>  
     *  
     * @param id
     * @param password
     */
    public void editPassword(User model);



    //用户管理那块，需要关联角色
    void save(User model, List<String> roleIds);
    //分页查询
    PageBean<User> pageQuery(DetachedCriteria criteria, int page, int rows);
}
  
