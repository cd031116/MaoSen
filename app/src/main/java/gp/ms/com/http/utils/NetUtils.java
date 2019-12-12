package gp.ms.com.http.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import gp.ms.com.http.RxHttpUtils;


/**
 * Created by lyj
 * <p>
 * @author lyj
 *      desc    : 管理管理类
 *      version : 1.0
 * </pre
 */
public class NetUtils {
    /**
     * 判断是否有网络
     *
     * @return 返回值
     */
    public static boolean isNetworkConnected() {
        Context context = RxHttpUtils.getContext();
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isRuo() {
        Context context = RxHttpUtils.getContext();
        if (isNetworkConnected()) {
            WifiManager mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();
            int wifi = mWifiInfo.getRssi();
            if (wifi > -50 && wifi < 0) {
                return false;
            } else if (wifi > -70 && wifi < -50) {
                return false;
            } else if (wifi > -80 && wifi < -70) {
                //较弱
                return true;
            } else if (wifi > -100 && wifi < -80) {
                //微弱
                return true;
            }
        } else {
            return true;
        }
        return false;
    }
}
