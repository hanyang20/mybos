package com.itheima.bos.service.impl;

import com.itheima.bos.bean.Decidedzone;
import com.itheima.bos.bean.Function;
import com.itheima.bos.bean.User;
import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.service.IFunctionService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.bos.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService {
    @Autowired
    private IFunctionDao functionDao;
    @Override
    public List<Function> findAll() {
        return functionDao.findAll();
    }

    @Override
    public void save(Function model) {
        Function parentFunction = model.getParentFunction();
        if(parentFunction!=null&&parentFunction.getId().equals("")){
            model.setParentFunction(null);
        }
        functionDao.save(model);
    }

    @Override
    public PageBean<Function> pageQuery(DetachedCriteria criteria, int page, int rows) {
        PageBean<Function> pageBean=new PageBean<Function>();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);
        int total = functionDao.getTotalSize(criteria);
        pageBean.setTotal(total);
        int totalPage=(int) Math.ceil(total*1.0/rows);
        pageBean.setTotalPage(totalPage);
        List<Function> list=functionDao.getPageList(criteria,page,rows);
        pageBean.setRows(list);
        return pageBean;
    }

    @Override
    public List<Function> findMenu(User user) {
        List<Function> list = null;
        if(user.getUsername().equals("ls")){
            //如果是超级管理员内置用户，查询所有菜单
            list = functionDao.findAllMenu();
        }else{
            //其他用户，根据用户id查询菜单
            list = functionDao.findMenuByUserId(user.getId());
        }
        return list;
    }
}
