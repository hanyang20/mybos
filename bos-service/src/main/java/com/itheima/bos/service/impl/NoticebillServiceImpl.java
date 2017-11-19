package com.itheima.bos.service.impl;

import com.itheima.bos.bean.*;
import com.itheima.bos.dao.IDecidedzoneDao;
import com.itheima.bos.dao.INoticebillDao;
import com.itheima.bos.dao.IWorkbillDao;
import com.itheima.bos.service.INoticebillService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.crm.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@Transactional
public class NoticebillServiceImpl implements INoticebillService {
    @Autowired
    private INoticebillDao noticebillDao;
    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IDecidedzoneDao decidedzoneDao;

    @Autowired
    private IWorkbillDao workbillDao;
    @Override
    public void save(Noticebill model) {
        User user = BOSUtils.getUser();
        model.setUser(user);
        noticebillDao.save(model);
        //取出用户的地址，去查询定区id
        String pickaddress = model.getPickaddress();
        //远程调用crm服务，根据取件地址查询定区id
        String decidedzoneId = customerService.findDecidedzoneIdByAddress(pickaddress);
        if(decidedzoneId != null){
            //如果查询到了定区id，可以完成自动分单
            //根据定区id查询所在的定区
          Decidedzone decidedzone=decidedzoneDao.findById(decidedzoneId);
          //把定区所在的取派员取出来，添加到业务通知单中
          Staff staff=decidedzone.getStaff();
          model.setStaff(staff);//业务通知单关联取派员
          // 完成自动分单
          model.setOrdertype(Noticebill.ORDERTYPE_AUTO);

          //为取派员产生一个工单
            Workbill workbill=new Workbill();
            workbill.setAttachbilltimes(0);//追单次数
            workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));
            workbill.setPickstate(Workbill.PICKSTATE_NO);//取件状态:未取件、取件中、已取件
            workbill.setType(Workbill.TYPE_1);//新单  //工单类型:新、追、改、销
            workbill.setRemark(model.getRemark());
            workbill.setNoticebill(model);
            workbill.setStaff(staff);
            workbillDao.save(workbill);

            //调用短信平台，发送短信
        }else{
            //如果没有查询到了定区id，不能完成自动分单，那就进行人工分单
            model.setOrdertype(Noticebill.ORDERTYPE_MAN);
        }

    }
}