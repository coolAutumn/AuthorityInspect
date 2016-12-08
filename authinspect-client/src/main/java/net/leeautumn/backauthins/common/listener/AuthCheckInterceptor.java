package net.leeautumn.backauthins.common.listener;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import org.apache.struts2.ServletActionContext;
import util.DESEncrypt.Des;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by coolAutumn on 3/31/16.
 */
public class AuthCheckInterceptor extends MethodFilterInterceptor{

    private static String serial=null;
    private static String checksuccess=null;
    private static Des d=null;

    @Override
    protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
        // initialize
        if(d==null){
            d=new Des();
        }
        ServletActionContext.getServletContext().setAttribute("authfilter","p_a_s_s");
        String str=null;
        File file=new File("DoNotTouch.txt");
        if(!file.exists()){
            file.createNewFile();
            return "authIllegal";
        }else {
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            if(serial==null){
                serial=EncryptOperation.getSerial();
            }
            if(checksuccess==null){
                checksuccess=EncryptOperation.md5(EncryptOperation.CHECK_SUCCESS);
            }
            if((str=bufferedReader.readLine()).contains(checksuccess)){
                //如果还有期限限制
                if((str=str.replace(EncryptOperation.md5(EncryptOperation.CHECK_SUCCESS),"")).length()!=0){
                    if(str.length()==34){
                        if(str.indexOf("FI")==0){
                            String start=d.strDec(str.substring(2,34),"1","2",EncryptOperation.getSerial());
                            if(start.compareTo(BackstageAuthChkListener.getCurrentDate().replaceAll("-",""))>=0){
                                return "authIllegal";
                            }
                        }else if(str.indexOf("SE")==0){
                            String end=d.strDec(str.substring(2,34),"1","2",EncryptOperation.getSerial());
                            if(end.compareTo(BackstageAuthChkListener.getCurrentDate().replaceAll("-",""))<=0){
                                return "authIllegal";
                            }
                        }
                    }else if(str.length()==68){
                        String start=d.strDec(str.substring(4,36),"1","2",EncryptOperation.getSerial());
                        String end=d.strDec(str.substring(36,68),"1","2",EncryptOperation.getSerial());
                        if(start.compareTo(BackstageAuthChkListener.getCurrentDate().replaceAll("-",""))>=0||
                                end.compareTo(BackstageAuthChkListener.getCurrentDate().replaceAll("-",""))<=0){
                            return "authIllegal";
                        }
                    }
                }
                System.out.println("succ");
                return actionInvocation.invoke();
            }else{
                ServletActionContext.getServletContext().setAttribute("serial",serial);
                return "authIllegal";
            }
        }
    }
}

