package com.itheima.bos.service.impl;

import java.util.List;

import com.itheima.bos.service.IDecidedzoneService;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.bean.Decidedzone;
import com.itheima.bos.bean.Subarea;
import com.itheima.bos.dao.IDecidedzoneDao;
import com.itheima.bos.dao.ISubareaDao;
import com.itheima.bos.utils.PageBean;

/**  
 * ClassName:DecidedzoneServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 4, 2017 6:45:19 PM <br/>       
 */
@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService {
    @Autowired
    private IDecidedzoneDao decidedzoneDao;
    @Autowired
    private ISubareaDao subareaDao;
    @Override
    public void save(Decidedzone model, String[] subareaid) {
       decidedzoneDao.save(model);
       for (String id : subareaid) {
        Subarea subarea = subareaDao.findById(id);
       //model.getSubareas().add(subarea);一方（定区）已经放弃维护外键权利，只能由多方（分区）负责维护
        subarea.setDecidedzone(model);//分区关联定区
    }
       
        
    }

    @Override
    public PageBean<Decidedzone> pageQuery(DetachedCriteria criteria, int page,
            int rows) {
          
        PageBean<Decidedzone> pageBean=new PageBean<>();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);
        int total = decidedzoneDao.getTotalSize(criteria);
        pageBean.setTotal(total);
        int totalPage=(int) Math.ceil(total*1.0/rows);
        pageBean.setTotalPage(totalPage);
        List<Decidedzone> list=decidedzoneDao.getPageList(criteria,page,rows);
        pageBean.setRows(list);
        return pageBean;
    }

}
  
