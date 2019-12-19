package gp.ms.com.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import gp.ms.com.R;
import gp.ms.com.base.BaseActivity;

public class OrderListActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout top_left;
    private EditText search_edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void initView() {
        top_left=findViewById(R.id.top_left);
        search_edt=findViewById(R.id.search_edt);
        top_left.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        search_edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId,  KeyEvent event)  {
                if (actionId== EditorInfo.IME_ACTION_SEARCH ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER))
                {
                    //搜索接口




                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_left:
                OrderListActivity.this.finish();
                break;
        }
    }
}
