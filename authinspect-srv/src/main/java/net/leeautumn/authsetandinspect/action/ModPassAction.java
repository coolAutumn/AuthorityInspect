package net.leeautumn.authsetandinspect.action;

import net.leeautumn.authlogin.model.RootListEntity;
import net.leeautumn.authlogin.service.AuthLoginService;
import net.leeautumn.authlogin.service.SendMailService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by coolAutumn on 3/11/16.
 */
public class ModPassAction extends ActionSupport
{
    @Autowired
    private SendMailService sendMailService;
    @Autowired
    private AuthLoginService authLoginService;
    private InputStream inputStream;
    private String security;
    private String newpass;

    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public InputStream getInputStream() {
        return inputStream;
    }


    @Override
    public String execute() throws Exception {
        ActionContext acx=ActionContext.getContext();
        String rootname=String.valueOf(acx.getSession().get("rootname"));
        System.out.println(rootname);
        if(rootname==null||rootname.equals("")||rootname.equals("null"))
        {
            inputStream=new ByteArrayInputStream("nouser".getBytes());
            return SUCCESS;
        }
        List<RootListEntity> list=authLoginService.getRootInfoByUserName(rootname);
        RootListEntity rootListEntity=list.get(0);
        System.out.println("当前登陆rootname为"+rootname);

        int rand=(int)(100000+Math.random()*1000000);
        inputStream=new ByteArrayInputStream("success".getBytes());
        sendMailService.connect();
        sendMailService.send(rootListEntity.getEmailAddress(),"管理员登陆系统更改密码","您的验证码为"+String.valueOf(rand));

        acx.getSession().put("passsec",rand+"");
        System.out.println("已将验证码"+rand+"放置到session中");
        return SUCCESS;
    }

    public String passstore()
    {
        ActionContext acx=ActionContext.getContext();
        String rootname=String.valueOf(acx.getSession().get("rootname"));
        List<RootListEntity> list=authLoginService.getRootInfoByUserName(rootname);
        RootListEntity rootListEntity=list.get(0);
        String sec=String.valueOf(acx.getSession().get("passsec"));
        System.out.println("得到session中的sec"+sec);
        if(security.equals(sec))
        {
            rootListEntity.setPassWord(newpass);
            authLoginService.saveRootListEntity(rootListEntity);
            inputStream=new ByteArrayInputStream("success".getBytes());
            return SUCCESS;
        }
        else {
            inputStream=new ByteArrayInputStream("error".getBytes());
            return SUCCESS;
        }
    }
}
