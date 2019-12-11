package gp.ms.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import gp.ms.com.R;
import gp.ms.com.activity.PunchActivity;
import gp.ms.com.base.BaseFragment;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private ImageView to_sign;
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        to_sign=view.findViewById(R.id.to_sign);
        to_sign.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.to_sign:
                Intent intent=new Intent(getActivity(), PunchActivity.class);
                startActivity(intent);
                break;
        }
    }
}
