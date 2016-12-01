package cn.com.oasis.base.env;

/**
 * 跳转传递参数必须使用这里的key
 * <p/>
 * 时间：2015_03_18 上午12:13
 */
public interface Key {
    String KEY_INT = "key-int";
    String KEY_STRING = "key-string";
    String KEY_BOOLEAN = "key-boolean";
    String KEY_LONG = "key-long";
    String KEY_FLOAT = "key-float";
    String KEY_DOUBLE = "key-double";
    String KEY_ID = "key-id";
    String KEY_TITLE = "key-title";
    String KEY_BEAN = "key-bean";
    String KEY_URL = "key-url";
    String KEY_OTHER = "key-other";
    String KEY_CONTENT = "key-content";
    String KEY_FUCK = "key-fuck";
    String KEY_JOB_BLOG_NUM = "key-job-blog-num";
    //礼物包用 ，我害怕冲突
    String KEY_SCENE_TYPE = "key_scene_type";
    String KEY_DIALOG_ID = "key_dialog_id";
    String KEY_PACKAGE_ID = "key_package_id";

    String KEY_LOGINOUT = "key_loginout";

    String KEY_JPUSH_BOOLEAN = "key-push-boolean";
    String KEY_JPUSH_ID = "key-jpush-id";

    String KEY_LIST = "key-list";

    String KEY_SOURCE = "key-source";
    String KEY_FRAGMENT_NAME = "key-fragment-name";

    String GET = "GET";
    String POST = "POST";
    String DELETE = "DELETE";
    String PUT = "PUT";

    /**
     * 下拉和翻页的RecylerView基类中的公用Key
     **/
    String BASE_PULL_RESYLERVIEW_TYPE = "type";
    String BASE_PULL_RESYLERVIEW_URL = "url";
    String BASE_PULL_RESYLERVIEW_CONTENT_TYPE = "Content-Type";


    String CONTENT_TYPE_JOSN = "application/json";
    String CONTENT_TYPE_XML = "application/xml";


    final String APP_SIGN_KEY = "nK!op4w9lB.alev0";
    int APP_CLIENT_TYPE = 2;
    String SingType = "sign-type=\"RSA\"";

}
