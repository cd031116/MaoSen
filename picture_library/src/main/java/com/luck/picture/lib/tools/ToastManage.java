package com.luck.picture.lib.tools;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @author：luck
 * @data：2018/3/28 下午4:10
 * @描述: Toast工具类
 */

public final class ToastManage {

    public static void s(Context mContext, String s) {
        Toast toast = Toast.makeText(mContext.getApplicationContext(), s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
