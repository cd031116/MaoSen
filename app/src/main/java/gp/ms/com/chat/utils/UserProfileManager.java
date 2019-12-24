package gp.ms.com.chat.utils;

import android.content.Context;
import android.text.TextUtils;

import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.ArrayList;
import java.util.List;

import gp.ms.com.chat.ChatHelper;

public class UserProfileManager {
    /**
     * application context
     */
    protected Context appContext = null;

    /**
     * init flag: test if the sdk has been inited before, we don't need to init
     * again
     */
    private boolean sdkInited = false;

    /**
     * HuanXin sync contact nick and avatar listener
     */
    private List<ChatHelper.DataSyncListener> syncContactInfosListeners;

    private boolean isSyncingContactInfosWithServer = false;

    private EaseUser currentUser;

    public UserProfileManager() {
    }

    public synchronized boolean init(Context context) {
        if (sdkInited) {
            return true;
        }
        syncContactInfosListeners = new ArrayList<ChatHelper.DataSyncListener>();
        sdkInited = true;
        return true;
    }

    public void addSyncContactInfoListener(ChatHelper.DataSyncListener listener) {
        if (listener == null) {
            return;
        }
        if (!syncContactInfosListeners.contains(listener)) {
            syncContactInfosListeners.add(listener);
        }
    }

    public void removeSyncContactInfoListener(ChatHelper.DataSyncListener listener) {
        if (listener == null) {
            return;
        }
        if (syncContactInfosListeners.contains(listener)) {
            syncContactInfosListeners.remove(listener);
        }
    }

    public void asyncFetchContactInfosFromServer(List<String> usernames, final EMValueCallBack<List<EaseUser>> callback) {
//        if (isSyncingContactInfosWithServer) {
//            return;
//        }
//        isSyncingContactInfosWithServer = true;
//        ParseManager.getInstance().getContactInfos(usernames, new EMValueCallBack<List<EaseUser>>() {
//
//            @Override
//            public void onSuccess(List<EaseUser> value) {
//                isSyncingContactInfosWithServer = false;
//                // in case that logout already before server returns,we should
//                // return immediately
//                if (!ChatHelper.getInstance().isLoggedIn()) {
//                    return;
//                }
//                if (callback != null) {
//                    callback.onSuccess(value);
//                }
//            }
//
//            @Override
//            public void onError(int error, String errorMsg) {
//                isSyncingContactInfosWithServer = false;
//                if (callback != null) {
//                    callback.onError(error, errorMsg);
//                }
//            }
//
//        });

    }

    public void notifyContactInfosSyncListener(boolean success) {
        for (ChatHelper.DataSyncListener listener : syncContactInfosListeners) {
            listener.onSyncComplete(success);
        }
    }

    public boolean isSyncingContactInfoWithServer() {
        return isSyncingContactInfosWithServer;
    }

    public synchronized void reset() {
        isSyncingContactInfosWithServer = false;
        currentUser = null;
        PreferenceManager.getInstance().removeCurrentUserInfo();
    }

    public synchronized EaseUser getCurrentUserInfo() {
        if (currentUser == null) {
            String username = EMClient.getInstance().getCurrentUser();
            currentUser = new EaseUser(username);
            String nick = getCurrentUserNick();
            currentUser.setNickname((nick != null) ? nick : username);
            currentUser.setAvatar(getCurrentUserAvatar());
        }
        return currentUser;
    }

    public void updateCurrentUserNickName(final String nickname) {
        if (!TextUtils.isEmpty(nickname)) {
            setCurrentUserNick(nickname);
        }
    }

    public String uploadUserAvatar(String avatarUrl) {
        if (avatarUrl != null) {
            setCurrentUserAvatar(avatarUrl);
        }
        return avatarUrl;
    }


    private void setCurrentUserNick(String nickname) {
        getCurrentUserInfo().setNickname(nickname);
        PreferenceManager.getInstance().setCurrentUserNick(nickname);
    }

    private void setCurrentUserAvatar(String avatar) {
        getCurrentUserInfo().setAvatar(avatar);
        PreferenceManager.getInstance().setCurrentUserAvatar(avatar);
    }

    private String getCurrentUserNick() {
        return PreferenceManager.getInstance().getCurrentUserNick();
    }

    private String getCurrentUserAvatar() {
        return PreferenceManager.getInstance().getCurrentUserAvatar();
    }

}
