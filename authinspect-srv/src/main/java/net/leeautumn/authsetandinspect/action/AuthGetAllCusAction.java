package net.leeautumn.authsetandinspect.action;

import net.leeautumn.authsetandinspect.service.AuthSetService;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by coolAutumn on 3/8/16.
 */
@Controller
public class AuthGetAllCusAction extends ActionSupport
{
    @Autowired
    AuthSetService authSetService;
    InputStream inputStream=new ByteArrayInputStream("".getBytes());

    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * 获得数据库记录数量
     * @return action的result
     * @throws Exception
     */
    @Override
    public String execute() throws Exception
    {
        String res="";
        List list=authSetService.getAllUsername();
        System.out.println(list);
        for(int i=0;i<list.size()-1;i++)
        {
            res+=(String)list.get(i)+"&";
        }
        if(list.size()!=0) {
            res += (String) list.get(list.size() - 1);
        }
        inputStream=new ByteArrayInputStream(res.getBytes());
        return SUCCESS;
    }
}
