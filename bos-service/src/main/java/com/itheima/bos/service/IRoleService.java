package com.itheima.bos.service;

import com.itheima.bos.bean.Role;
import com.itheima.bos.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public interface IRoleService {
    void save(Role model, String functionIds);

    PageBean<Role> pageQuery(DetachedCriteria criteria, int page, int rows);

    List<Role> findAll(DetachedCriteria criteria);
}
