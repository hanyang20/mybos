package com.itheima.bos.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.bean.Staff;
import com.itheima.bos.utils.PageBean;

/**  
 * ClassName:iStaffService <br/>  
 * Function:  <br/>  
 * Date:     Oct 30, 2017 9:40:37 AM <br/>       
 */
public interface IStaffService {
/**
 * 添加取派员
 * save:. <br/>  
 *  
 * @param model
 */
  public void save(Staff model);
/**
 * 分页显示取派员
 * pageQuery:. <br/>  
 *  
 * @param criteria
 * @param page
 * @param rows
 * @return
 */
public PageBean<Staff> pageQuery(DetachedCriteria criteria, int currentPage, int pageSize);
/**
 * 批量删除
 * deleteBatch:. <br/>  
 *  
 * @param ids
 */
public void deleteBatch(String ids);
/**
 * 查询所有
 * findAll:. <br/>  
 *  
 * @param criteria
 * @return
 */
public List<Staff> findAll(DetachedCriteria criteria);

}
  
