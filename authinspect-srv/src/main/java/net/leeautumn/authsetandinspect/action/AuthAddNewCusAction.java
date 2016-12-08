package net.leeautumn.authsetandinspect.action;

import net.leeautumn.authsetandinspect.model.UserInfoEntity;
import net.leeautumn.authsetandinspect.service.AuthSetService;
import net.leeautumn.common.EncryptOperation;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by coolAutumn on 3/7/16.
 */
@Controller
public class AuthAddNewCusAction extends ActionSupport implements ServletRequestAware
{

    private HttpServletRequest httpRequest ;
    InputStream inputStream;

    public InputStream getInputStream() {
        return inputStream;
    }

    public InputStream getInputStream1() {
        return inputStream1;
    }

    InputStream inputStream1=new ByteArrayInputStream("token".getBytes());

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Autowired
    private AuthSetService authSetService;

    private String id;
    private String username;
    private String userunit;
    private String originalcode;
    private String matchcode;
    private String userip;
    private String userhostname;
    private String usermacaddress;
    private String licensestatus;
    private String startdata;
    private String enddata;
    private String everydaystartworktime;
    private String everydayendworktime;
    private String minutesafterstart;
    private String moudlecode;
    private String fieldvalidata;
    private String newcus;

    public void setFieldvalidata(String fieldvalidata) {
        this.fieldvalidata = fieldvalidata;
    }

    public void setNewcus(String newcus) {
        this.newcus = newcus;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserunit(String userunit) {
        this.userunit = userunit;
    }

    public void setOriginalcode(String originalcode) {
        this.originalcode = originalcode;
    }

    public void setMatchcode(String matchcode) {
        this.matchcode = matchcode;
    }

    public void setUserip(String userip) {
        this.userip = userip;
    }

    public void setUserhostname(String userhostname) {
        this.userhostname = userhostname;
    }

    public void setUsermacaddress(String usermacaddress) {
        this.usermacaddress = usermacaddress;
    }

    public void setLicensestatus(String licensestatus) {
        this.licensestatus = licensestatus;
    }

    public void setStartdata(String startdata) {
        this.startdata = startdata;
    }

    public void setEnddata(String enddata) {
        this.enddata = enddata;
    }

    public void setEverydaystartworktime(String everydaystartworktime) {
        this.everydaystartworktime = everydaystartworktime;
    }

    public void setEverydayendworktime(String everydayendworktime) {
        this.everydayendworktime = everydayendworktime;
    }

    public void setMinutesafterstart(String minutesafterstart) {
        this.minutesafterstart = minutesafterstart;
    }

    public void setMoudlecode(String moudlecode) {
        this.moudlecode = moudlecode;
    }

    /**
     * 存储新用户
     * @return  action的result
     * @throws Exception
     */
    @Override
    public String execute() throws Exception
    {
        UserInfoEntity userInfoEntity=new UserInfoEntity();

        userInfoEntity.setFieldValid(fieldvalidata);

        //根据数据库中序列号是否重复来选择是否添加id
        List<UserInfoEntity> list=authSetService.getInfo(originalcode);
        if(newcus!=null) {
            if(newcus.equals("1")) {
                if (list.size() >= 1) {
                    inputStream = new ByteArrayInputStream("duplicate".getBytes());
                    return SUCCESS;
                }
            }
        }
        if (list.size()!=0)
        {
            userInfoEntity.setId(list.get(0).getId());
        }
        if(username!=null)
        {
            userInfoEntity.setUserName(username.trim());
            if(authSetService.getInfoByUsername(username).size()!=list.size()){
                userInfoEntity.setUserName("NewCus_wait_to_be_edited"+(authSetService.getAllUsername().size()+1));
            }else {

            }
        }else{
            userInfoEntity.setUserName("NewCus_wait_to_be_edited"+(authSetService.getAllUsername().size()+1));
        }
        if(userunit!=null)
        {
            userInfoEntity.setUserUnit(userunit.trim());
        }
        if(originalcode!=null)
        {
            userInfoEntity.setOriginalCode(originalcode.trim());
        }
        if(matchcode!=null)
        {
            userInfoEntity.setMatchCode(EncryptOperation.getmatchCode(originalcode.trim()));
        }
        if(userip!=null)
        {
            userInfoEntity.setUserIp(userip.trim());
        }
        if(userhostname!=null)
        {
            userInfoEntity.setUserHostName(userhostname.trim());
        }
        if(usermacaddress!=null)
        {
            userInfoEntity.setUserMacAddress(usermacaddress.trim());
        }
        if(licensestatus!=null)
        {
            userInfoEntity.setLicenseStatus(licensestatus.trim());
        }
        if(startdata!=null)
        {
            userInfoEntity.setStartData(startdata.trim());
        }
        if(enddata!=null)
        {
            userInfoEntity.setEndData(enddata.trim());
        }
        if(everydaystartworktime!=null)
        {
            userInfoEntity.setEveryDayStartWorkTime(everydaystartworktime.trim());
        }
        if(everydayendworktime!=null)
        {
            userInfoEntity.setEveryDayEndWorkTime(everydayendworktime.trim());
        }
        if(minutesafterstart!=null)
        {
            userInfoEntity.setMinutesAfterStart(minutesafterstart.trim());
        }
        if(moudlecode!=null)
        {
            userInfoEntity.setMoudleCode(moudlecode.trim());
        }
        if(authSetService.saveUserInfoEntity(userInfoEntity)) {
            inputStream=new ByteArrayInputStream("success".getBytes());
            return SUCCESS;
        }
        else {
            inputStream=new ByteArrayInputStream("inner".getBytes());
            return SUCCESS;
        }
    }

    @Override
    public void setServletRequest(javax.servlet.http.HttpServletRequest httpServletRequest) {
        this.httpRequest=httpServletRequest;
    }

    /**
     * (已废弃)
     * 根据传进的str返回0(代表无效)和1(代表有效)
     * @param str   表单中复选框的值
     * @return 0和1
     */
    private String checkFieldValidata(String str)
    {
        if(str==null)
        {
            return "0";
        }
        else{
            return "1";
        }
    }
}
