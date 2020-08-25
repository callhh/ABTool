package com.callhh.nn.util;


/**
 * 常量工具类
 */
public class ConstUtils {

    /**
     * 统一的日志标签key
     */
    public static String TAG_LOG = "callhh";
    /**
     * h5页面url链接和标题
     */
    public static final String WEB_URL = "WEB_URL";
    public static final String WEB_TITLE = "TITLE";
    /**
     * 系统类型码：查询=1；抵离=2；交通=3，礼宾=4;青运村=5
     */
    public static final int SYSTEM_CODE_QUERY = 1;
    public static final int SYSTEM_CODE_DL = 2;
    public static final int SYSTEM_CODE_TRAFFIC = 3;
    public static final int SYSTEM_CODE_LB = 4;
    public static final int SYSTEM_CODE_QYC = 5;
    /**
     * 临时的图片文件名
     */
    public final static String KEY_TEMP_FILE_NAME = "tempFileName";
    /**
     * SP缓存的key标签
     */
    public final static String KEY_IS_LOGIN = "is_login";
    public final static String KEY_OPENVALUE = "KEY_OPENVALUE";
    public final static String KEY_USER_ID = "user_id";
    public final static String KEY_USER_TOKEN = "user_token";
    public final static String KEY_USER_AVATAR = "user_avatar";
    public final static String KEY_USER_PHONE = "user_phone";
    public final static String KEY_USER_ACCOUNT = "user_account";
    public final static String KEY_USER_LOGIN_ACCOUNT = "user_login_account";
    public final static String KEY_USER_NAME = "user_name";
    public final static String KEY_SEARCH_HISTORY = "search_history";
    public static final String KEY_HOMEPAGE_DATA = "homepage_data";
    public final static String KEY_HOME_MENU_DATA = "home_menu";
    public static final String KEY_COMMON_NATIONALITY_DATA = "nationality_data";
    public static final String KEY_COMMON_DICTIONARY_DATA = "dictionary_data";
    public final static String KEY_IS_FINISH = "KEY_IS_FINISH";
    public final static String KEY_HOST_TYPE_INDEX = "host_type_index";//切换测试环境的下标，默认0
    /**
     * 选择头像相关常量
     */
    public static final int TAKE_PICTURE = 0;
    public static final int CHOOSE_PICTURE = 1;
    public static final int CROP = 2;
    public static final int CROP_PICTURE = 3;
    /**
     * 照片缩小比例
     */
    public static final int SCALE = 5;

    /**
     * 颜色值
     */
    public static final int COLOR_APP_MAIN = 0xffDE2E3A;
    public static final int COLOR_TRANS = 0x00000000;
    public static final int REQUEST_CODE_PHOTO_PREVIEW = 10;
    public static final int REQUEST_CODE_CHOOSE_PHOTO = 11;
    public static final int ReqCode_1 = 1; //登录页回调
    public static final int ReqCode_100 = 100;
    public static final int ReqCode_101 = 101;
    public static final int ReqCode_110 = 110;
    public static final int ReqCode_111 = 111;
    public static final int ReqCode_112 = 112;
    public static final int ReqCode_113 = 113;
    public static final int ReqCode_114 = 114;
    public static final int ReqCode_120 = 120;//抵达详情 -> 航班延误
    public static final int ReqCode_121 = 121;//抵离详情 -> 航班延误
    public static final int ReqCode_140 = 140; //青运村抵达回调请求码
    public static final int ReqCode_141 = 141; //青运村迎送回调请求码
    public static final int ReqCode_142 = 142; //故障维修录入

    public static final int REQUEST_CODE_SCAN_1111 = 1111;


    public static String[] HOME_TITLE_LIST = {"待办事项", "工作台", "我的"};
    /**
     * 任务列表tab title list common
     */
    public static String[] TAB_TITLE_LIST_CONC_TASK = {"全部任务", "待接收", "未完成", "已完成"};
    /**
     * 任务列表tab common stateType
     */
    public static String[] TAB_TITLE_LIST_CONC_TASK_STATE = {"", "0", "1", "2"};

    /**
     * 宾客信息页面类型：新增宾客 TYPE_GUEST_ADD=1；编辑宾客信息 TYPE_GUEST_UPDATE=2
     */
    public static final String TYPE_GUEST_ADD = "1";
    public static final String TYPE_GUEST_UPDATE = "2";
}
