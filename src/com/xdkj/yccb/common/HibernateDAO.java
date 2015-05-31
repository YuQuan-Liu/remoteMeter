package com.xdkj.yccb.common;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * *****************************************************************
 * Created on 2013-3-20 上午10:15:39
 * @author shugr (mailto:*****@sdas.org)
 * 功能说明： ------ empty log ------
 *
 * 修改历史
 * Revision 1.1   2014-11-20 上午10:15:39 by pc-staion
 * Update: ------ empty log ------
 ******************************************************************
 *@param <T> 泛型
 */
@Repository
public class HibernateDAO<T> extends HibernateDaoSupport {

	/**
	 * 功能说明：注入session factory
	 *
	 * @param sessionFactory spring管理的session factory
	 *
	 */
	@Resource(name="sessionFactory")
	public final void setHibernateSessionFactory(final SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public void saveBase(T t){
		getHibernateTemplate().save(t);
	}
	
	public void updateBase(T t){
		getHibernateTemplate().update(t);
	}
	
	public T getById(Class<T> beanClass,Integer id){
		return (T) getHibernateTemplate().load(beanClass, id);
	}
}