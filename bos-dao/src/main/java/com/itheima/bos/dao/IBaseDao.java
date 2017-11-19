package com.itheima.bos.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.bean.Region;
import com.itheima.bos.bean.Staff;

/**  
 * ClassName:IBaseDao <br/>  
 * Function:  <br/>  
 * Date:     Oct 27, 2017 3:32:29 PM <br/>       
 */
public interface IBaseDao<T> {
public void save(T t);
public void delete(T t);
public void delete(Serializable  id);
public void update(T t);
public T findById(Serializable  id);
public List<T> findAll();
List<T> findAll(DetachedCriteria criteria);
//public void excuteUpdate(String queryName,Object...objects);
public void saveOrUpdate(T t);
//查询总条数
public int getTotalSize(DetachedCriteria criteria);
//每页显示的数据集合
List<T> getPageList(DetachedCriteria criteria, int currentPage,
        int pageSize);
public void executeUpdate(String queryName, Object... objects);
}
  
