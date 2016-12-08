package net.leeautumn.common;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

/**
 * Created by Autumn on 2016/4/19.
 */
public class EncryptOperation {

	private static String serial=getSerial();
	public static final String CHECK_SUCCESS=getmatchCode(serial).substring(0,10)+serial.substring(10,21);

	//获得matchCode(匹配号)
	//根据originalCode来获得特定的匹配号,originalCode的长度是6+6+12=24
	public static String getmatchCode(String originalCode){

		String matchCode="";
		char[] arr=originalCode.toCharArray();

		//对str里的字符进行对换,对换顺序为changeOrder数组
		char temp=arr[0];
		int[] changerOrder=new int[]{4,19,22,1,14,20,3,9,18,19,13,16};
		for(int i=0;i<changerOrder.length;i=i+2){
			temp=arr[changerOrder[i]];
			arr[changerOrder[i]]=arr[changerOrder[i+1]];
			arr[changerOrder[i+1]]=temp;
		}

		//md5加密
		matchCode=md5(new String(arr));

		//生成首部随机字符
		String hex=matchCode.substring(10,30);
		String hexAfterMd5=md5(hex);
		int lengthInHexAfterMd5=(int)((Integer.parseInt(matchCode.substring(10,11),16)+1)*1.5);
		String hexSplit=hexAfterMd5.substring(2,6+lengthInHexAfterMd5);

		matchCode=hexSplit+matchCode;

		return matchCode;
	}

	public static char writeDisturbCode(){
		int random= (int) (48+Math.random()*122);
		return (char)random;
	}

	//md5加密
	public static String md5(String prime){
		char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		String encryption="";
		char[] arr=prime.toCharArray();
		try {
			MessageDigest md5=MessageDigest.getInstance("MD5");
			md5.update(new String(arr).getBytes());
			byte[] md=md5.digest();
			char[] encryption_char=new char[md.length*2];
			int k=0;
			for (int i = 0; i < md.length; i++) {
				byte byte0 = md[i];
				encryption_char[k++] = hexDigits[byte0 >>> 4 & 0xf];
				encryption_char[k++] = hexDigits[byte0 & 0xf];
			}
			encryption=new String(encryption_char);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encryption;
	}


	//对osName+osVersion+jvmVersion+hostName进行加密
	public static String getEncryption(String str){
		char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		String encryption=null;
		char[] arr=str.toCharArray();

		//对str里的字符进行对换,对换顺序为changeOrder数组
		char temp=arr[0];
		int[] changerOrder=new int[]{3,2,7,6,9,5,10,6,2,8};
		for(int i=0;i<changerOrder.length;i=i+2){
			temp=arr[changerOrder[i]];
			arr[changerOrder[i]]=arr[changerOrder[i+1]];
			arr[changerOrder[i+1]]=temp;
		}

		//md5加密
		encryption=md5(new String(arr));
		return encryption;
	}


	//获得cpuID,如果无法获得就返回'wrongwrongwrongw'
	public static String getCpuId(){
		Properties pro = System.getProperties();
		String osName = pro.getProperty("os.name");
		String result="wrongwrongwrongw";

		if (osName.toLowerCase().contains("linux")) {
			try {
				Process p = Runtime.getRuntime().exec("dmidecode -t processor | grep ID");
				BufferedInputStream bufferIn = new BufferedInputStream(p.getInputStream());
				BufferedReader bufferReader = new BufferedReader(new InputStreamReader(bufferIn));
				System.out.println("Result is ::");
				while ((result = bufferReader.readLine()) != null) {
					if (result.contains("ID")) {
						result = result.replaceAll("ID", "");
						result = result.replaceAll(":", "");
						result = result.replaceAll(" ", "");
						return result.trim();
					}
				}
				if (p.waitFor() != 0) {
					if (p.exitValue() == 1) {
						System.out.println("execute fail.");
					}
				}
				bufferReader.close();
				bufferIn.close();
			} catch (IOException e) {
				System.out.println("IO exception.");
			} catch (InterruptedException e) {
				System.out.println("execute fail.");
			}
		}
		else if(osName.toLowerCase().contains("windows")){
			try {
				Process p = Runtime.getRuntime().exec("wmic CPU get ProcessorID");
				BufferedInputStream bufferIn = new BufferedInputStream(p.getInputStream());
				BufferedReader bufferReader = new BufferedReader(new InputStreamReader(bufferIn));
				while ((result = bufferReader.readLine()) != null) {
					if(result.trim().length()==16) {
						result = result.replaceAll(" ", "");
						return result.trim();
					}
				}
				if (p.waitFor() != 0) {
					if (p.exitValue() == 1) {
						System.out.println("execute fail.");
					}
				}
				bufferReader.close();
				bufferIn.close();
			} catch (IOException e) {
				System.out.println("IO exception.");
			} catch (InterruptedException e) {
				System.out.println("execute fail.");
			}
		}
		else{
			result="wrongwrongwrongw";
		}
		return result;
	}

	//接下去把mac地址和cpuid插进去,如果cpuid没有获取到就用对wrongwrongwrongw加密
	//mac地址和cpuid都要加密后插入primeEncryption,设置几组插入次序,用随机数取模后再确定用第几组插入,再把插入顺序的数组编号加到第三个char后面.
	public static String combineParams(String primeEncryption,String cpuID,String macAddress) {
		//确定几组交换位置的数组(有点麻烦,弃用)
		//int[] orders1={11,23,31,25,9,10,30,21,35,17,5,8};
		//int[] orders2={12,21,11,22,19,1,3,21,39,40,25,19};
		//int[] orders3={10,13,22,10,19,13,31,24,25,18,15,31};

		macAddress = macAddress.replaceAll(":", "");
		String encryptedCpuID = getEncryption(cpuID);
		String primeSerial = primeEncryption.substring(0, 6) + encryptedCpuID.substring(0, 6) + macAddress;

		//对primeSerial进行打乱
		char[] arr = primeSerial.toCharArray();

		char temp = arr[0];
		int[] changerOrder = new int[]{3, 2, 7, 6, 9, 5, 10, 6, 2, 8, 11, 7, 10, 17, 2, 15, 10, 14, 8, 13, 20, 9, 23, 2, 22, 18};
		for (int i = 0; i < changerOrder.length; i = i + 2) {
			temp = arr[changerOrder[i]];
			arr[changerOrder[i]] = arr[changerOrder[i + 1]];
			arr[changerOrder[i + 1]] = temp;
		}
		return new String(arr);
	}

	//获得本地的mac地址
	public static String getMacAddress(){
		byte[] mac= new byte[0];
		try {
			InetAddress ia=InetAddress.getLocalHost();
			mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		StringBuffer sb=new StringBuffer("");
		for(int i=0;i<mac.length;i++){
			if(i!=0){
				sb.append(':');
			}
			int temp=mac[i]&0xff;
			String str=Integer.toHexString(temp);
			if(str.length()==1){
				sb.append("0"+str);
			}else{
				sb.append(str);
			}
		}
		return sb.toString();
	}

	//获得服务器的当前用户名称
	public static String getUserHostName(){
		InetAddress ia= null;
		String hostName="cantGet";
		try {
			ia = InetAddress.getLocalHost();
			hostName=ia.getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return hostName;
	}

	//根据系统名字,系统版本号,jvm版本号,主机名,mac地址,cpuid进行加密
	public static String getSerial(){
		if(serial!=null){
			return serial;
		}

		Properties systenInfo=System.getProperties();
		String osName=systenInfo.getProperty("os.name");
		String osVersion=systenInfo.getProperty("os.version");
		String jvmVersion=systenInfo.getProperty("java.vm.version");

		InetAddress inetAddress= null;
		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String hostName=inetAddress.getHostName();
		String macAddress=getMacAddress().replaceAll(":","");

		String primeEncryption=getEncryption(osName+osVersion+jvmVersion+hostName);


		String cpuID=getCpuId();
		if(cpuID==null){
			cpuID="wrongwrongwrongw";
		}else if(cpuID.length()!=16){
			cpuID="wrongwrongwrongw";
		}
		if(macAddress==null){
			macAddress="00:11:22:33:44:55";
		}else if(macAddress.length()!=17){
			macAddress="00:11:22:33:44:55";
		}

		return combineParams(primeEncryption,cpuID,macAddress);

	}
}
