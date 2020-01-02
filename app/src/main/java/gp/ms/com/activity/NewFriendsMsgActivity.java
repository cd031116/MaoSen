package gp.ms.com.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

import gp.ms.com.R;
import gp.ms.com.adapter.NewFriendsMsgAdapter;
import gp.ms.com.chat.db.InviteMessgeDao;
import gp.ms.com.chat.domian.InviteMessage;
import gp.ms.com.chat.ui.ChatBaseActivity;
/**
 *  新的朋友邀请消息页面
 * */
public class NewFriendsMsgActivity extends ChatBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friends_msg);

        ListView listView = (ListView) findViewById(R.id.list);
        InviteMessgeDao dao = new InviteMessgeDao(this);
        List<InviteMessage> msgs = dao.getMessagesList();
        Collections.reverse(msgs);

        NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(this, 1, msgs);
        listView.setAdapter(adapter);
        dao.saveUnreadMessageCount(0);
    }

    public void back(View view) {
        finish();
    }




}
