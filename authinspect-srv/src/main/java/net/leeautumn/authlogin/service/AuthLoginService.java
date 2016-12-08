package net.leeautumn.authlogin.service;

import com.autumn.authcheck.authlogin.model.RootListEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by coolAutumn on 3/10/16.
 */
@Transactional
public interface AuthLoginService
{
    public List getRootInfoByUserName(String username);
    public boolean saveRootListEntity(RootListEntity rootListEntity);
}
