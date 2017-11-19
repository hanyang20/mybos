package com.itheima.bos.action;

import com.itheima.bos.bean.Workordermanage;
import com.itheima.bos.service.IWorkordermanageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope(scopeName="prototype")
public class WorkordermanageAction extends BaseAction<Workordermanage> {
    @Autowired
    private IWorkordermanageService workordermanageService;
    public String save(){
        String f="1";
        try {
            workordermanageService.save(model);
        }catch (Exception e){
               e.printStackTrace();
               f="0";
        }
           return NONE;
    }
}
