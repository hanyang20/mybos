package com.itheima.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.bean.Subarea;
import com.itheima.bos.bean.Subarea;
import com.itheima.bos.dao.ISubareaDao;
import com.itheima.bos.service.ISubareaService;
import com.itheima.bos.utils.PageBean;

/**  
 * ClassName:SubareaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 1, 2017 5:29:54 PM <br/>       
 */
@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService{
    @Autowired
    private ISubareaDao subareaDao;
    @Override
    public void save(Subarea model) {
          
        subareaDao.save(model); 
        
    }
    @Override
    public PageBean<Subarea> pageQuery(DetachedCriteria criteria, int page,
            int rows) {
          
        PageBean<Subarea> pageBean=new PageBean<>();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);
        int total = subareaDao.getTotalSize(criteria);
        pageBean.setTotal(total);
        int totalPage=(int) Math.ceil(total*1.0/rows);
        pageBean.setTotalPage(totalPage);
        List<Subarea> list=subareaDao.getPageList(criteria,page,rows);
        pageBean.setRows(list);
        return pageBean;
    }
  //查询所有  , 查找没有关联定区的分区
    @Override
    public List<Subarea> findAll(DetachedCriteria criteria) {
          
        return subareaDao.findAll(criteria);
    }
 // 根据定区id查询关联的分区
    @Override
    public List<Subarea> findBydDecidedzoneId(DetachedCriteria criteria) {
          
        return subareaDao.findAll(criteria);
    }

}
  
