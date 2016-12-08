package net.leeautumn.authlogin.action;

import com.autumn.authcheck.authlogin.model.RootListEntity;
import com.autumn.authcheck.authlogin.service.AuthLoginService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by coolAutumn on 3/7/16.
 */
@Component
class AuthLoginAction extends ActionSupport
{
    @Autowired
    private AuthLoginService authLoginService;
    private InputStream inputStream;
//    private List<String> loggedRoot=new ArrayList<>();


    private String username;
    private String password;
    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * 登录action
     * @return action的result
     * @throws Exception
     */
    @Override
    public String execute() throws Exception {
        List<RootListEntity> list=authLoginService.getRootInfoByUserName(username);
        if(list==null)
        {
            inputStream=new ByteArrayInputStream("inner".getBytes());
            return SUCCESS;
        }
//        if(loggedRoot.contains(username))
//        {
//            inputStream=new ByteArrayInputStream("haveLogined".getBytes());
//            return SUCCESS;
//        }

        String pass=list.get(0).getPassWord();
        if(password.equals(pass))
        {
            ActionContext acx=ActionContext.getContext();
            acx.getSession().put("rootname",username);
//            loggedRoot.add(username);
            if(list.get(0).getEmailAddress()==null)
            {
                inputStream=new ByteArrayInputStream("noemail".getBytes());
                return SUCCESS;
            }
            inputStream=new ByteArrayInputStream("success".getBytes());
            return SUCCESS;
        }
        else
        {
            inputStream=new ByteArrayInputStream("inner".getBytes());
            return SUCCESS;
        }
    }
}
