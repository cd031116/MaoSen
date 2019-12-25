package gp.ms.com.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import gp.ms.com.R;
import gp.ms.com.base.BaseActivity;

public class OrderListActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout top_left;
    private TextView top_right_text;
    private EditText search_edt;
    private ImageView sImage;
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
        top_right_text=findViewById(R.id.top_right_text);
        sImage=findViewById(R.id.image);
        top_left.setOnClickListener(this);
        top_right_text.setOnClickListener(this);
        sImage.setOnClickListener(this);
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
        search_edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){//获得焦点
                    top_right_text.setVisibility(View.VISIBLE);
                    sImage.setVisibility(View.GONE);
                }else{//失去焦点
                    top_right_text.setVisibility(View.GONE);
                    sImage.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_left:
                OrderListActivity.this.finish();
                break;
            case R.id.top_right_text:
                search_edt.setText("");
                search_edt.clearFocus();
                break;
            case R.id.image:



                break;
        }
    }
}
