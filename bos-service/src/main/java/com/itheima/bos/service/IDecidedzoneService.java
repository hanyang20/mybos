package com.itheima.bos.service;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.bean.Decidedzone;
import com.itheima.bos.utils.PageBean;

/**  
 * ClassName:IDecidedzoneService <br/>
 * Function:  <br/>  
 * Date:     Nov 4, 2017 6:45:02 PM <br/>       
 */
public interface IDecidedzoneService {

    void save(Decidedzone model, String[] subareaid);

    PageBean<Decidedzone> pageQuery(DetachedCriteria criteria, int page,
            int rows);

}
  
