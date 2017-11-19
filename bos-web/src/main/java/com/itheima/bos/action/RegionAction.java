package com.itheima.bos.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestOperations;

import com.fasterxml.jackson.annotation.JsonTypeInfo.None;
import com.itheima.bos.bean.Region;
import com.itheima.bos.bean.Region;
import com.itheima.bos.service.IRegionService;
import com.itheima.bos.utils.PageBean;
import com.itheima.bos.utils.PinYin4jUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:RegionAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 1, 2017 10:47:41 AM <br/>       
 */
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region>{
    private File regionFile;
    @Autowired
   private IRegionService regionService;
    
    //导入区域数据
    public String importXls() throws Exception {
        List<Region> regions=new ArrayList<>();
        //使用poi解析xls文件
        HSSFWorkbook hssfWorkbook=new HSSFWorkbook(new FileInputStream(regionFile));
        HSSFSheet sheetAt = hssfWorkbook.getSheetAt(0);
        for (Row row : sheetAt) {
            int num = row.getRowNum();
            if(num == 0){
                continue;
            }
            String id = row.getCell(0).getStringCellValue();
            String province = row.getCell(1).getStringCellValue();
            String city = row.getCell(2).getStringCellValue();
            String district = row.getCell(3).getStringCellValue();
            String postcode = row.getCell(4).getStringCellValue();
            Region region=new Region(id, province, city, district, postcode, null, null, null);
         
            province=province.substring(0,province.length()-1);
            city=city.substring(0,city.length()-1);
            district=district.substring(0,district.length()-1);
            String sum=province+city+district;
          //简码---->>GDSZNS
            String[] strings = PinYin4jUtils.getHeadByString(sum);
            String shortcode = StringUtils.join(strings);
            //城市编码
            String citycode = PinYin4jUtils.hanziToPinyin(city, "");
            region.setCitycode(citycode);
            region.setShortcode(shortcode);
            regions.add(region);
        }
        regionService.saveBatch(regions);
        return NONE;
    }
  /*//当前页
    private int page;
    //每页显示条数
    private int rows;*/
    public String pageQuery() throws IOException{
        /*DetachedCriteria criteria=DetachedCriteria.forClass(Region.class);
        PageBean<Region> pageBean =regionService.pageQuery(criteria,page,rows);
        //指定哪些属性不需要转json
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"currentPage","criteria","totalPage","pageSize"});
        //2 将列表数据转换为json发送给浏览器
        //total:总记录数 
       //rows:每行显示的数据
        //{total:xx,rows:[{user_id:1,user_name:'tom'}]}
        //ActionContext.getContext().put("pageBean" , pageBean);
        Map map  = new HashMap();
        map.put("total", pageBean.getTotalCount());
        map.put("rows", pageBean.getList());
      //将map转换为json
        String json = JSONObject.fromObject(map,jsonConfig).toString();
        ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
        ServletActionContext.getResponse().getWriter().println(json);*/
        PageBean<Region> pageBean =regionService.pageQuery(criteria,page,rows);
        this.java2Json(pageBean, new String[]{"currentPage","criteria","totalPage","pageSize","subareas"});
        return NONE;
     }
     private String q;
    public String findAll() throws IOException{
        if(StringUtils.isNotBlank(q)){
            criteria.add(Restrictions.or(
                                         Restrictions.like("citycode", "%"+q+"%"),
                                         Restrictions.like("shortcode", "%"+q+"%"),
                                         Restrictions.like("province", "%"+q+"%"),
                                         Restrictions.like("city", "%"+q+"%"),
                                         Restrictions.like("district", "%"+q+"%")
                                         ));
            
        }
        List<Region> list = regionService.findAll(criteria);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas"});
        String json = JSONArray.fromObject(list,jsonConfig).toString();
        ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
        ServletActionContext.getResponse().getWriter().println(json);
        return NONE;
    }
    public void setRegionFile(File regionFile) {
        this.regionFile = regionFile;
    }
    public void setQ(String q) {
        this.q = q;
    }
    
}
  
