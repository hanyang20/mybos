package com.itheima.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.bean.User;
import com.itheima.bos.dao.IUserDao;

/**  
 * ClassName:UserDaoImpl <br/>  
 * Function:  <br/>  
 * Date:     Oct 27, 2017 7:24:54 PM <br/>       
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao{

    @Override
    public User getUserByNameAndPassword(User model) {
          String hql="from User where username = ? and password = ?";
      List<User> list = (List<User>) super.getHibernateTemplate().find(hql, model.getUsername(),model.getPassword());
      if(list != null && list.size()>0){
          return list.get(0);
      }
      
      return null;
    }

    @Override
    public User findUserByUserName(String username) {
        String hql="from User where username = ?";
        List<User> list = (List<User>) super.getHibernateTemplate().find(hql, username);
        if(list != null && list.size()>0){
            return list.get(0);
        }

        return null;
    }


}
  
