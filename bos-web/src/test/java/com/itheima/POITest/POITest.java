package com.itheima.POITest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

/**  
 * ClassName:POITest <br/>  
 * Function:  <br/>  
 * Date:     Nov 1, 2017 11:38:46 AM <br/>       
 */
public class POITest {
    //@Test
    public void test1() throws FileNotFoundException, IOException {
        String realPath="F:\\BaiduNetdiskDownload\\物流BOS系统\\BOS-day05\\BOS-day05\\资料\\区域导入测试数据.xls";
      //包装一个Excel文件对象
        HSSFWorkbook hssfWorkbook=new HSSFWorkbook(new FileInputStream(new File(realPath)));
       HSSFSheet sheetAt = hssfWorkbook.getSheetAt(0);
       for (Row row : sheetAt) {
           for (Cell cell : row) {
            System.out.println(cell);
        }
       System.out.println();
       }
  }
}
  
