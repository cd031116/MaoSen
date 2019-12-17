package gp.ms.com.base;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.support.v4.content.LocalBroadcastManager;

import com.baidu.mapapi.SDKInitializer;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.HashMap;
import java.util.Map;
import gp.ms.com.BuildConfig;
import gp.ms.com.http.RxHttpUtils;
import gp.ms.com.http.config.OkHttpConfig;
import gp.ms.com.http.cookie.store.SPCookieStore;
import okhttp3.OkHttpClient;

public class MyApp  extends Application {
    private Map<String, Object> headerMaps = new HashMap<>();
    public static MyApp instances;
    @Override
    public void onCreate() {
        super.onCreate();
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //use default options if options is null
        if (EaseUI.getInstance().init(this, options)) {
            //debug mode, you'd better set it to false, if you want release your App officially.
            EMClient.getInstance().setDebugMode(true);

//            broadcastManager = LocalBroadcastManager.getInstance(appContext);
        }
        SDKInitializer.initialize(getApplicationContext());//百度地图
        instances = this;
        initCustomRxHttpUtils();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        if (!BuildConfig.DEBUG) {
            CrashReport.initCrashReport(getApplicationContext(), "2c11a01bf4", false);
        }
    }

    


    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        instances = null;
        super.onTerminate();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level >= ComponentCallbacks2.TRIM_MEMORY_BACKGROUND) {
            //重启应用
            Intent intent = instances.getPackageManager().getLaunchIntentForPackage(instances.getPackageName());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /**
     * 全局请求的统一配置（以下配置根据自身情况选择性的配置即可）
     */
    private void initCustomRxHttpUtils() {
        OkHttpClient okHttpClient = new OkHttpConfig
                .Builder(this)
                //全局的请求头信息
                .setHeaders(headerMaps)
                .setCacheMaxSize(1024*1024*50)
                //开启缓存策略(默认false)
                //1、在有网络的时候，先去读缓存，缓存时间到了，再去访问网络获取数据；
                //2、在没有网络的时候，去读缓存中的数据。
                .setCache(true)
                //全局持久话cookie,保存到内存（new MemoryCookieStore()）或者保存到本地（new SPCookieStore(this)）
                //不设置的话，默认不对cookie做处理
                .setCookieType(new SPCookieStore(this))
                //可以添加自己的拦截器(比如使用自己熟悉三方的缓存库等等)
                .setAddInterceptor()
                //全局ssl证书认证
                //1、信任所有证书,不安全有风险（默认信任所有证书）
                //.setSslSocketFactory()
                //2、使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(cerInputStream)
                //3、使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(bksInputStream,"123456",cerInputStream)
                //全局超时配置
                .setReadTimeout(10)
                //全局超时配置
                .setWriteTimeout(10)
                //全局超时配置
                .setConnectTimeout(10)
                //全局是否打开请求log日志
                .setDebug(true)
                .build();

        RxHttpUtils
                .getInstance()
                .init(this)
                .config()
                //自定义factory的用法
//                .setCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .setConverterFactory(ScalarsConverterFactory.create(),GsonConverterFactory.create(GsonAdapter.buildGson()))
                //配置全局baseUrl
                .setBaseUrl(BuildConfig.BaseUrl)
                //开启全局配置
                .setOkClient(okHttpClient);
    }



    public static MyApp getInstances() {
        return instances;
    }




}
