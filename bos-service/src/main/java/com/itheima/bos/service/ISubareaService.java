package com.itheima.bos.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.bean.Subarea;
import com.itheima.bos.utils.PageBean;

/**  
 * ClassName:ISubareaService <br/>  
 * Function:  <br/>  
 * Date:     Nov 1, 2017 5:28:37 PM <br/>       
 */
public interface ISubareaService {

    void save(Subarea model);

    PageBean<Subarea> pageQuery(DetachedCriteria criteria, int page, int rows);
    //查询所有  , 查找没有关联定区的分区
    List<Subarea> findAll(DetachedCriteria criteria);
    // 根据定区id查询关联的分区
    List<Subarea> findBydDecidedzoneId(DetachedCriteria criteria);

}
  
