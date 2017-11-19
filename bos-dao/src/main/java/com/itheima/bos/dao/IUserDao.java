package com.itheima.bos.dao;

import com.itheima.bos.bean.User;

/**  
 * ClassName:UserDao <br/>  
 * Function:  <br/>  
 * Date:     Oct 27, 2017 7:22:53 PM <br/>       
 */
public interface IUserDao extends IBaseDao<User>{

   public User getUserByNameAndPassword(User model);

    public void saveOrUpdate(User model);

    User findUserByUserName(String username);
}
  
