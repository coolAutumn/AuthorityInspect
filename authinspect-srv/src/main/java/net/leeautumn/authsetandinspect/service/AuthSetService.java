package net.leeautumn.authsetandinspect.service;

import net.leeautumn.authsetandinspect.model.UserInfoEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by coolAutumn on 3/7/16.
 */
@Transactional
public interface AuthSetService
{
    /**
     * 保存PO
     * @param userInfoEntity 被保存的PO
     * @return boolean型,是否保存成功
     */
    public boolean saveUserInfoEntity(UserInfoEntity userInfoEntity);

    /**
     * 获得数据库中的记录数量
     * @return  int型,记录数量
     */
    public List getAllUsername();

    /**
     * 根据id获得相应纪录
     * @param username    需要记录的id(id为数据库主键)
     * @return  记录的list集合(list的size为1)
     */
    public List getInfoByUsername(String username);

    public List getInfo(String originalcode);

    public void deleteCus(String ori);
}
