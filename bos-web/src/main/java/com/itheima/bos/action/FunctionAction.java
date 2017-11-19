package com.itheima.bos.action;

import com.itheima.bos.bean.Function;
import com.itheima.bos.bean.User;
import com.itheima.bos.service.IFunctionService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.bos.utils.PageBean;
import com.itheima.crm.Customer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function> {
     @Autowired
    private IFunctionService functionService;
    /**
     * 查询所有权限，返回json数据
     */
    public String findAll() throws IOException {
        List<Function> list = functionService.findAll();
        list2Json(list, new String[]{"roles","parentFunction"});
        return NONE;
    }
    //保存
    public String save() throws IOException {
        functionService.save(model);
        return "save_function";
    }
    //分页查询
    public String pageQuery() throws IOException {
        String function_page = model.getPage();
        page=Integer.parseInt(function_page);
        PageBean<Function> pageBean=functionService.pageQuery(criteria, page,rows);
        java2Json(pageBean, new String[]{"totalPage","pageSize","currentPage","roles","children","parentFunction"});
        return NONE;
    }
    //根据当前的用户查询对应的菜单
    public String findMenu() throws IOException {
        User user = BOSUtils.getUser();
        List<Function> list=functionService.findMenu(user);
        list2Json(list, new String[]{"totalPage","pageSize","currentPage","roles","children","parentFunction"});
        return NONE;
    }
}
