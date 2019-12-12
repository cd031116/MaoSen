package gp.ms.com.http.utils;

import android.view.Gravity;
import android.widget.Toast;

import gp.ms.com.http.RxHttpUtils;


/**
 * Created by lyj
 *
 * @author lyj
 *         Toast工具类
 */

public class ToastUtils {

    private static Toast mToast;

    /**
     * Toast提示
     *
     * @param msg 提示内容
     */
    public static void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(RxHttpUtils.getContext(), msg, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }
}
