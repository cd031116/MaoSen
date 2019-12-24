package gp.ms.com.activity;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMClientListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMMultiDeviceListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.EMLog;

import java.util.List;

import gp.ms.com.R;
import gp.ms.com.base.BaseActivity;
import gp.ms.com.chat.ChatHelper;
import gp.ms.com.chat.db.InviteMessgeDao;
import gp.ms.com.fragment.ContactsFragment;
import gp.ms.com.fragment.HomeFragment;
import gp.ms.com.fragment.InoListFragment;
import gp.ms.com.fragment.MyFragment;
import gp.ms.com.utils.Constant;
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
    private TextView unreadLabel;
    private InviteMessgeDao inviteMessgeDao;
    //
    private int currentTabIndex;

    private HomeFragment homeFragment;
    private InoListFragment inoListFragment;
    private ContactsFragment contactListFragment;
    private MyFragment myFragment;
    private Fragment[] fragments;
    // user logged into another device
    public boolean isConflict = false;
    // user account was removed
    private boolean isCurrentAccountRemoved = false;
    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inviteMessgeDao = new InviteMessgeDao(this);
        registerBroadcastReceiver();
        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
        EMClient.getInstance().addClientListener(clientListener);
        EMClient.getInstance().addMultiDeviceListener(new MyMultiDeviceListener());
        if (savedInstanceState != null) {
            homeFragment = (HomeFragment) getSupportFragmentManager().getFragment(savedInstanceState, HomeFragment.class.getSimpleName());
            inoListFragment= (InoListFragment) getSupportFragmentManager().getFragment(savedInstanceState, InoListFragment.class.getSimpleName());
            contactListFragment = (ContactsFragment) getSupportFragmentManager().getFragment(savedInstanceState, ContactsFragment.class.getSimpleName());
            myFragment = (MyFragment) getSupportFragmentManager().getFragment(savedInstanceState, MyFragment.class.getSimpleName());
            fragments = new Fragment[]{homeFragment,inoListFragment, contactListFragment, myFragment};
            getSupportFragmentManager().beginTransaction()
                    .show(homeFragment)
                    .hide(inoListFragment)
                    .hide(contactListFragment)
                    .hide(myFragment)
                    .commit();
            currentTabIndex=0;
            selectItem(0);
        } else {
            homeFragment = new HomeFragment();
            inoListFragment=new InoListFragment();
            contactListFragment = new ContactsFragment();
            myFragment = new MyFragment();
            fragments = new Fragment[]{homeFragment,inoListFragment, contactListFragment, myFragment};
            getSupportFragmentManager().beginTransaction().add(R.id.fl_container, homeFragment)
                    .add(R.id.fl_container, inoListFragment).hide(inoListFragment)
                    .add(R.id.fl_container, contactListFragment).hide(contactListFragment)
                    .add(R.id.fl_container, myFragment).hide(myFragment)
                    .show(homeFragment)
                    .commitAllowingStateLoss();
            currentTabIndex=0;
            selectItem(0);
        }
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
        unreadLabel=findViewById(R.id.unread_msg_number);
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


    private void changeFrament(int index) {
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fl_container, fragments[index]);
            }
            trx.show(fragments[index]).commitAllowingStateLoss();
        }
        currentTabIndex = index;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_ima:
            case R.id.home_t:
                mPosition=0;
                selectItem(mPosition);
                break;
            case R.id.msg_ima:
            case R.id.msg_t:
                mPosition=1;
                selectItem(mPosition);
                break;
            case R.id.contacts_ima:
            case R.id.contacts_t:
                mPosition=2;
                selectItem(mPosition);
                break;
            case R.id.my_ima:
            case R.id.my_t:
                mPosition=3;
                selectItem(mPosition);
                break;
        }
        changeFrament(mPosition);
    }


    private void  selectItem(int index){
        switch (index){
            case 0:
                home_ima.setSelected(true);
                msg_ima.setSelected(false);
                contacts_ima.setSelected(false);
                my_ima.setSelected(false);
                home_t.setTextColor(Color.parseColor("#fc1645"));
                msg_t.setTextColor(Color.parseColor("#333333"));
                contacts_t.setTextColor(Color.parseColor("#333333"));
                my_t.setTextColor(Color.parseColor("#333333"));
                break;
            case 1:
                home_ima.setSelected(false);
                msg_ima.setSelected(true);
                contacts_ima.setSelected(false);
                my_ima.setSelected(false);
                home_t.setTextColor(Color.parseColor("#333333"));
                msg_t.setTextColor(Color.parseColor("#fc1645"));
                contacts_t.setTextColor(Color.parseColor("#333333"));
                my_t.setTextColor(Color.parseColor("#333333"));
                break;
            case 2:
                home_ima.setSelected(false);
                msg_ima.setSelected(false);
                contacts_ima.setSelected(true);
                my_ima.setSelected(false);
                home_t.setTextColor(Color.parseColor("#333333"));
                msg_t.setTextColor(Color.parseColor("#333333"));
                contacts_t.setTextColor(Color.parseColor("#fc1645"));
                my_t.setTextColor(Color.parseColor("#333333"));
                break;
            case 3:
                home_ima.setSelected(false);
                msg_ima.setSelected(false);
                contacts_ima.setSelected(false);
                my_ima.setSelected(true);
                home_t.setTextColor(Color.parseColor("#333333"));
                msg_t.setTextColor(Color.parseColor("#333333"));
                contacts_t.setTextColor(Color.parseColor("#333333"));
                my_t.setTextColor(Color.parseColor("#fc1645"));
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


    /**
     * update unread message count
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            unreadLabel.setText(String.valueOf(count));
            unreadLabel.setVisibility(View.VISIBLE);
        } else {
            unreadLabel.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * get unread event notification count, including application, accepted, etc
     *
     * @return
     */
    public int getUnreadAddressCountTotal() {
        int unreadAddressCountTotal = 0;
        unreadAddressCountTotal = inviteMessgeDao.getUnreadMessagesCount();
        return unreadAddressCountTotal;
    }



    public int getUnreadMsgCountTotal() {
        return EMClient.getInstance().chatManager().getUnreadMessageCount();
    }


    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message: messages) {
                ChatHelper.getInstance().getNotifier().vibrateAndPlayTone(message);
            }
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {}
    };

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
                updateUnreadLabel();
                if (mPosition == 1) {
                    // refresh conversation list
                    if (inoListFragment != null) {
                        inoListFragment.refresh();
                    }
                }
            }
        });
    }

    private void unregisterBroadcastReceiver(){
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }



    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateUnreadLabel();
                if (mPosition == 1) {
                    // refresh conversation list
                    if (inoListFragment != null) {
                        inoListFragment.refresh();
                    }
                } else if (mPosition == 2) {
                    if(contactListFragment != null) {
                        contactListFragment.refresh();
                    }
                }
                String action = intent.getAction();
                if(action.equals(Constant.ACTION_GROUP_CHANAGED)){
//                    if (EaseCommonUtils.getTopActivity(MainActivity.this).equals(GroupsActivity.class.getName())) {
//                        GroupsActivity.instance.onResume();
//                    }
                }
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }



    public class MyContactListener implements EMContactListener {
        @Override
        public void onContactAdded(String username) {}
        @Override
        public void onContactDeleted(final String username) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (ChatActivity.activityInstance != null && ChatActivity.activityInstance.toChatUsername != null &&
                            username.equals(ChatActivity.activityInstance.toChatUsername)) {
                        String st10 = getResources().getString(R.string.have_you_removed);
                        Toast.makeText(MainActivity.this, ChatActivity.activityInstance.getToChatUsername() + st10, Toast.LENGTH_LONG)
                                .show();
                        ChatActivity.activityInstance.finish();
                    }
                }
            });
//            updateUnreadAddressLable();
        }
        @Override
        public void onContactInvited(String username, String reason) {}
        @Override
        public void onFriendRequestAccepted(String username) {}
        @Override
        public void onFriendRequestDeclined(String username) {}
    }

    public class MyMultiDeviceListener implements EMMultiDeviceListener {

        @Override
        public void onContactEvent(int event, String target, String ext) {

        }

        @Override
        public void onGroupEvent(int event, String target, final List<String> username) {
            switch (event) {
                case EMMultiDeviceListener.GROUP_LEAVE:
                    ChatActivity.activityInstance.finish();
                    break;
                default:
                    break;
            }
        }
    }

    EMClientListener clientListener = new EMClientListener() {
        @Override
        public void onMigrate2x(boolean success) {
            Toast.makeText(MainActivity.this, "onUpgradeFrom 2.x to 3.x " + (success ? "success" : "fail"), Toast.LENGTH_LONG).show();
            if (success) {
                refreshUIWithMessage();
            }
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isConflict", isConflict);
        outState.putBoolean(Constant.ACCOUNT_REMOVED, isCurrentAccountRemoved);

        //save fragments
        FragmentManager fm = getSupportFragmentManager();
        for (Fragment f : fragments) {
            if (f.isAdded()) {
                fm.putFragment(outState, f.getClass().getSimpleName(), f);
            }
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isConflict && !isCurrentAccountRemoved) {
            updateUnreadLabel();
        }

        // unregister this event listener when this activity enters the
        // ChatHelper
        ChatHelper sdkHelper = ChatHelper.getInstance();
        sdkHelper.pushActivity(this);
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }





}
