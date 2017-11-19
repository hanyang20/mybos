package com.itheima.bos.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.itheima.bos.bean.Region;
import com.itheima.bos.bean.Staff;
import com.itheima.bos.bean.User;
import com.itheima.bos.dao.IBaseDao;

/**  
 * ClassName:IBaseDaoImpl <br/>  
 * Function:  <br/>  
 * Date:     Oct 27, 2017 3:36:29 PM <br/>       
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
    //代表的是某个实体的类型
    private Class clazz;
  //在父类（BaseDaoImpl）的构造方法中动态获得clazz
    public BaseDaoImpl(){
      //获得当前类型的带有泛型类型的父类
       ParameterizedType p = (ParameterizedType) this.getClass().getGenericSuperclass();
     //获得运行期的泛型类型
       clazz = (Class) p.getActualTypeArguments()[0];
   }
    //根据类型注入spring工厂的回话对象sessionFactory
    @Resource(name="sessionFactory")
     private void setMySessionFactory(SessionFactory sessionFactory) {
       super.setSessionFactory(sessionFactory);

    }
    @Override
    public void save(T t) {
       this.getHibernateTemplate().save(t); 
    }

    @Override
    public void delete(T t) {
          this.getHibernateTemplate().delete(t);
        
    }
    @Override
    public void delete(Serializable id) {
          Object object = this.getHibernateTemplate().get(clazz, id);
          this.getHibernateTemplate().delete(object);
        
    }
    @Override
    public void update(T t) {
          this.getHibernateTemplate().update(t);
        
    }
    @Override
    public T findById(Serializable id) {
          
        return (T) this.getHibernateTemplate().get(clazz, id);
    }

    @Override
    public List<T> findAll() {
          
        String hql="from"+clazz.getSimpleName(); 
        return  (List<T>) this.getHibernateTemplate().find(hql);
    }
    /**
     * 修改密码
     * TODO 简单描述该方法的实现功能（可选）.  
     * @see com.itheima.bos.dao.IBaseDao#excuteUpdate(java.lang.String, java.lang.Object[])
     */
  /*  @Override
    public void excuteUpdate(String queryName, Object... objects) {
        Session session = this.getSessionFactory().getCurrentSession();
        Query query = session.getNamedQuery(queryName);
        int i = 0;
        for (Object object : objects) {
            //为HQL语句中的？赋值
            query.setParameter(i++, object);
        }
        //执行更新
        query.executeUpdate();
    }*/
    @Override
    public void saveOrUpdate(T t) {
       getHibernateTemplate().saveOrUpdate(t);
        
    }
    @Override
    public int getTotalSize(DetachedCriteria criteria) {
          criteria.setProjection(Projections.rowCount());
        List<Long> list = (List<Long>) getHibernateTemplate().findByCriteria(criteria); 
        criteria.setProjection(null);
        criteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
        if(list!=null && list.size()>0){
            return list.get(0).intValue();
        }
        return 0;
    }
    @Override
    public List<T> getPageList(DetachedCriteria criteria, int currentPage,
            int pageSize) {
          
        List<T> list = (List<T>) getHibernateTemplate().findByCriteria(criteria, (currentPage-1)*pageSize, pageSize);
        return list;
    }
  //执行更新
    public void executeUpdate(String queryName, Object... objects) {
        Session session = this.getSessionFactory().getCurrentSession();
        Query query = session.getNamedQuery(queryName);
        int i = 0;
        for (Object object : objects) {
            //为HQL语句中的？赋值
            query.setParameter(i++, object);
        }
        //执行更新
        query.executeUpdate();
    }
    @Override
    public List<T> findAll(DetachedCriteria criteria) {
          List<T> list = (List<T>) this.getHibernateTemplate().findByCriteria(criteria);
        return list;
    }

}
  
