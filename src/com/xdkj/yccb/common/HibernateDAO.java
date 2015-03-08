package com.xdkj.yccb.common;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
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
@SuppressWarnings("unchecked")
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

	/**
	 * entity class
	 */
	private Class<T> entityClass;

	/**
	 * 获取泛型的类型
	 */
	public HibernateDAO() {
		entityClass = ReflectUtils.getClassGenricType(getClass());
	}

	/**
	 * 功能说明：根据实体进行查询
	 *
	 * @param clazz clazz
	 * @param id id
	 * @param <X> <X>
	 * @return <X> <X>
	 *
	 */
	public final <X> X get(final Class<X> clazz, final Serializable id) {
		return this.getHibernateTemplate().get(clazz, id);
	}

	/**
	 * 功能说明：
	 *
	 * @param id id
	 * @return T 查询结果
	 *
	 */
	public final T get(final Serializable id) {
		return get(entityClass, id);
	}

	/**
	 * 功能说明：
	 *
	 * @param entity entity
	 */
	public final void delete(final Object entity) {
		this.getHibernateTemplate().delete(entity);
	}

	/**
	 * 功能说明：
	 *
	 * @param id id
	 *
	 */
	public final void delete(final Serializable id) {
		delete(get(id));
	}

	/**
	 * 功能说明：
	 *
	 * @param clazz class
	 * @param id Serializable id
	 *
	 */
	public final void delete(final Class<T> clazz, final Serializable id) {
		delete(get(clazz, id));
	}

	/**
	 * 功能说明：
	 *
	 * @param entity 实体
	 *
	 */
	public final void save(final Object entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	/**
	 * 功能说明：
	 *
	 * @param newEntityClass 实体类
	 * @return <T> 结果集
	 *
	 */
	public final <X> List<X> getAll(final Class<T> newEntityClass) {
		return createCriteria(newEntityClass).list();
	}

	/**
	 * 功能说明：
	 *
	 * @return List<T> 结果集
	 *
	 */
	public final List<T> getAll() {
		return query();
	}

	/**
	 * 功能说明：
	 *
	 * @param criterions criterions
	 * @return list<T>查询结果
	 *
	 */
	public final List<T> query(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	/**
	 * 功能说明：
	 *
	 * @param hql hql语句
	 * @param values 多个查询条件
	 * @return query 查询结果
	 *
	 */
	public final Query createQuery(final String hql, final Object... values) {
		Query query = getSession().createQuery(hql);
		int j = values.length;
		for (int i = 0; i < j; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}
	
	public final void executeUpdate(final String hql, final Object... values){
		Query query = createQuery(hql, values);
		query.executeUpdate();
	}

	/**
	 * 功能说明：
	 *
	 * @param sql sql语句
	 * @param values 查询条件
	 * @return SQLQuery 查询结果
	 *
	 */
	public final SQLQuery createSQLQuery(final String sql, final Object... values) {
		SQLQuery query = getSession().createSQLQuery(sql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 功能说明：
	 *
	 * @param clazz 实体
	 * @return Criteria Criteria
	 *
	 */
	public final Criteria createCriteria(final Class<T> clazz) {
		return getSession().createCriteria(clazz);
	}

	/**
	 * 功能说明：
	 *
	 * @param clazz 实体
	 * @param criterions criterions
	 * @return Criteria Criteria
	 *
	 */
	public final Criteria createCriteria(final Class<T> clazz, final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(clazz);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 功能说明：
	 *
	 * @param criterions criterions
	 * @return Criteria Criteria
	 *
	 */
	public final Criteria createCriteria(final Criterion... criterions) {
		return createCriteria(entityClass, criterions);
	}
	/**
	 * 功能说明：
	 *
	 * @param queryString HQL
	 * @param values 条件
	 * @return List 查询结果
	 *
	 */
	public final List<T> findByHQL(String queryString, final Object... values){
		Query query = createQuery(queryString, values);
		return (List<T>) query.list();
	}
	/**
	 * 功能说明：
	 *
	 * @param queryString sql
	 * @param values 条件
	 * @return List 查询结果
	 *
	 */
	public final List<T> findBySQL(String queryString, final Object... values){
		SQLQuery query = createSQLQuery(queryString, values);
		return (List<T>) query.list();
	}
}