package gp.ms.com.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import gp.ms.com.R;
import gp.ms.com.base.BaseActivity;
import gp.ms.com.fragment.HomeFragment;
import gp.ms.com.fragment.MyFragment;
import gp.ms.com.utils.StatusBarUtil;


public class MainActivity extends BaseActivity implements View.OnClickListener{
    FragmentManager mFragmentMan;
    ImageButton home_ima;
    TextView home_t;
    ImageButton my_ima;
    TextView my_t;

   private int mPosition=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        home_ima=findViewById(R.id.home_ima);
        home_t=findViewById(R.id.home_t);
        my_ima=findViewById(R.id.my_ima);
        my_t=findViewById(R.id.my_t);
        home_ima.setOnClickListener(this);
        home_t.setOnClickListener(this);
        my_ima.setOnClickListener(this);
        my_t.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        selectItem(0);
    }


    /**
     * fragment切换 先进第二个或第三个的子模块，再返回首页
     */
    private String lastFragmentTag = null;

    private void changeFrament(String tag, int index) {
        if (mFragmentMan != null) {
            // Add default fragments to view. Try to reuse old fragments or create new ones
            FragmentTransaction transaction = mFragmentMan.beginTransaction();
            // 当前的Fragment
            Fragment mCurrentFragment = mFragmentMan.findFragmentByTag(tag);
            // 前一次Fragment
            Fragment mLastFragment = null;
            if (!TextUtils.isEmpty(lastFragmentTag) && !lastFragmentTag.equals(tag)) {
                mLastFragment = mFragmentMan.findFragmentByTag(lastFragmentTag);
            }
            if (mCurrentFragment == null){
                switch (index) {
                    case 0:
                        mCurrentFragment = HomeFragment.newInstance();
                        break;
                    case 3:
                        mCurrentFragment = MyFragment.newInstance();
                        break;
                    default:

                        break;
                }
                transaction.add(R.id.fl_container, mCurrentFragment, tag);
            } else {
                transaction.show(mCurrentFragment);
            }
            /**隐藏前一次Fragment
             * */
            if (mLastFragment != null) {
                transaction.hide(mLastFragment);
            }
            transaction.commitAllowingStateLoss();
            lastFragmentTag = tag;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_ima:
            case R.id.home_t:
                if (mPosition==0){
                    break;
                }
                selectItem(0);
                break;
            case R.id.my_ima:
            case R.id.my_t:
                if (mPosition==3){
                    break;
                }
                selectItem(3);
                break;
        }
    }


    private void  selectItem(int index){
        mPosition=index;
        switch (index){
            case 0:
                my_ima.setSelected(false);
                home_ima.setSelected(true);
                home_t.setTextColor(Color.parseColor("#d81e06"));
                my_t.setTextColor(Color.parseColor("#333333"));
                changeFrament("aFragment",0);
                break;
            case 1:

                break;
            case 2:

                break;
            case 3:
                my_ima.setSelected(true);
                home_ima.setSelected(false);
                home_t.setTextColor(Color.parseColor("#333333"));
                my_t.setTextColor(Color.parseColor("#d81e06"));
                changeFrament("bFragment",3);
                break;


        }


    }




}
