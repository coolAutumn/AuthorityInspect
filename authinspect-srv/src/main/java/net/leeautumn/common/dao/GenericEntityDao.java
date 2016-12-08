package net.leeautumn.common.dao;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*import javax.annotation.Nullable;*/

/**
 * GenericEntityService.
 *
 * @author Rene Gielen
 */
@SuppressWarnings("unchecked")
@Transactional
public abstract class GenericEntityDao<T, I extends Serializable> {

    @Inject
    @Qualifier("sessionFactory")
    SessionFactory sessionFactory;

    protected abstract Class<T> entityClass();

    public T get(I id) {
        if (id != null) {
            return (T) getCurrentSession().get(entityClass(), id);
        } else {
            return null;
        }
    }

    public T load(I id) {
        return (T) getCurrentSession().load(entityClass(), id);
    }

    public <M extends T> I save(M modelObject) {
        return (I) getCurrentSession().save(modelObject);
    }

    public <M extends T> M update(M modelObject) {
        getCurrentSession().update(modelObject);
        return modelObject;
    }

    public <M extends T, R extends T> R merge(M modelObject) {
        return (R) getCurrentSession().merge(modelObject);
    }

    public <M extends T> M saveOrUpdate(M modelObject) {
        getCurrentSession().saveOrUpdate(modelObject);
        return modelObject;
    }

    public <M extends T> List<M> bulkSaveOrUpdate(M... modelObject) {
        final List<M> result = new ArrayList<M>();
        for (M m : modelObject) {
            getCurrentSession().saveOrUpdate(m);
            result.add(m);
        }
        return result;
    }

    public <M extends T> void delete(M modelObject) throws DataAccessException {
        getCurrentSession().delete(modelObject);
    }

    protected <M extends T> void lock(M modelObject, LockOptions lockMode) {
        getCurrentSession().buildLockRequest(lockMode).lock(modelObject);
    }

    public <M extends T> void attach(M modelObject) {
        if (!getCurrentSession().contains(modelObject)) {
            getCurrentSession().refresh(modelObject, LockOptions.NONE);
        }
    }

    public Query createQuery(String queryString) {
        //  String queryString = "from " + entityClass().getName();
        return getCurrentSession().createQuery(queryString);
    }

    public Query getNamedQuery(String queryName) {
        return getCurrentSession().getNamedQuery(queryName);
    }

    public void flush() {
        getCurrentSession().flush();
    }

    public Criteria createCriteria() {
        return getCurrentSession().createCriteria(entityClass());
    }

    public <M extends T> List<M> list(Criteria criteria) {
        if (criteria != null) {
            return criteria.list();
        } else {
            return Collections.emptyList();
        }
    }

    public <M extends T> List<M> findAll() {
        return list(createCriteria());
    }


  /*  public
    @Nullable
    <M> M first(List<M> list) {
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }*/

    public <M extends T> List<M> saveOrUpdate(List<M> toSave) {
        if (toSave == null || toSave.isEmpty()) {
            return Collections.emptyList();
        }
        List<M> result = new ArrayList<M>(toSave.size());
        for (M m : toSave) {
            result.add(saveOrUpdate(m));
        }
        return result;
    }

    public <M extends T> List<M> saveOrUpdate(M... toSave) {
        return saveOrUpdate(Arrays.asList(toSave));
    }

    public <M extends T> List<M> merge(List<M> toSave) {
        if (toSave == null || toSave.isEmpty()) {
            return Collections.emptyList();
        }
        List<M> result = new ArrayList<M>(toSave.size());
        for (M m : toSave) {
            result.add((M) merge(m));
        }
        return result;
    }

    public <M extends T> List<M> merge(M... toSave) {
        return merge(Arrays.asList(toSave));
    }

    public void initialize(Object toInitialize) {
        Hibernate.initialize(toInitialize);
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    protected void clear() {
        getCurrentSession().clear();
    }

    /**
     * 分页查找所有的记录
     *
     * @param pageNo
     *            要返回第几页的数据
     * @param pageSize
     *            每页记录数
     * @return
     */
    public <M extends T> List<M> findAll(int pageNo, int pageSize)
    {
        String queryString = "from " + entityClass().getName();
        Query query = this.getCurrentSession().createQuery(queryString);
        int firstResult = (pageNo - 1) * pageSize;
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);
        return query.list();
    }

    /**
     * 统计所有记录的总数
     *
     * @return 总数
     */
    public int countAll()
    {
        String queryString = "select count(*) from "
                + entityClass().getName();
        Query query = this.getCurrentSession().createQuery(queryString);
        List list = query.list();
        Long result = (Long) list.get(0);
        return result.intValue();
    }

    //根据HQL查找对象,可以找单个，也可以找列表
    public Object loadObject(String hql) {
        final String hql1 = hql;
        Object obj = null;
        List list = getCurrentSession().createQuery(hql1).list() ;
        if (list.size() > 0) {
            obj = list.get(0);
        }
        return obj;
    }

    /**
     * 通过属性查找
     *
     * @param propertyName
     *            属性名称
     * @param value
     *            属性的值
     * @return
     */
    @SuppressWarnings("unchecked")
    public <M extends T> List<M> findByStringProperty(String propertyName, String value)
    {
        String queryString = "from " + entityClass().getName()
                + " as model where model." + propertyName + "= :PROPERTY_VALUE";
        Query query = getCurrentSession().createQuery(queryString).setString("PROPERTY_VALUE",value);
        List list = query.list();
        return list;
    }

    /**
     *根据Id来删除
     * @param id
     * @param <M>
     */
    public <M extends T> void deleteById(I id){
        delete(get(id));
    }

    /**
     * 根据参数来删除
     * @param propertyName
     * @param value
     * @param <M>
     * @return
     */
    public <M extends T> boolean deleteByStringProperty(String propertyName, String value){
        String queryString = "delete " + entityClass().getName()
                + " where " + propertyName + "= :PROPERTY_VALUE";
        Query query = getCurrentSession().createQuery(queryString).setString("PROPERTY_VALUE",value);
        int ret = query.executeUpdate();
        return ret>0 ;
    }
}
