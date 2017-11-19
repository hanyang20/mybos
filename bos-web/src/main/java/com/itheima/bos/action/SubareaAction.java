package com.itheima.bos.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.bean.Region;
import com.itheima.bos.bean.Staff;
import com.itheima.bos.bean.Subarea;
import com.itheima.bos.service.ISubareaService;
import com.itheima.bos.utils.FileUtils;
import com.itheima.bos.utils.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**  
 * ClassName:SubareaAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 1, 2017 5:18:56 PM <br/>       
 */
@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea>{
        /**  
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).  
     * @since JDK 1.6  
     */
    private static final long serialVersionUID = 1L;
        @Autowired
        private ISubareaService subareaService;
        
        
        public String save(){
            subareaService.save(model);
            return "save_subarea";
        }
        public String pageQuery() throws IOException{
            if(StringUtils.isNotBlank(model.getAddresskey())){
                criteria.add(Restrictions.like("addresskey", model.getAddresskey()));
            }
            if(model.getRegion() != null){
                criteria.createAlias("region", "r");
                if(StringUtils.isNotBlank(model.getRegion().getProvince())){
                    criteria.add(Restrictions.like("r.province", "%"+model.getRegion().getProvince()+"%"));
                }
                if(StringUtils.isNotBlank(model.getRegion().getCity())){
                    criteria.add(Restrictions.like("r.city", "%"+model.getRegion().getCity()+"%"));
                }
                if(StringUtils.isNotBlank(model.getRegion().getDistrict())){
                    criteria.add(Restrictions.like("r.district", "%"+model.getRegion().getDistrict()+"%"));
                }
            }
            
            
            PageBean<Subarea> pageBean =subareaService.pageQuery(criteria,page,rows);
            this.java2Json(pageBean, new String[]{"currentPage","criteria","totalPage","pageSize","decidedzone","subareas"});
            return NONE;
         }
        //导出分区数据，使用POI
        public String exportXls() throws IOException{
//            第一步：查询所有的分区数据
            List<Subarea> list=subareaService.findAll(criteria);
//            第二步：使用POI将数据写到Excel文件中
                 //在内存中创建一个Excel文件
            HSSFWorkbook hssfWorkbook=new HSSFWorkbook();
                //创建一个标签页
            HSSFSheet createSheet = hssfWorkbook.createSheet("分区数据");
               //创建标题行
            HSSFRow headerRow = createSheet.createRow(0);
            headerRow.createCell(0).setCellValue("分区编号");
            headerRow.createCell(1).setCellValue("开始编号");
            headerRow.createCell(2).setCellValue("结束编号");
            headerRow.createCell(3).setCellValue("省市区");
            headerRow.createCell(4).setCellValue("位置信息");
             
              for (Subarea subarea : list) {
                  HSSFRow dateRow=createSheet.createRow(createSheet.getLastRowNum()+1);
                  dateRow.createCell(0).setCellValue(subarea.getId());
                  dateRow.createCell(1).setCellValue(subarea.getStartnum());
                  dateRow.createCell(2).setCellValue(subarea.getEndnum());
                  dateRow.createCell(3).setCellValue(subarea.getRegion().getName());
                  dateRow.createCell(4).setCellValue(subarea.getPosition());
            }
              //第三步：使用输出流进行文件下载（一个流、两个头）
              String filename="分区数据.xls";
              String contentType = ServletActionContext.getServletContext().getMimeType(filename);
              ServletActionContext.getResponse().setContentType(contentType);
              ServletOutputStream outputStream = ServletActionContext.getResponse().getOutputStream();
              //获取客户端浏览器类型 
              String agent=ServletActionContext.getRequest().getHeader("User-Agent");
              filename = FileUtils.encodeDownloadFilename(filename, agent);
              ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename="+filename);

              hssfWorkbook.write(outputStream);
              outputStream.close();
            return NONE;
            
        }
        /**
         * 查询所有  , 查找没有关联定区的分区
         * deleteBatch:. <br/>  
         *  
         * @return
         * @throws IOException 
         */
        public String findAll() throws IOException{
            //添加过滤条件，查找没有关联定区的分区
            criteria.add(Restrictions.isNull("decidedzone"));
            List<Subarea> list = subareaService.findAll(criteria);
            JsonConfig jsonConfig=new JsonConfig();
            jsonConfig.setExcludes(new String[]{"decidedzone","region"});
            String json = JSONArray.fromObject(list,jsonConfig).toString();
            ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
            ServletActionContext.getResponse().getWriter().println(json);
            return NONE;
        
        }
        
        /**
         * 根据定区id查询关联的分区
         * findBydDecidedzoneId:. <br/>  
         *  
         * @return
         */
        private String decidedzoneId;
        public String findBydDecidedzoneId(){
//            criteria.add(Restrictions.eq("decidedzone.id", decidedzoneId));
//           List<Subarea> list=subareaService.findBydDecidedzoneId(criteria);
//           list2Json(list, new String[]{"currentPage","criteria","totalPage","pageSize","subareas","decidedzone"});
           
            //完全可以写上面的但是为了看的更清楚
            if(model.getDecidedzone() != null){
                criteria.createAlias("decidedzone", "d");
                if(StringUtils.isNotBlank(model.getDecidedzone().getId())){
                    criteria.add(Restrictions.eq("d.id", decidedzoneId));
                }
            }
            PageBean<Subarea> pageBean =subareaService.pageQuery(criteria,page,rows);
            this.java2Json(pageBean, new String[]{"currentPage","criteria","totalPage","pageSize","decidedzone","subareas"});
            return NONE;
            
        }
       
        
        
        
        
        
        
        
        public void setDecidedzoneId(String decidedzoneId) {
            this.decidedzoneId = decidedzoneId;
        }
}
  
