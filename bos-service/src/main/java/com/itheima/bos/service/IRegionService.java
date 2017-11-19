package com.itheima.bos.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.bean.Region;
import com.itheima.bos.utils.PageBean;

/**  
 * ClassName:IRegionService <br/>  
 * Function:  <br/>  
 * Date:     Nov 1, 2017 1:06:37 PM <br/>       
 */
public interface IRegionService {

    void saveBatch(List<Region> regions);

    PageBean<Region> pageQuery(DetachedCriteria criteria, int page, int rows);


    List<Region> findAll(DetachedCriteria criteria);

}
  
