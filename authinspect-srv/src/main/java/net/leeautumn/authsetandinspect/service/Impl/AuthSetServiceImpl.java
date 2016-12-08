package net.leeautumn.authsetandinspect.service.Impl;

import net.leeautumn.authsetandinspect.dao.AuthSetAndInsDao;
import net.leeautumn.authsetandinspect.model.UserInfoEntity;
import net.leeautumn.authsetandinspect.service.AuthSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.List;

/**
 * Created by coolAutumn on 3/7/16.
 */
@Named(value = "authSetService")
@Component
@Transactional
public class AuthSetServiceImpl implements AuthSetService
{
    @Autowired
    AuthSetAndInsDao authSetAndInsDao;

    @Override
    public boolean saveUserInfoEntity(UserInfoEntity userInfoEntity) {
        authSetAndInsDao.saveOrUpdate(userInfoEntity);
        return true;
    }

    @Override
    public List getAllUsername() {
        return authSetAndInsDao.getCurrentSession().createSQLQuery("select UserName  FROM userInfo ")
                .list();
    }

    @Override
    public List getInfoByUsername(String username) {
        return authSetAndInsDao.getCurrentSession().createSQLQuery("select *  FROM userInfo where UserName like '"+ username+"'")
                .addEntity(UserInfoEntity.class).list();
    }

    @Override
    public List getInfo(String originalcode)
    {
        return authSetAndInsDao.getCurrentSession().createSQLQuery("select *  FROM userInfo where userInfo.OriginalCode like '"+originalcode+"'")
                .addEntity(UserInfoEntity.class).list();
    }

    @Override
    public void deleteCus(String ori) {
        List<UserInfoEntity> list=this.getInfo(ori);
        UserInfoEntity userInfoEntity=list.get(0);
        authSetAndInsDao.delete(userInfoEntity);
    }
}
