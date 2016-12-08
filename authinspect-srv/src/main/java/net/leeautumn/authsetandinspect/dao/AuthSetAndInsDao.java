package net.leeautumn.authsetandinspect.dao;

import net.leeautumn.authsetandinspect.model.UserInfoEntity;
import net.leeautumn.common.dao.GenericEntityDao;

import javax.inject.Named;

/**
 * Created by coolAutumn on 3/7/16.
 */
@Named("authSetAndInsDao")
public class AuthSetAndInsDao extends GenericEntityDao<UserInfoEntity,String>{
    @Override
    protected Class<UserInfoEntity> entityClass() {
        return UserInfoEntity.class;
    }
}
