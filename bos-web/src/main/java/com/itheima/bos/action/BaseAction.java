package com.itheima.bos.action;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;

import com.itheima.bos.bean.Staff;
import com.itheima.bos.utils.PageBean;
import com.itheima.crm.Customer;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:BaseAction <br/>  
 * Function:  <br/>  
 * Date:     Oct 27, 2017 7:04:34 PM <br/>       
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
    protected static final String HOME = "home";
    /**  
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).  
     * @since JDK 1.6  
     */
    private static final long serialVersionUID = 1L;
    //当前页
    protected int page;
    //每页显示条数
    protected int rows;
    protected DetachedCriteria criteria;
   
    protected T model;
   //将指定java对象转换为json，并响应给页面
    public void java2Json(Object o,String[] excludes){
      //指定哪些属性不需要转json
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(excludes);
        //2 将列表数据转换为json发送给浏览器
        //total:总记录数 
       //rows:每行显示的数据
        //{total:xx,rows:[{user_id:1,user_name:'tom'}]}
        //ActionContext.getContext().put("pageBean" , pageBean);
        /*PageBean<T> pageBean=new PageBean<>();
        Map map  = new HashMap();
        map.put("total", pageBean.getTotalCount());
        map.put("rows", pageBean.getList());*/
      //将map转换为json
        String json = JSONObject.fromObject(o,jsonConfig).toString();
        ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
        try {
            ServletActionContext.getResponse().getWriter().println(json);
        } catch (IOException e) {
            e.printStackTrace();  
            
        }
    }
    public void list2Json(List list,String[] excludes){
        //指定哪些属性不需要转json
          JsonConfig jsonConfig=new JsonConfig();
          jsonConfig.setExcludes(excludes);
          //2 将列表数据转换为json发送给浏览器
          //total:总记录数 
         //rows:每行显示的数据
          //{total:xx,rows:[{user_id:1,user_name:'tom'}]}
          //ActionContext.getContext().put("pageBean" , pageBean);
          /*PageBean<T> pageBean=new PageBean<>();
          Map map  = new HashMap();
          map.put("total", pageBean.getTotalCount());
          map.put("rows", pageBean.getList());*/
        //将map转换为json
          String json = JSONArray.fromObject(list,jsonConfig).toString();
          ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
          try {
              ServletActionContext.getResponse().getWriter().println(json);
          } catch (IOException e) {
              e.printStackTrace();  
              
          }
      }
    //在构造方法中动态获取实体类型，通过反射创建model对象
    public BaseAction(){
      //获得当前类型的带有泛型类型的父类
        ParameterizedType p = (ParameterizedType) this.getClass().getGenericSuperclass();
      //获得运行期的泛型类型
      Class<T>  entityClass = (Class<T>) p.getActualTypeArguments()[0];
      criteria = DetachedCriteria.forClass(entityClass);
      //通过反射创建对象
       try {
        model = entityClass.newInstance();
    } catch (Exception e) {
        e.printStackTrace();  
    }
 }
    @Override
    public T getModel() {
        return model;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
}
  
