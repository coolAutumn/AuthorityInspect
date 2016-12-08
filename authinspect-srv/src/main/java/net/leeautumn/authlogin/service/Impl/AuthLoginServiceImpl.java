package net.leeautumn.authlogin.service.Impl;

import com.autumn.authcheck.authlogin.dao.AuthLoginDao;
import com.autumn.authcheck.authlogin.model.RootListEntity;
import com.autumn.authcheck.authlogin.service.AuthLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.List;

/**
 * Created by coolAutumn on 3/10/16.
 */
@Named(value = "authLoginService")
@Component
@Transactional
public class AuthLoginServiceImpl implements AuthLoginService {

    @Autowired
    AuthLoginDao authLoginDao;

    @Override
    public List getRootInfoByUserName(String username) {
        return authLoginDao.getCurrentSession().createSQLQuery("SELECT * FROM RootList WHERE UserName LIKE '"+username+"'")
                .addEntity(RootListEntity.class).list();

    }

    @Override
    public boolean saveRootListEntity(RootListEntity rootListEntity) {
        authLoginDao.saveOrUpdate(rootListEntity);
        return true;
    }
}
