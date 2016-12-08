package net.leeautumn.authsetandinspect.action;

import net.leeautumn.DESEncrypt.Des;
import net.leeautumn.common.EncryptOperation;
import net.leeautumn.common.action.GenericActionSupport;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by coolAutumn on 5/27/16.
 */
public class GetMatchCode extends GenericActionSupport {

    public InputStream inputStream;

    public void setOriginalcode(String originalcode) {
        this.originalcode = originalcode;
    }

    public void setStartdata(String startdata) {
        this.startdata = startdata;
    }

    public void setEnddata(String enddata) {
        this.enddata = enddata;
    }

    public String originalcode;
    public String startdata;
    public String enddata;

    @Override
    public String execute() throws Exception {
        String serial= EncryptOperation.getmatchCode(originalcode);
        Des d=new Des();

        String res=serial;
        if(startdata!=null&&startdata.length()==10){
            res+="FI"+d.strEnc(startdata.replaceAll("-",""),"1","2",originalcode);
        }
        if(enddata!=null&&enddata.length()==10){
            res+="SE"+d.strEnc(enddata.replaceAll("-",""),"1","2",originalcode);
        }
        inputStream=new ByteArrayInputStream((res).getBytes());

        return SUCCESS;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
