package net.leeautumn.authsetandinspect.action;

import net.leeautumn.DESEncrypt.DESCoder;
import net.leeautumn.DESEncrypt.Des;
import net.leeautumn.authsetandinspect.model.UserInfoEntity;
import net.leeautumn.authsetandinspect.service.AuthInspectService;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by coolAutumn on 3/8/16.
 */
@Controller
public class AuthInspectAction implements Action , ServletRequestAware
{
    @Autowired
    AuthInspectService authInspectService;
    @Autowired
    private com.autumn.authcheck.authsetandinspect.service.AuthSetService authSetService;

    InputStream inputStream;
    private HttpServletRequest httpServletRequest;

    public InputStream getInputStream() {
        return inputStream;
    }

    private String id;
    private String username;
    private String userunit;

    private String originalcode;
    private String matchcode;
    private String userip;
    private String userhostname;
    private String usermacaddress;
    private String passedtime;



    private String currentdate;
    private String currenttime;

    private String licensestatus;

    private String moudlecode;

    public void setCurrenttime(String currenttime) {
        this.currenttime = currenttime;
    }

    public void setCurrentdate(String currentdate) {
        this.currentdate = currentdate;
    }

    public void setPassedtime(String passedtime) {
        this.passedtime = passedtime;
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

    public void setMoudlecode(String moudlecode) {
        this.moudlecode = moudlecode;
    }

    @Override
    public String execute() throws Exception {

        //如果没有传入originalcode参数,则直接返回false
        if(originalcode==null||originalcode.equals("wrongwrongwrongw")){
            inputStream = new ByteArrayInputStream("false".getBytes());
            return SUCCESS;
        }

        //获得ip
        String ip = httpServletRequest.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("http_client_ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("HTTP_X_FORWARDED_FOR");
        }
        // 如果是多级代理，那么取第一个ip为客户ip
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
        }
        userip=ip;
        usermacaddress=usermacaddress.replaceAll("-",":").toUpperCase();

        //根据硬件信息加密产生的序列号来查找DB中的记录
        List<UserInfoEntity> list=authInspectService.getInfoByOriginalcode(originalcode);
        if(list.size()==0||list==null)
        {
            System.out.println("begin to add new user.");
            //如果序列号在userInfo中没有找到,则自动添加
            if(originalcode.length()!=24){
                inputStream = new ByteArrayInputStream("false".getBytes());
                return SUCCESS;
            }
            UserInfoEntity user=new UserInfoEntity();
            user.setUserName("NewCus_wait_to_be_edited"+(authSetService.getAllUsername().size()+1));
            user.setOriginalCode(originalcode);
            user.setMatchCode(matchcode);
            user.setUserHostName(userhostname);
            user.setUserIp(userip);
            user.setUserMacAddress(usermacaddress);
            user.setFieldValid("00111110000000");

            System.out.println(user);

            if(authInspectService.saveUserInfoEntity(user))
                inputStream = new ByteArrayInputStream("true".getBytes());
            else
                inputStream = new ByteArrayInputStream("false".getBytes());
            return SUCCESS;
        }

        UserInfoEntity userInfoEntity=list.get(0);

        String fieldValid=userInfoEntity.getFieldValid();
        if(fieldValid.equals("00100000000000")){
            inputStream=new ByteArrayInputStream("false".getBytes());
            return SUCCESS;
        }

        String key=DESCoder.initKey(originalcode);


        char[] arr=fieldValid.toCharArray();
        boolean valid1=true;

        int[] params=new int[]{4,5,6,7,8,10,11,12,13,14};
        for (int i=0;i<params.length;i++)
        {
            valid1=inspectFactor(userInfoEntity,arr[params[i]-2],params[i]);
            if(!valid1) {
                inputStream=new ByteArrayInputStream((String.valueOf(params[i])).getBytes());
                break;
            }
        }

        Des d=new Des();
        //如果限制了期限
        if(valid1 && arr[8]=='1'&& arr[9]=='1'){
            inputStream = new ByteArrayInputStream(("true"+"FISE"+
                    d.strEnc(userInfoEntity.getStartData().replaceAll("-",""),"1","2",originalcode)+
                    d.strEnc(userInfoEntity.getEndData().replaceAll("-",""),"1","2",originalcode)).getBytes());
        }else if(valid1 && arr[8]=='1'){
            inputStream=new ByteArrayInputStream(("true"+"FI"+
                    d.strEnc(userInfoEntity.getStartData().replaceAll("-",""),"1","2",originalcode)).getBytes());
        }else if(valid1 && arr[9]=='1'){
            inputStream=new ByteArrayInputStream(("true"+"SE"+
                    d.strEnc(userInfoEntity.getEndData().replaceAll("-",""),"1","2",originalcode)).getBytes());
        }else if(valid1){
            inputStream=new ByteArrayInputStream("true".getBytes());
        }

        return SUCCESS;
    }

    /**
     * 验证属性值
     * @param userInfoEntity    根据id获得的一条数据库记录
     * @param valid             此字段是否需要验证
     * @param order             根据order来选择字段从而和userInfoEntity中的相应的属性值进行验证
     * @return                  boolean型,验证结果
     */
    private boolean inspectFactor(UserInfoEntity userInfoEntity,char valid,int order)
    {
        try {
            switch (order) {
                case 1:
                    if (valid=='1') {
                        if(id!=null) {
                            return userInfoEntity.getId() == Integer.valueOf(id);
                        }
                    }
                    return true;
                case 2:
                    if (valid=='1') {
                        if (username != null) {
                            System.out.println("username:"+username);
                            return username.equals(userInfoEntity.getUserName());
                        }
                    }
                    return true;
                case 3:
                    if (valid=='1') {
                        if (userunit != null) {
                            System.out.println("userunit:"+userunit);
                            return userunit.equals(userInfoEntity.getUserUnit());
                        }
                    }
                    return true;
                case 4:
                    if (valid=='1') {
                        if (originalcode!= null) {
                            System.out.println("originalcode:"+originalcode);
                            return originalcode.toLowerCase().equals(userInfoEntity.getOriginalCode().toLowerCase());
                        }
                    }
                    return true;

                case 5:
                    if (valid=='1') {
                        if (matchcode != null) {
                            System.out.println("matchcode:"+matchcode);
                            return matchcode.toLowerCase().equals(userInfoEntity.getMatchCode().toLowerCase());
                        }
                    }
                    return true;
                case 6:
                    if (valid=='1') {
                        if (userip != null) {
                            System.out.println("userip:"+userip);
                            return userip.equals(userInfoEntity.getUserIp());
                        }
                    }
                    return true;
                case 7:
                    if (valid=='1') {
                        System.out.println("test:userhostname");
                        if (userhostname != null) {
                            System.out.println("userhostname:"+userhostname);
                            return userhostname.toLowerCase().equals(userInfoEntity.getUserHostName().toLowerCase());
                        }
                    }
                    return true;
                case 8:
                    if (valid=='1') {
                        System.out.println("test:usermac");
                        if (usermacaddress != null) {
                            System.out.println("usermacaddress:"+usermacaddress);
                            return usermacaddress.toLowerCase().equals(userInfoEntity.getUserMacAddress().toLowerCase());
                        }
                    }
                    return true;
                case 9:
                    if (valid=='1') {
                        System.out.println("test:liscen");
                        if (licensestatus != null) {
                            System.out.println("licensestatus:"+licensestatus);
                            return licensestatus.toLowerCase().equals(userInfoEntity.getLicenseStatus().toLowerCase());
                        }
                    }
                    return true;
                case 10:
                    if (valid=='1') {
                        System.out.println("test:currdate");
                        if(currentdate!=null){
                            System.out.println("currentdate:"+currentdate);
                            int curr=Integer.valueOf(currentdate.replaceAll("-",""));
                            int left=Integer.valueOf(userInfoEntity.getStartData().replaceAll("-",""));
                            return (left<curr);
                        }
                    }
                    return true;
                case 11:
                    if (valid=='1') {
                        System.out.println("test:currdate");
                        if(currentdate!=null){
                            System.out.println("currentdate:"+currentdate);
                            int curr=Integer.valueOf(currentdate.replaceAll("-",""));
                            int right=Integer.valueOf(userInfoEntity.getEndData().replaceAll("-",""));
                            return (right>curr);
                        }
                    }
                    return true;
                case 12:
                    if(valid=='1'){
                        if(currenttime!=null){
                            System.out.println("currenttime:"+currenttime);
                            int curr=Integer.valueOf(currenttime.replaceAll(":",""));
                            int left=Integer.valueOf(userInfoEntity.getEveryDayStartWorkTime().replaceAll(":",""));
                            return left<curr;
                        }
                    }
                    return true;
                case 13:
                    if(valid=='1'){
                        if(currenttime!=null){
                            System.out.println("currenttime:"+currenttime);
                            int curr=Integer.valueOf(currenttime.replaceAll(":",""));
                            int right=Integer.valueOf(userInfoEntity.getEveryDayEndWorkTime().replaceAll(":",""));
                            return right>curr;
                        }
                    }
                    return true;
                case 14:
                    if(valid=='1'){
                        if(passedtime!=null){
                            System.out.println("passedtime:"+passedtime);
                            if(passedtime.equals("0"))
                                return true;
                            return (Integer.valueOf(passedtime)/60000)<Integer.valueOf(userInfoEntity.getMinutesAfterStart());
                        }
                    }
                    return true;
                case 15:
                    if (valid=='1') {
                        if (moudlecode != null) {
                            System.out.println("moudlecode:"+moudlecode);
                            return moudlecode.equals(userInfoEntity.getMoudleCode());
                        }
                    }
                    return true;
            }
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest=httpServletRequest;
    }
}
