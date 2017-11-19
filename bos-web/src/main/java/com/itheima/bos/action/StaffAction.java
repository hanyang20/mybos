package com.itheima.bos.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.bean.Staff;
import com.itheima.bos.bean.Staff;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:StaffAction <br/>  
 * Function:  <br/>  
 * Date:     Oct 30, 2017 9:36:44 AM <br/>       
 */
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>{
    @Autowired
    private IStaffService staffService;
    /**
     * 保存或修改
     * save:. <br/>  
     *  
     * @return
     * @throws Exception
     */
    public String save() throws Exception {
        staffService.save(model);
        return "add_satff";
    }
    public String pageQuery() throws IOException{
        PageBean<Staff> pageBean =staffService.pageQuery(criteria,page,rows);
        this.java2Json(pageBean, new String[]{"currentPage","criteria","totalPage","pageSize","decidedzones"});
        return NONE;
    }
    /**
     * 批量删除
     */
    private String ids;
    public String deleteBatch(){
        staffService.deleteBatch(ids);
        return "add_satff";
        
    }
    /**
     * 查询所有
     * deleteBatch:. <br/>  
     *  
     * @return
     * @throws IOException 
     */
    public String findAll() throws IOException{
        //添加过滤条件，deltag等于0的
        criteria.add(Restrictions.eq("deltag", "0"));
        List<Staff> list = staffService.findAll(criteria);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"decidedzones"});
        String json = JSONArray.fromObject(list,jsonConfig).toString();
        ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
        ServletActionContext.getResponse().getWriter().println(json);
        return NONE;
    
    }
   
    
    
    
    
    
    
    
    
    
    
    
    public String edit(){
        return ids;
        
    }
    public void setIds(String ids) {
        this.ids = ids;
    }
    
}
  
