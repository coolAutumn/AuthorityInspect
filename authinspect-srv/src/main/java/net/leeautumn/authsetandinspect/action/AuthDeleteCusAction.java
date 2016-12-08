package net.leeautumn.authsetandinspect.action;

import net.leeautumn.authsetandinspect.service.AuthSetService;
import net.leeautumn.common.action.GenericActionSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by coolAutumn on 5/30/16.
 */
public class AuthDeleteCusAction extends GenericActionSupport {

    String ori;
    public InputStream inputStream;
    @Autowired
    private AuthSetService authSetService;

    @Override
    public String execute() throws Exception {
        if(ori!=null) {
            authSetService.deleteCus(ori);
        }
        inputStream=new ByteArrayInputStream("success".getBytes());
        return SUCCESS;
    }

    public void setOri(String ori) {
        this.ori = ori;
    }
    public InputStream getInputStream() {
        return inputStream;
    }
}
