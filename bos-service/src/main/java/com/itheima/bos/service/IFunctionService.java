package com.itheima.bos.service;

import com.itheima.bos.bean.Function;
import com.itheima.bos.bean.User;
import com.itheima.bos.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public interface IFunctionService {
    List<Function> findAll();

    void save(Function model);

    PageBean<Function> pageQuery(DetachedCriteria criteria, int page, int rows);

    List<Function> findMenu(User user);
}
