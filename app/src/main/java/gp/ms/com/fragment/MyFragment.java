package gp.ms.com.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import gp.ms.com.R;
import gp.ms.com.base.BaseFragment;

public class MyFragment extends BaseFragment implements View.OnClickListener {
    private FrameLayout column_l;

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public int setFmView() {
        return R.layout.fragment_my;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
