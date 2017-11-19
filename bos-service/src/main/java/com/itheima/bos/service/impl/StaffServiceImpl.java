 package com.itheima.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.bean.Staff;
import com.itheima.bos.dao.IStaffDao;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.utils.PageBean;

/**  
 * ClassName:StaffServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Oct 30, 2017 9:42:07 AM <br/>       
 */
@Service
@Transactional
public class StaffServiceImpl implements IStaffService {
    @Autowired
    private IStaffDao staffDao;
    @Override
    public void save(Staff model) {
      staffDao.saveOrUpdate(model);

    }
    @Override
    public PageBean<Staff> pageQuery(DetachedCriteria criteria, int page,
            int rows) {
        PageBean<Staff> pageBean=new PageBean<>();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);
        int total = staffDao.getTotalSize(criteria);
        pageBean.setTotal(total);
        int totalPage=(int) Math.ceil(total*1.0/rows);
        pageBean.setTotalPage(totalPage);
        List<Staff> list=staffDao.getPageList(criteria,page,rows);
        pageBean.setRows(list);
        return pageBean;
    }
    /**
     * 取派员批量删除
     * 逻辑删除，将deltag改为1
     */
    @Override
    public void deleteBatch(String ids) {
          if(StringUtils.isNotBlank(ids)){
              String[] split = ids.split(",");
              for (String id : split) {
                staffDao.executeUpdate("staff.delete",id);
            }
          }
        
        
    }
    @Override
    public List<Staff> findAll(DetachedCriteria criteria) {
          
        return staffDao.findAll(criteria);
    }

}
  
