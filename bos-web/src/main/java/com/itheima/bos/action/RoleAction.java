package com.itheima.bos.action;

import com.itheima.bos.bean.Function;
import com.itheima.bos.bean.Role;
import com.itheima.bos.service.IFunctionService;
import com.itheima.bos.service.IRoleService;
import com.itheima.bos.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{
    @Autowired
    private IRoleService roleService;
    private String functionIds;

    public void setFunctionIds(String functionIds) {
        this.functionIds = functionIds;
    }

    //保存
    public String save() throws IOException {
        roleService.save(model,functionIds);
        return "save_role";
    }
    //分页查询
    public String pageQuery() throws IOException {

        PageBean<Role> pageBean=roleService.pageQuery(criteria, page,rows);
        java2Json(pageBean, new String[]{"totalPage","pageSize","currentPage","users","functions"});
        return NONE;
    }

    //用户选择角色里的，查询所有
    public String findAll(){
        List<Role> list=roleService.findAll(criteria);
        list2Json(list,new String[]{"code","description","functions","users"});
        return NONE;
    }
}
