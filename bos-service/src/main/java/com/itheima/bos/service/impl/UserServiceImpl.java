package com.itheima.bos.service.impl;

import com.itheima.bos.bean.Function;
import com.itheima.bos.bean.Role;
import com.itheima.bos.dao.IRoleDao;
import com.itheima.bos.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.bean.User;
import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.service.IUserService;

import java.util.List;

/**  
 * ClassName:UserServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Oct 27, 2017 9:54:47 PM <br/>       
 */
@Service
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IRoleDao roleDao;
    /**
     * 用户登陆
     * login:. <br/>  
     */
    @Override
    public User login(User model) {
        
        return userDao.getUserByNameAndPassword(model);
    }
    /**
     * 修改当前用户密码
     * editPassword:. <br/>  
     */
    @Override
    public void editPassword(User model) {

        userDao.saveOrUpdate(model);
        
    }
    //用户管理那块，需要关联角色，一个用户可以有多个角色
    @Override
    public void save(User model, List<String> roleIds) {
        //如果有md5的话，要先把密码取出来，用户md5加密，然后再设置到user里
        userDao.save(model);
        for (String id : roleIds) {
            Role role = roleDao.findById(id);
            //角色那边已经放弃了外键维护，所以要客户去关联角色
            model.getRoles().add(role);
        }
    }

    @Override
    public PageBean<User> pageQuery(DetachedCriteria criteria, int page, int rows) {

        PageBean<User> pageBean=new PageBean<User>();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);
        int total = userDao.getTotalSize(criteria);
        pageBean.setTotal(total);
        int totalPage=(int) Math.ceil(total*1.0/rows);
        pageBean.setTotalPage(totalPage);
        List<User> list=userDao.getPageList(criteria,page,rows);
        pageBean.setRows(list);
        return pageBean;
    }
}
  
