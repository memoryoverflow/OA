package cn.yj.user;

/**
 * <p>
 * 自定义一些key
 * </p>
 *
 * @author 永健
 * @since 2019-09-10 21:28
 */
public interface ConsVal
{
    String USER_IMG_SUFFIX = ".png";


    /**
     * 高德接口 请求成功字段
     */
    String GEO_REQ_SUCCESS_KEY = "infocode";
    String GEO_REQ_SUCCESS_VALUE = "10000";


    /**
     * <br>
     * 商品Controller
     */
    String IS_COLLECT_KEY = "isCollect";
    String IS_FOUCS_KEY = "isFoucs";
    String IS_SUPPORT_KEY = "isSupport";
    String TAKE_CODE = "takeCode";

    /**
     * <br>
     * 逻辑删除 字段默认值
     */
    String DELETED_VALUE = "0";

    String STATUS_DEFAULT_VALUE = "0";


    /**
     * KEY
     */
    String KEY_ID="id";
    String KEY_TOKEN="token";
    String KEY_USER="userInfo";


    /**
     * <br>
     * 菜单父级ID 默认为 0
     */
    Integer DEFAULT_PARENT_VAL = 0;


    /**
     * <br>
     *   超级管理员的Code
     */
    String SUPER_ADMIN_CODE = "superAdmin";
    int USER_STATUS_DEFAULT = 1;
    int DELETED_DEFAULT = 0;



    /**
     * <br>
     *
     * shiro
     *
     */
    String SHIRO_REDIS_KEY_PREIX="shiro_session_key";
}

