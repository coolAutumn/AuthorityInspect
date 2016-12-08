package net.leeautumn.backauthins.common.listener;

import net.leeautumn.util.DESEncrypt.Des;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Autumn on 2016/4/14.
 */
public class BackstageAuthChkListener implements ServletContextListener{

	Timer time;
	File file;
	ServletContextEvent servletContextEvent;
	//需要发送到云服务器的参数有
	//currentData  : String 当前日期,格式为yyyy-mm-dd
	//currentTime  : String 当前时间,格式为hh:mm
	//originalCode : String 根据硬件信息来获取一个唯一的序列号
	//matchCode    : string 序列号进到算法后出来的一个字符串(算法需要被混淆)
	//macAddress   : String 当前机器的mac地址
	//userHostName : String 机器主机名
	//passedTime   : String 自服务开始已经运行的时间
	String currentDate=null;
	String currentTime=null;
	String originalCode=null;
	String matchCode=null;
	String userMacAddress=null;
	String userHostName=null;
	String passedTime=null;
	String serial=null;
	long startTime=0;
	boolean netNotAvailable=false;
	boolean goToCheck=false;
	Des d=null;
	public static boolean checkPass=false;


	public void contextDestroyed(ServletContextEvent arg0) {
		if(time!=null) {
			time.cancel();
		}
	}

	public class Task extends TimerTask {

		public void run() {
			//发送请求获得结果
			//初始化序列号和匹配码
			serial=EncryptOperation.getSerial();
			originalCode=serial;
			matchCode=EncryptOperation.getmatchCode(originalCode);
			File file=new File("DoNotTouch.txt");

			System.out.println("直接开始本地验证");
			String checksuccess = null;
			String str = null;
			BufferedReader bufferedReader = null;
			try {
				bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			if (checksuccess == null) {
				checksuccess = EncryptOperation.md5(EncryptOperation.CHECK_SUCCESS);
			}
			try {
				if((str = bufferedReader.readLine())==null){
					str="false";
				}
				else if (str.contains(checksuccess)) {
                    //如果还有期限限制
					if ((str = str.replace(EncryptOperation.md5(EncryptOperation.CHECK_SUCCESS), "")).length() != 0) {
                        if (str.length() == 34) {
                            if (str.indexOf("FI") == 0) {
                                String start = d.strDec(str.substring(2, 34), "1", "2", EncryptOperation.getSerial());
                                if (start.compareTo(getCurrentDate().replaceAll("-", "")) >= 0) {
                                    System.out.println("网未断并且本地验证已通过,直接本地验证不通过 start");
                                    try {
                                        writeAuthResult("false",file);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    checkPass=false;
                                    return;
                                }
                            } else if (str.indexOf("SE") == 0) {
                                String end = d.strDec(str.substring(2, 34), "1", "2", EncryptOperation.getSerial());
                                if (end.compareTo(getCurrentDate().replaceAll("-", "")) <= 0) {
                                    System.out.println("网未断并且本地验证已通过,直接本地验证不通过 end");
                                    try {
                                        writeAuthResult("false",file);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    checkPass=false;
                                    return;
                                }
                            }
                        } else if (str.length() == 68) {
                            String start = d.strDec(str.substring(4, 36), "1", "2", EncryptOperation.getSerial());
                            String end = d.strDec(str.substring(36, 68), "1", "2", EncryptOperation.getSerial());
                            if (start.compareTo(getCurrentDate().replaceAll("-", "")) >= 0 ||
                                    end.compareTo(getCurrentDate().replaceAll("-", "")) <= 0) {
                                System.out.println("网未断并且本地验证已通过,直接本地验证不通过 se");
                                try {
                                    writeAuthResult("false",file);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                checkPass=false;
                                return;
                            }
                        }
                    }
                    System.out.println("本地验证已通过");
                    return;
                }else{
                    System.out.println("本地验证不通过");
                    checkPass=false;
                }
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				//检查网络状态
				checkNetSituation();
				if(!netNotAvailable){
					// initialize
					if(d==null){
						d=new Des();
					}
					//可以到达云验证服务器,开始验证
					goToCheck=false;
					try {
						System.out.println("网未断并且开始云端验证");
						String result=authInspect(new String[]{currentDate,currentTime,originalCode,matchCode,userMacAddress,userHostName,passedTime});
						System.out.println("server return:"+result);
						//根据result的值返回不同结果
						if(result.contains("true")){
							//return "authLegal";
							//允许继续使用
							//请求结果写在文件中供拦截器提取
							try {
								System.out.println("网未断并且开始云端验证通过");
								//将权限检测结果写入文件中
								writeAuthResult(result,file);
							} catch (IOException e) {
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
						}else{
							//return "authIllegal";
							//直接停止服务
							try {
								//将权限检测结果写入文件中
								System.out.println("网未断并且开始云端验证未通过");
								writeAuthResult("false",file);
							} catch (IOException e) {
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
						}
					} catch (Exception e) {
//						System.out.println("BackCheck失败");
					}
				}else if(!goToCheck&&!checkPass){
					//无法连接,返回到特定验证页面
					//向application中加入serial,并在特定页面上显示
					goToCheck=true;
					writeAuthResult("false",file);

					System.out.println("网断并且需要去本地验证");
					String originalCode=EncryptOperation.getSerial();
					servletContextEvent.getServletContext().setAttribute("serial",originalCode);
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public void checkNetSituation() throws IOException {
		//云服务器IP address
		InetAddress inetAddress = InetAddress.getByName("120.24.64.87");
		if(inetAddress.isReachable(5000)){
			netNotAvailable=false;
		}else{
			netNotAvailable=true;
		}
	}

	//将权限检测结果写到二进制文件中
	public static void writeAuthResult(String result,File file) throws IOException, ClassNotFoundException {

		clearTxtFile(file);

		FileOutputStream fileOutputStream=new FileOutputStream(file);
		if(result.contains("true")){
			String resultAfterMd5=EncryptOperation.md5(EncryptOperation.CHECK_SUCCESS);
			fileOutputStream.write((resultAfterMd5+result.replaceAll("true","")).getBytes());
		}else{
			String resultAfterMd5=EncryptOperation.md5("2016/4/19fail");
			fileOutputStream.write(resultAfterMd5.getBytes());
		}
	}

	//清空文件
	public static void clearTxtFile(File file) throws IOException {
		FileWriter fileWriter=new FileWriter(file);
		fileWriter.write("");
		fileWriter.close();
	}

	public void contextInitialized(ServletContextEvent arg0) {

		arg0.getServletContext().setAttribute("ListenerStart","p_a_s_s");
		arg0.getServletContext().setAttribute("serial",EncryptOperation.getSerial());
		servletContextEvent=arg0;
		//记录服务启动时间
		startTime=System.currentTimeMillis();
		//initialize
		currentDate=getCurrentDate();
		currentTime=getCurrentTime();
		userMacAddress=EncryptOperation.getMacAddress();
		userHostName=EncryptOperation.getUserHostName();

		//检测是否有注册信息文件,没有则创建
		file=new File("DoNotTouch.txt");
		if(!file.exists()){
			try {
				file.createNewFile();
				System.out.println("TXTFile create succeed");
				System.out.println(file.getAbsolutePath());
			} catch (IOException e) {
				System.out.println("DoNotTouch创建失败");
			}
		}

		//清空txt文件
//		try {
//			clearTxtFile(file);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		// 初始化日志处理器
		System.out.println("INFO:数据初始化完毕");
		time=new Timer();
		Task task=new Task();
		//开始后台验证,10分钟
		time.schedule(task,0,600000);
	}

	private String authInspect(String[] arr){
		int length=7;
		String currentDate=arr[0];
		String currentTime=arr[1];
		String originalCode=arr[2];
		String matchCode=arr[3];
		String userMacAddress=arr[4];
		String userHostName=arr[5];
		String passedTime=arr[6];
		passedTime=String.valueOf(System.currentTimeMillis()-startTime);

		BufferedWriter writer = null;
		BufferedReader reader = null;
		StringBuffer sb=new StringBuffer("");
		String result="";
		String url="http://120.24.64.87:8080/authmanager/authinsp";

		try {
			URL urlPath=new URL(url);
			HttpURLConnection uc=(HttpURLConnection)urlPath.openConnection();
			uc.setDoInput(true);
			uc.setDoOutput(true);
			uc.setRequestMethod("POST");
			uc.setRequestProperty("Connection","Keep-Alive");
			uc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			writer=new BufferedWriter(new OutputStreamWriter(uc.getOutputStream()));
			String params="originalcode=" + originalCode.trim() +
					"&matchcode=" + matchCode.trim() +
					"&userhostname=" + userHostName.trim() +
					"&usermacaddress=" + userMacAddress.trim() +
					"&currenttime=" + currentTime.trim() +
					"&currentdate=" + currentDate.trim() +
					"&passedtime=" + passedTime;
			writer.write(params);
			writer.flush();
			writer.close();
			reader=new BufferedReader(new InputStreamReader(uc.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null){
				sb.append(line);
			}
			result=sb.toString();
			reader.close();
		}catch (Exception e1){
			e1.printStackTrace();
			System.out.println("connect failed");
		}finally {
			closeIO(writer,reader);
		}
		return result;
	}

	//获得当前日期 格式:yyyy-mm-dd
	public static String getCurrentDate(){
		Calendar calendar=Calendar.getInstance();
		String str3=calendar.get(Calendar.MONTH)+1+"";
		String str4=calendar.get(Calendar.DAY_OF_MONTH)+"";
		if(calendar.get(Calendar.MONTH)<9){
			str3="0"+(calendar.get(Calendar.MONTH)+1);
		}
		if(calendar.get(Calendar.DAY_OF_MONTH)<=9){
			str4="0"+calendar.get(Calendar.DAY_OF_MONTH);
		}
		return calendar.get(Calendar.YEAR)+"-"+str3+"-"+str4;
	}

	//获得当前时间 格式:hh:mm
	private String getCurrentTime(){
		Calendar calendar=Calendar.getInstance();
		String str1=calendar.get(Calendar.HOUR_OF_DAY)+"";
		String str2=calendar.get(Calendar.MINUTE)+"";

		if(calendar.get(Calendar.HOUR_OF_DAY)<=9){
			str1="0"+calendar.get(Calendar.HOUR_OF_DAY);
		}
		if(calendar.get(Calendar.MINUTE)<=9){
			str2="0"+calendar.get(Calendar.MINUTE);
		}
		return str1+":"+str2;
	}




	//关闭网络连接中的IO
	private void closeIO(BufferedWriter writer,BufferedReader reader){
		if (writer != null) {
			try {
				writer.close();
				writer = null;
			} catch (Exception e) {
				return;
			}
		}
		if (reader != null) {
			try {
				reader.close();
				reader = null;
			} catch (Exception e) {
				return;
			}
		}
	}
}
