package net.leeautumn.common;

/**
 *�洢ϵͳ����
 *
 */
public class Constant {
	
	/**
	 * 常量名：DATA_SIZE
	 * 用�?：每页显示的数据行数
	 */
	public final static int DATA_SIZE = 2;
	
	
	/**
	 * 用�?：工程合�?jpdl.xml,png存放路径
	 */
	public final static String JPDLPNG_PATH = "xmlJpdlPng/";
	public final static String JPDLPNG_PATH_NAME = "gongchenghetong";
	public final static String JPDLPNG_PATH_GCHT = JPDLPNG_PATH  +  JPDLPNG_PATH_NAME;

	
	/**
	 * 用�?：排�?
	 */
	public final static String ORDERBY_OBJECTNAME_ASC = "1";
	public final static String ORDERBY_OBJECTNAME_DESC = "2";

	public final static String ORDERBY_OBJECTAUTHOR_ASC = "3";
	public final static String ORDERBY_OBJECTAUTHOR_DESC = "4";
	
	public final static String ORDERBY_DATETIME_ASC = "5";
	public final static String ORDERBY_DATETIME_DESC = "6";

	public final static String ORDERBY_TYPE_ASC = "7";
	public final static String ORDERBY_TYPE_DESC = "8";
	
	
	/**
	 * 用�?：文件夹固定目录
	 */
	public final static String DIR_FILE_PATH = "/upload/file/"; 		//文件共享:个人文件�?file:11)�?
	public final static String DIR_DOC_PATH = "/upload/doc/"; 		//档案(doc:6)、合�?agreement:7)、法�?rule:8)
	public final static String DIR_AGRE_PATH = "/upload/agreement/"; 	//合同
	public final static String DIR_RULE_PATH = "/upload/rule/"; 		//法规
	public final static String DIR_GGTZ_PATH = "/upload/ggtz/"; 		//公告通知ggtz 9
	public final static String DIR_FINANCE_PATH = "/upload/finance/"; //财务�?finance:12)�?
	public final static String DIR_SERVICE_PATH = "/upload/service/"; //招商服务�?service:13)�?
	public final static String DIR_PLAN_PATH = "/upload/plan/"; 		//规划建设�?plan:14)�?
	public final static String DIR_PLANP01_PATH = "/upload/plan/p01/"; 		//	工程立项审批附件(p01:21)
	public final static String DIR_PLANP02_PATH = "/upload/plan/p02/"; 		//	工程设计附件(p02:22)
	public final static String DIR_PLANP03_PATH = "/upload/plan/p03/"; 		//	工程发包附件(p03:23)
	public final static String DIR_PLANP04_PATH = "/upload/plan/p04/"; 		//	施工准备阶段工作附件(p04:24)
	public final static String DIR_PLANP05_PATH = "/upload/plan/p05/"; 		//	施工阶段进度控制附件(p05:25)
	public final static String DIR_PLANP06_PATH = "/upload/plan/p06/"; 		//	施工阶段质量控制及验收工作附�?p06:26)
	public final static String DIR_PLANP07_PATH = "/upload/plan/p07/"; 		//	质量事故处理附件(p07:27)
	public final static String DIR_PLANP08_PATH = "/upload/plan/p08/"; 		//	工程设计变更工作附件(p08:28)
	public final static String DIR_PLANP09_PATH = "/upload/plan/p09/"; 		//	项目验收流程附件(p09:29)
	public final static String DIR_HR_PATH 	= "/upload/hr/"; 		//人力资源�?hr:15)�?
	public final static String DIR_PROPERTY_PATH = "/upload/property/"; 		//物业�?property:16)
	public final static String DIR_OA_PATH 	= "/upload/oa/"; 		//办公室办事管�?oa:17)�?
	public final static String DIR_OTHER_PATH = "/upload/other/"; 	//其他上传附件(other:18)
	
	public final static String nulString = "{id:11,pId:0,name:'个人文件�?, t:'id=11',  url:'file/grwjList.action?id=11',target:'rightContent',title:'',open:false }";//默认文件�?
	public final static String nulString1 = "{id:11,pId:0,name:'共享文件�?, t:'id=11',  url:'file/grwjList1.action?id=11',target:'rightContent',title:'',open:false }";//默认文件�?
	public final static String nulString2 = "{id:6,pId:0,name:'档案管理', t:'id=6',  url:'file/grwjList.action?id=6&firstLevelDirId=6',target:'rightContent',title:'',open:false }";//默认文件�?
	public final static String nulString3 = "{id:8,pId:0,name:'法规管理', t:'id=8',  url:'file/grwjList.action?id=6&firstLevelDirId=8',target:'rightContent',title:'',open:false }";//默认文件�?
	public final static String nulString4 = "{id:7,pId:0,name:'合同管理', t:'id=7',  url:'file/grwjList.action?id=7&firstLevelDirId=7',target:'rightContent',title:'',open:false }";//默认文件�?
	
	//武大科技�?办公流程节点
	//现在办公主要�?级：
	//				�?级： 办事人员 （在系统管理中，员工级别的�?为：2�?
	//				�?级： 部门领导 （在系统管理中，员工级别的�?为：4�?
	//				�?级： 分管副�? （在系统管理中，员工级别的�?为：6�?
	//				�?级： �?�?�?（在系统管理中，员工级别的�?为：8�?
	//				�?级： �?�?�?（在系统管理中，员工级别的�?为：9�?
	//判断流程进行到哪�?��，会用到这个对应关系
	public final static char OA_STEP_ONE = '2';
	public final static char OA_STEP_TWO = '4';
	public final static char OA_STEP_THREE = '6';
	public final static char OA_STEP_FOUR = '8';//总经�?
	public final static char OA_STEP_FIVE = '9';//董事�?


	public final static String OA_GCSG_ONE = "1";//1施工进度计划
	public final static String OA_GCSG_TWO = "2";//2质量控制及验�?
	public final static String OA_GCSG_THREE = "3";//3 质量事故处理
	public final static String OA_GCSG_FOUR = "4";//4 工程设计变更报告（申请）
	public final static String OA_GCSG_FIVE = "5";//5 初检报告
	public final static String OA_GCSG_SIX = "6";//6 �?��报告
	public final static String OA_GCSG_SEVEN = "7";//7 验收报告
	public final static String OA_GCSG_EIGHT = "8";//8 整改通知�?
	public final static String OA_GCSG_NINE = "9";//9 竣工报告
	public final static String OA_GCSG_TEN= "10";//10 竣工项目移交材料
	/**
	 * 用�?：表示武大科�?��
	 */
	public final static String COMPANY_ID = "1";
	/**
	 * 类别
	 */
	public final static String SYS_LB_ONE = "0";//0档案类别
	public final static String SYS_LB_TWO = "1";//1职位类别
	public final static String SYS_LB_THREE = "2";//2 资讯类别
	public final static String SYS_LB_FIVE= "3";//3请假类型
	public final static String SYS_LB_SEVEN = "4";//4 资源内容级别
	public final static String SYS_LB_EIGHT = "5";//5 资源内容类别
	public final static String SYS_LB_NINE = "6";//6收藏类型 
	public final static String SYS_LB_TEN = "7";//7公司类型
	public final static String SYS_LB_FOUR = "8";//8 员工职位
	public final static String SYS_LB_SIX = "9";//9应聘岗位


    public final static String C = "checked";

	/**
	 * 服务器ip地址
	 */
	public final static String SERVER_IP = "192.168.205.205";


	/**
	 * session中的实体
	 */
	public final static String USER_IN_SESSION = "userEntity";//9应聘岗位

}
