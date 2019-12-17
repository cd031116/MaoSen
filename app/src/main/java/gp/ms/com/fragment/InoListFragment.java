package gp.ms.com.fragment;

import android.os.Bundle;
import android.view.View;

import gp.ms.com.R;
import gp.ms.com.base.BaseFragment;

public class InoListFragment extends BaseFragment implements View.OnClickListener {

    public static InoListFragment newInstance() {
        InoListFragment fragment = new InoListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setFmView() {
        return R.layout.fm_home;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
    }

    @Override
    public void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

}
