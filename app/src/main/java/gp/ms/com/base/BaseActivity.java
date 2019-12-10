package gp.ms.com.base;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import gp.ms.com.widget.LodingDialog;

public abstract class BaseActivity extends AppCompatActivity {
    private LodingDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //是否需要保持常亮
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Window window = getWindow();
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        AppManager.getAppManager().addActivity(this);
        setContentView(setLayout());
        initView();
        initData();
    }


    // 设置布局
    protected abstract int setLayout();

    // 初始化组件
    protected abstract void initView();

    // 初始化数据
    protected abstract void initData();


    /**
     * 获取当前设备状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }




    /**
     * 显示加载图标
     *
     * @param txt
     */
    public void showAlert(String txt) {
        if (this.isFinishing())
        {
            return;
        }
        if (!"".equals(txt) && txt != null) {
            if (progressDialog == null) {
                progressDialog = new LodingDialog(this,true);
            }
            progressDialog.show();
            progressDialog.showText(txt);
        }
    }

    /**
     * 关闭加载图标
     */
    public void dismissAlert() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    private String getTop() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String topAcitivty = cn.getClassName();
        return topAcitivty;
    }


    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();

    }

}
