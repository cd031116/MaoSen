package gp.ms.com.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import gp.ms.com.widget.LodingDialog;

/**
 * @author lyj
 * @description:  Fragment基类
 * @date :2019/2/28 0028 下午 3:48
 */
public abstract class BaseFragment extends Fragment {
    private LodingDialog progressDialog;
    public Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(setFmView(),container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view,savedInstanceState);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 初始化布局, 子类必须实现
     */
    public abstract int setFmView();

    /**
     * 绑定控件等
     */
    public abstract void initView(View view,Bundle savedInstanceState );

    /**
     * 初始化数据, 子类可以不实现
     */
    public abstract void initData();


    /**
     * 显示加载图标
     * @param txt
     */
    public void showAlert(String txt){
        if(!"".equals(txt)&&txt!=null){
            if(progressDialog==null){
                progressDialog=new LodingDialog(getActivity(),false);
            }
            progressDialog.show();
            progressDialog.showText(txt);
        }
    }

    /**
     * 关闭加载图标
     */
    public void dismissAlert(){
        if(progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }


}
