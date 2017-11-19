package com.itheima.bos.action;

import com.itheima.bos.bean.Noticebill;
import com.itheima.bos.service.INoticebillService;
import com.itheima.crm.Customer;
import com.itheima.crm.ICustomerService;
import com.opensymphony.xwork2.ActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill> {
    @Autowired
    private ICustomerService proxy;
    @Autowired
    private INoticebillService noticebillService;
    //添加业务通知单，中的客户回显信息
    public String  add(){
        Customer customer = proxy.findCustomerByTelephone(model.getTelephone());
        java2Json(customer,null);
        return NONE;

    }
    //保存业务通知单
    public String  save(){
        noticebillService.save(model);
        return "add_noticebill";

    }
}
