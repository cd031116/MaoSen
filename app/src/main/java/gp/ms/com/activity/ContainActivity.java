package gp.ms.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.support.v4.app.FragmentTransaction;


import gp.ms.com.R;
import gp.ms.com.base.BaseActivity;

public class ContainActivity extends BaseActivity {
    private RelativeLayout main_r;
    /**
     * 启动一个界面
     *
     * @param activity
     * @param clazz
     * @param args
     */
    public static void launch(Activity activity, Class<? extends Fragment> clazz, Bundle args) {
        Intent intent = new Intent(activity, ContainActivity.class);
        intent.putExtra("className", clazz.getName());
        if (args != null) {
            intent.putExtra("args", args);
        }
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String className = getIntent().getStringExtra("className");
        if (TextUtils.isEmpty(className)) {
            super.onCreate(savedInstanceState);
            finish();
            return;
        }
        Fragment fragment = null;
        try {
            Bundle values = getIntent().getBundleExtra("args");
            Class clazz  = Class.forName(className);
            fragment = (Fragment) clazz.newInstance();
            if (null!=values){
                fragment.setArguments(values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        if (fragment != null) {
            AddFragmentToThis(fragment);
        }
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_contain;
    }
    @Override
    protected void initView() {
        main_r=findViewById(R.id.main_r);
    }

    @Override
    protected void initData() {
        main_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void AddFragmentToThis(Fragment fragment) {
        FragmentTransaction transaction = this.getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fl_container, fragment);
        transaction.commitAllowingStateLoss();
    }


//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void getMsg(SignEvent event) {
//        setCardMesage(event);
//    }


    @Override
    public void finish(){
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
