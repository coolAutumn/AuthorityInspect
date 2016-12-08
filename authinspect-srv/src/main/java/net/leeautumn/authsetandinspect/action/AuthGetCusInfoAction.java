package net.leeautumn.authsetandinspect.action;

import net.leeautumn.authsetandinspect.model.UserInfoEntity;
import net.leeautumn.authsetandinspect.service.AuthSetService;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by coolAutumn on 3/8/16.
 */
public class AuthGetCusInfoAction extends ActionSupport
{
    @Autowired
    private AuthSetService authSetService;
    private InputStream inputStream=new ByteArrayInputStream("".getBytes());

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * 根据username获得用户信息
     * @return
     * @throws Exception
     */
    @Override
    public String execute() throws Exception {
        List<UserInfoEntity> list= authSetService.getInfoByUsername(username);
        if(list==null)
        {
            inputStream=new ByteArrayInputStream("(没有这样的用户)".getBytes());
            return SUCCESS;
        }
        UserInfoEntity userInfoEntity=list.get(0);
        String info="";
        info+=userInfoEntity.getUserName()+"&";
        info+=userInfoEntity.getUserUnit()+"&";
        info+=userInfoEntity.getOriginalCode()+"&";
        info+=userInfoEntity.getMatchCode()+"&";
        info+=userInfoEntity.getUserIp()+"&";
        info+=userInfoEntity.getUserHostName()+"&";
        info+=userInfoEntity.getUserMacAddress()+"&";
        info+=userInfoEntity.getLicenseStatus()+"&";
        info+=userInfoEntity.getStartData()+"&";
        info+=userInfoEntity.getEndData()+"&";
        info+=userInfoEntity.getEveryDayStartWorkTime()+"&";
        info+=userInfoEntity.getEveryDayEndWorkTime()+"&";
        info+=userInfoEntity.getMinutesAfterStart()+"&";
        info+=userInfoEntity.getMoudleCode()+"&";
        info+=userInfoEntity.getFieldValid();
        inputStream=new ByteArrayInputStream(info.getBytes());
        return SUCCESS;
    }
}
