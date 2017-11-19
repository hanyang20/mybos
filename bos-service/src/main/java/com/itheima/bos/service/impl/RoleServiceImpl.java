package com.itheima.bos.service.impl;

import com.itheima.bos.bean.Function;
import com.itheima.bos.bean.Role;
import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.dao.IRoleDao;
import com.itheima.bos.service.IRoleService;
import com.itheima.bos.utils.PageBean;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IFunctionDao functionDao;
    @Override
    //保存一个角色，还需关联权限
    public void save(Role model, String functionIds) {
        //保存角色
     roleDao.save(model);
     if(StringUtils.isNotEmpty(functionIds)){
         String[] split = functionIds.split(",");
         for (String id:split) {
             //通过id查询出权限
             Function functionDaoById = functionDao.findById(id);
             //角色关联权限
             model.getFunctions().add(functionDaoById);
         }
     }
    }

    @Override
    public PageBean<Role> pageQuery(DetachedCriteria criteria, int page, int rows) {
        PageBean<Role> pageBean=new PageBean<Role>();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);
        int total = roleDao.getTotalSize(criteria);
        pageBean.setTotal(total);
        int totalPage=(int) Math.ceil(total*1.0/rows);
        pageBean.setTotalPage(totalPage);
        List<Role> list=roleDao.getPageList(criteria,page,rows);
        pageBean.setRows(list);
        return pageBean;
    }

    @Override
    public List<Role> findAll(DetachedCriteria criteria) {
        return roleDao.findAll(criteria);
    }
}
