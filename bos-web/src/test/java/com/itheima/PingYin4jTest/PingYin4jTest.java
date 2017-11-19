package com.itheima.PingYin4jTest;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.itheima.bos.utils.PinYin4jUtils;

/**  
 * ClassName:PingYin4jTest <br/>  
 * Function:  <br/>  
 * Date:     Nov 1, 2017 2:10:43 PM <br/>       
 */
public class PingYin4jTest {
    @Test
  public void PingYin4jTest(){
        String province = "广东省";
        String city = "深圳市";
        String district = "南山区";
        province=province.substring(0,province.length()-1);
        city=city.substring(0,city.length()-1);
        district=district.substring(0,district.length()-1);
        String sum=province+city+district;
      //简码---->>GDSZNS
        String[] strings = PinYin4jUtils.getHeadByString(sum);
        String join = StringUtils.join(strings);
        System.out.println(join);
        //城市编码
        String hanziToPinyin = PinYin4jUtils.hanziToPinyin(city, "");
        System.out.println(hanziToPinyin);
  }
}
  
