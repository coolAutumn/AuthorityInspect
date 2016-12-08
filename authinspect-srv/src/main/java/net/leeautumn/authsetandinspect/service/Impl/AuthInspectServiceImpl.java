package net.leeautumn.authsetandinspect.service.Impl;

import net.leeautumn.authsetandinspect.dao.AuthSetAndInsDao;
import net.leeautumn.authsetandinspect.model.UserInfoEntity;
import net.leeautumn.authsetandinspect.service.AuthInspectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.List;

/**
 * Created by coolAutumn on 3/7/16.
 */
@Named(value = "authInspectService")
@Component
@Transactional
public class AuthInspectServiceImpl implements AuthInspectService
{
    @Autowired
    AuthSetAndInsDao authSetAndInsDao;

    @Override
    public boolean saveUserInfoEntity(UserInfoEntity userInfoEntity) {
        authSetAndInsDao.saveOrUpdate(userInfoEntity);
        return true;
    }

    @Override
    public int getUserInfoRecordNum() {
        return authSetAndInsDao.findAll().size();
    }

    @Override
    public List getInfoByMatchcode(String matchcode)
    {
        return authSetAndInsDao.getCurrentSession().createSQLQuery("select *  FROM userInfo where userInfo.MatchCode like '"+matchcode+"'")
                .addEntity(UserInfoEntity.class).list();
    }

    @Override
    public List getInfoByOriginalcode(String originalcode)
    {
        return authSetAndInsDao.getCurrentSession().createSQLQuery("select *  FROM userInfo where userInfo.OriginalCode like '"+originalcode+"'")
                .addEntity(UserInfoEntity.class).list();
    }
}
