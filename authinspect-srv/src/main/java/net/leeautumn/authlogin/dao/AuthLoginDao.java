package net.leeautumn.authlogin.dao;

import com.autumn.authcheck.authlogin.model.RootListEntity;
import com.autumn.authcheck.common.dao.GenericEntityDao;

import javax.inject.Named;

/**
 * Created by coolAutumn on 3/10/16.
 */
@Named("authLoginDao")
public class AuthLoginDao extends GenericEntityDao<RootListEntity,String>
{
    @Override
    protected Class<RootListEntity> entityClass() {
        return RootListEntity.class;
    }
}
