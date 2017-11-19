package com.itheima.bos.action;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.bean.Decidedzone;
import com.itheima.bos.service.IDecidedzoneService;
import com.itheima.bos.utils.PageBean;
import com.itheima.crm.Customer;
import com.itheima.crm.ICustomerService;

/**  
 * ClassName:DecidedzoneAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 4, 2017 5:16:21 PM <br/>       
 */
@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone>{
    private String[] subareaid;
    @Autowired
    private IDecidedzoneService decidedzoneService;
   
    public String save(){
        decidedzoneService.save(model,subareaid);
        return "save_decidedzone";
    }
    public String pageQuery() throws IOException{
        PageBean<Decidedzone> pageBean = decidedzoneService.pageQuery(criteria,page,rows);
        this.java2Json(pageBean, new String[]{"currentPage","criteria","totalPage","pageSize","subareas","decidedzones"});
        return NONE;
     }
    //查询未关联定区的用户
   @Autowired
    private ICustomerService proxy;
  public String findListNotAssociation() throws IOException{
     List<Customer> list = proxy.findListNotAssociation();
       list2Json(list, null);
       return NONE;
    }
  //查询已关联定区的用户
  public String findListHasAssociation() throws IOException{
        String id = model.getId();
        List<Customer> list = proxy.findListHasAssociation(id);
        list2Json(list, null);
        return NONE;
  }
  //属性驱动，接收页面提交的多个客户id
  private List<Integer> customerIds;
  public void setCustomerIds(List<Integer> customerIds) {
    this.customerIds = customerIds;
}
/**
   * 远程调用crm服务，将客户关联到定区
   */
  public String assigncustomerstodecidedzone(){
      proxy.assigncustomerstodecidedzone(model.getId(), customerIds);
      return "save_decidedzone";
  }

    public void setSubareaid(String[] subareaid) {
        this.subareaid = subareaid;
    }
    
    
}




  
