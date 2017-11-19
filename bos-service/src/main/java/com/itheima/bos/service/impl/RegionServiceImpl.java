package com.itheima.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.bean.Region;
import com.itheima.bos.bean.Region;
import com.itheima.bos.dao.IRegionDao;
import com.itheima.bos.service.IRegionService;
import com.itheima.bos.utils.PageBean;

/**  
 * ClassName:RegionService <br/>  
 * Function:  <br/>  
 * Date:     Nov 1, 2017 1:08:39 PM <br/>       
 */
@Service
@Transactional
public class RegionServiceImpl implements IRegionService {
    @Autowired
    private IRegionDao regionDao;
    @Override
    public void saveBatch(List<Region> regions) {
          for (Region region : regions) {
            regionDao.saveOrUpdate(region);
        }          
        
    }
    @Override
    public PageBean<Region> pageQuery(DetachedCriteria criteria, int page,
            int rows) {
          
        PageBean<Region> pageBean=new PageBean<>();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);
        int total = regionDao.getTotalSize(criteria);
        pageBean.setTotal(total);
        int totalPage=(int) Math.ceil(total*1.0/rows);
        pageBean.setTotalPage(totalPage);
        List<Region> list=regionDao.getPageList(criteria,page,rows);
        pageBean.setRows(list);
        return pageBean;
    }
    @Override
    public List<Region> findAll(DetachedCriteria criteria) {
          
        return regionDao.findAll(criteria);
    }

}
  
