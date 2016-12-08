package net.leeautumn.backauthins.common.listener;

import com.opensymphony.xwork2.ActionSupport;

import java.io.File;
import java.io.IOException;

/**
 * Created by coolAutumn on 4/1/16.
 */
public class AuthorityCheckAction extends ActionSupport {

    public String getSerial() {
        return serial;
    }

    public String getMatchcode() {
        return matchcode;
    }

    public String serial="";
    public String matchcode="";

    public String check() throws IOException, ClassNotFoundException {
        File file=new File("DoNotTouch.txt");
        if(!file.exists()){
            return "authIllegal";
        }else {
            String serial=getSerial();
            if(matchcode!=null) {
                if (matchcode.contains(EncryptOperation.getmatchCode(serial))){
                    BackstageAuthChkListener.checkPass=true;
                    BackstageAuthChkListener.writeAuthResult("true"+matchcode, file);
                    return "authLegal";
                }
            }
        }
        return "authIllegal";
    }
}
