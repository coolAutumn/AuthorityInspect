package net.leeautumn.common.action;

import com.alibaba.fastjson.JSON;
import net.leeautumn.common.util.DateJsonValueProcessorUtil;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Cryse on 14-1-9.
 * Email: tyk5555@hotmail.com
 */
@Component
public class GenericActionSupport extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware{
    protected Map<String, Object> mSessionMap;
    protected HttpServletRequest mServletRequest;
    protected HttpServletResponse mServletResponse;
    public File upload;
    public String uploadFileName;
    public String savePath;	//保存长传文件的路径
    //返回到前台的JSON字符串
    public String jsonString;

    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        mServletRequest = httpServletRequest;
    }

    @Override
    public void setServletResponse(HttpServletResponse httpServletResponse) {
        mServletResponse = httpServletResponse;
    }

    @Override
    public void setSession(Map<String, Object> stringObjectMap) {
        mSessionMap = stringObjectMap;
    }

    public Map<String, Object> getSessionMap() {
        return mSessionMap;
    }

    public HttpServletRequest getServletRequest() {
        return mServletRequest;
    }

    public HttpServletResponse getServletResponse() {
        return mServletResponse;
    }

    public HttpSession getHttpSession() {
        if(mServletRequest != null) {
            return mServletRequest.getSession();
        } else {
            return null;
        }
    }
    public File getUpload() {
        return upload;
    }
    public void setUpload(File upload) {
        this.upload = upload;
    }
    public String getUploadFileName() {
        return uploadFileName;
    }
    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }
    public String getSavePath() {
        return ServletActionContext.getServletContext().getRealPath("/" + savePath);
    }
    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
    /**
     * @Enclosing_Method: outJsonString
     * @Description: 将JSON返回到前台
     * @param str
     */
    public void outJsonString(String str) {
        getResponse().setContentType("text/javascript;charset=UTF-8");
        outString(str);
    }

    /**
     * @Enclosing_Method: outString
     * @Description: 将字符串返回到前台
     * @param str
     */
    public void outString(String str) {
        try {
            PrintWriter out = getResponse().getWriter();
            if(str.startsWith("[")){
                str=str.substring(1, str.length()-1);
            }
            out.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Enclosing_Method: outXMLString
     * @Description: 将XML格式的数据返回到前台
     * @param xmlStr
     */
    public void outXMLString(String xmlStr) {
        getResponse().setContentType("application/xml;charset=UTF-8");
        outString(xmlStr);
    }

    /**
     * @Enclosing_Method: getRequest
     * @Description:获取HttpServletRequest对象
     * @return
     */
    public HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    /**
     * @Enclosing_Method: getResponse
     * @Description: 获得Response对象
     * @return
     */
    public HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    /**
     * @Enclosing_Method: getSession
     * @Description: 获取Session对象
     * @return
     */
    public HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * @Enclosing_Method: getServletContext
     * @Description: 获取ServletContext对象
     * @return
     */
    public ServletContext getServletContext() {
        return ServletActionContext.getServletContext();
    }

    /**
     * @Enclosing_Method: getRealyPath
     * @Description: 获取项目路径
     * @param path
     * @return
     */
    public String getRealyPath(String path) {
        return getServletContext().getRealPath(path);
    }

    /**
     * @Enclosing_Method: Map2json
     * @Description: 将Map对象转化为JSON格式的数据
     * @param map
     * @return
     */
    public String Map2json(Map map){
        if(map!=null)
        {
            JSONArray jsonarray = JSONArray.fromObject(map);
            return jsonarray.toString();
        }


        return "";
    }

    /**
     * @Enclosing_Method: Obj2json
     * @Description: 将Object转成Json格式
     * @param obj
     * @return
     */
    public String Obj2json(Object obj){
        if(obj!=null)
        {
            JSONObject jsonObj = JSONObject.fromObject(obj);
            return jsonObj.toString();
        }


        return "";
    }

    public void writeJson(Object object) {
        try {
            String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
            ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
            ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
            ServletActionContext.getResponse().getWriter().write(json);
            ServletActionContext.getResponse().getWriter().flush();
            ServletActionContext.getResponse().getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 安卓手机
     * @param object
     */
    public void writeJsonArrayForAndroid(Object object){
        try{
            PrintWriter out = mServletResponse.getWriter();
            JSONArray jsonArray= JSONArray.fromObject(object);
            out.print(jsonArray);
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void writeJsonArray(Object object){
        try{
            PrintWriter out = mServletResponse.getWriter();

            //对日期进行了处理，把它转换成字符串
            JsonConfig jf = new JsonConfig();
            jf.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessorUtil("yyyy-MM-dd HH:mm:ss"));
            jf.registerJsonValueProcessor(Date.class, new DateJsonValueProcessorUtil("yyyy-MM-dd HH:mm:ss"));

            JSONArray jsonArray= JSONArray.fromObject(object,jf);
            out.print(jsonArray);
            out.flush();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    /**
     * 上传Excel文件
     * @return
     */
    public File uploadExcelFile() {
        Integer random = new Double(Math.random() * 10000).intValue();			//获取随机数
        String extension = uploadFileName.substring(uploadFileName.lastIndexOf("."));	//获取文件的扩展名
        String filename = new Long(System.currentTimeMillis()).toString() + "-" + random.toString() + extension;
        File excel = new File(getSavePath() + "\\" + filename);
        //保存上传的文件
        try {
            FileOutputStream outputStream = new FileOutputStream(excel);
            FileInputStream inputStream = new FileInputStream(getUpload());
            byte[] buffer = new byte[1024];
            int length = 0;
            while((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();
        } catch(IOException e) {
            return null;
        }
        return excel;
    }




    public String getBasePath()  {
        try {
            return mServletRequest.getScheme() + "://" + InetAddress.getLocalHost().getHostAddress() + ":" + mServletRequest.getServerPort();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "fuck";
        }
    }

    public void printForAjax(String s){
        try {
            PrintWriter out = mServletResponse.getWriter();
            out.print(s);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Timestamp getCurrnetDate() throws ParseException {
        //生成精度为19的日期时间
        String  time = new Timestamp(new Date().getTime()).toString().subSequence(0, 19).toString();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new Timestamp(format.parse(time).getTime());
    }
}
