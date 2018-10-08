package com.yj.oa.common.constant;

/**
 * 用户常量信息
 *
 * @author ruoyi
 */
public class UserConstants {

//    用户默认头像
public static final String AVATOR = "/img/commonuserimg.jpg";

    /**
     * 正常状态
     */
    public static final String NORMAL = "0";

    /**
     * 异常状态
     */
    public static final String EXCEPTION = "1";

    /**
     * 用户封禁状态
     */
    public static final String USER_BLOCKED = "1";

    /**
     * 角色封禁状态
     */
    public static final String ROLE_BLOCKED = "1";

    /**
     * 部门正常状态
     */
    public static final String DEPT_NORMAL = "0";

    /**
     * 用户名长度限制
     */
    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 20;

    /**
     * 手机号码是否唯一的返回结果
     */
    public final static String USER_PHONE_UNIQUE = "0";
    public final static String USER_PHONE_NOT_UNIQUE = "1";

    /**
     * 岗位名 是否唯一返回结果
     * @date 2018/9/18 19:52
     */
    public final static String POST_NAME_UNIQUE = "0";
    public final static String POST_NAME_NOT_UNIQUE = "1";



    /**
     * e-mail 是否唯一的返回结果
     */
    public final static String USER_EMAIL_UNIQUE = "0";
    public final static String USER_EMAIL_NOT_UNIQUE = "1";

    /**
     * 部门名称是否唯一的返回结果码
     */
    public final static String DEPT_NAME_UNIQUE = "0";
    public final static String DEPT_NAME_NOT_UNIQUE = "1";

    /**
     * 角色名称是否唯一的返回结果码
     */
    public final static String ROLE_NAME_UNIQUE = "0";
    public final static String ROLE_NAME_NOT_UNIQUE = "1";

    /**
     * 菜单名称是否唯一的返回结果码
     */
    public final static String MENU_NAME_UNIQUE = "0";
    public final static String MENU_NAME_NOT_UNIQUE = "1";


    /**
     * 参数键名是否唯一的返回结果码
     */
    public final static String CONFIG_KEY_UNIQUE = "0";
    public final static String CONFIG_KEY_NOT_UNIQUE = "1";

    /**
     * 密码长度限制
     */
    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 20;

    /**
     * 手机号码格式限制
     */
    public static final String MOBILE_PHONE_NUMBER_PATTERN = "^0{0,1}(13[0-9]|15[0-9]|14[0-9]|18[0-9])[0-9]{8}$";

    /**
     * 邮箱格式限制
     */
    public static final String EMAIL_PATTERN = "^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?";

    /**
     * 房间状态情况 0：空闲，1：预约中 2：使用中，3停用
     */
    public static final int MEET_ROOM_STATUS_FREE=0;
    public static final int MEET_ROOM_STATUS_APPLYING=1;
    public static final int MEET_ROOM_STATUS_USING=2;
    public static final int MEET_ROOM_STATUS_STOP=3;


    /**
     * 申请状态码 状态 0：同意 状态 1：不同意
     */
    public static final Integer APPLY_STATUS_AGREE = 1;
    public static final Integer APPLY_STATUS_DISAGREE = 2;


    /**
     * 工作日程 安排 状态常量 0未完成 1已完成
     */
    public static final Integer SCHEDULE_NO_COMPLETE=0;
    public static final Integer SCHEDULE_YES_COMPLETE=1;





    ////////  工作流的常量         /////////////
    /**
     * 代理人字符常量
     */
    public final static String AGENT = "agent";
    /**
     * 任务的发起人
     */
    public final static String INITIATOR = "initiator";
    /**
     * 任务表的表单id字段 字符窜
     * */
    public final static String FORMID = "formId";

    /**
     * 请假表单的字符窜： true:提交申请 false:放弃申请
     * */
    public final static String Leave_FLAG = "flag";
    public final static String Leave_FLAG_TRUE = "true";
    public final static String Leave_FLAG_FaLSE = "false";
    /**
     * 请假表单的状态码 2:同意请假 3:拒绝
     * */
    public final static String Leave_status_GOING = "1";
    public final static String Leave_status_succe = "2";
    public final static String Leave_status_false = "3";
    public final static String Leave_FLAG_giveup = "4";






    /**
     * 工作流中的 busseniess_key ( 0:FormKey:) :代表会议申请,FormKey 表单ID + 表单Id，1：代表当前实例 是 请假流程
     */
    public final static String  BUSINESS_KEY_APPLYROOM = "0:FormKey:";
    public final static String BUSINESS_KEY_LEAVE = "1:FormKey:";


    /**
     * 打卡状态 1 异常 迟到
     */
    public final static int ATTEND_ERROR =1;

    /**
     * 工作是时间表 1位选用当前条工作是时间
     */
    public final static int WORK_TIME_USIN=0;
    public final static int WORK_TIME_FREE=1;
}
