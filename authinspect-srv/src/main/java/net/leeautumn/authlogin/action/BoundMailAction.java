package net.leeautumn.authlogin.action;

import com.autumn.authcheck.authlogin.model.RootListEntity;
import com.autumn.authcheck.authlogin.service.AuthLoginService;
import com.autumn.authcheck.authlogin.service.SendMailService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by coolAutumn on 3/10/16.
 */
@Component
public class BoundMailAction implements Action
{
    @Autowired
    private SendMailService sendMailService;
    @Autowired
    private AuthLoginService authLoginService;
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    private String emailadd;
    private InputStream inputStream;
    private String security;

    public void setSecurity(String security) {
        this.security = security;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setEmailadd(String emailadd) {
        this.emailadd = emailadd;
    }

    @Override
    public String execute() throws Exception {
        System.out.println("发送验证码至:"+emailadd);
        int rand=(int)(100000+Math.random()*1000000);
        inputStream=new ByteArrayInputStream("success".getBytes());
        sendMailService.connect();
        sendMailService.send(emailadd,"管理员登陆系统绑定邮箱","您的验证码为"+String.valueOf(rand));

        ActionContext acx=ActionContext.getContext();
        acx.getSession().put("sec",rand+"");
        System.out.println("已将验证码"+rand+"放置到session中");
        return SUCCESS;
    }

    public String mailStore()
    {
        List<RootListEntity> list=authLoginService.getRootInfoByUserName(username);
        RootListEntity rootListEntity=list.get(0);
        ActionContext acx=ActionContext.getContext();
        String sec=String.valueOf(acx.getSession().get("sec"));
        System.out.println("得到session中的sec"+sec);
        if(security.equals(sec))
        {
            acx.getSession().put("rootname",username);
            rootListEntity.setEmailAddress(emailadd);
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
