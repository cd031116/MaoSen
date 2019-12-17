package gp.ms.com.activity;
import android.Manifest;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import gp.ms.com.R;
import gp.ms.com.base.BaseActivity;
import gp.ms.com.fragment.ContactsFragment;
import gp.ms.com.fragment.HomeFragment;
import gp.ms.com.fragment.InoListFragment;
import gp.ms.com.fragment.MyFragment;
import gp.ms.com.utils.StatusBarUtil;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends BaseActivity implements View.OnClickListener{
    FragmentManager mFragmentMan;
    ImageButton home_ima;
    TextView home_t;
    ImageButton my_ima;
    TextView my_t;
    ImageButton msg_ima;
    TextView msg_t;

    ImageButton contacts_ima;
    TextView contacts_t;
   private int mPosition=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mFragmentMan = getSupportFragmentManager();
        home_ima=findViewById(R.id.home_ima);
        home_t=findViewById(R.id.home_t);
        my_ima=findViewById(R.id.my_ima);
        my_t=findViewById(R.id.my_t);
        msg_ima=findViewById(R.id.msg_ima);
        msg_t=findViewById(R.id.msg_t);
        contacts_ima=findViewById(R.id.contacts_ima);
        contacts_t=findViewById(R.id.contacts_t);

        home_ima.setOnClickListener(this);
        home_t.setOnClickListener(this);
        my_ima.setOnClickListener(this);
        my_t.setOnClickListener(this);
        msg_ima.setOnClickListener(this);
        msg_t.setOnClickListener(this);
        contacts_ima.setOnClickListener(this);
        contacts_t.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        selectItem(0);
        MainActivityPermissionsDispatcher.needLocantionWithPermissionCheck(this);
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
                    case 1:
                        mCurrentFragment = InoListFragment.newInstance();
                        break;
                    case 2:
                        mCurrentFragment = ContactsFragment.newInstance();
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
            case R.id.msg_ima:
            case R.id.msg_t:
                if (mPosition==1){
                    break;
                }
                selectItem(1);
                break;
            case R.id.contacts_ima:
            case R.id.contacts_t:
                if (mPosition==2){
                    break;
                }
                selectItem(2);
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
                home_t.setTextColor(Color.parseColor("#fc1645"));
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
                my_t.setTextColor(Color.parseColor("#fc1645"));
                changeFrament("bFragment",3);
                break;


        }

    }


    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public  void needLocantion() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }



}
