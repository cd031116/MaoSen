package gp.ms.com.http.observer;

import android.text.TextUtils;


import gp.ms.com.http.base.BaseStringObserver;
import gp.ms.com.http.utils.ToastUtils;
import io.reactivex.disposables.Disposable;


/**
 * Created by lyj
 * <p>
 * @author lyj
 * <p>
 * 自定义Observer 处理string回调
 */

public abstract class StringObserver extends BaseStringObserver {

    /**
     * 失败回调
     *
     * @param errorMsg 错误信息
     */
    protected abstract void onError(String errorMsg);

    /**
     * 成功回调
     *
     * @param data 结果
     */
    protected abstract void onSuccess(String data);


    @Override
    public void doOnSubscribe(Disposable d) {
    }

    @Override
    public void doOnError(String errorMsg) {
        if (!isHideToast() && !TextUtils.isEmpty(errorMsg)) {
            ToastUtils.showToast(errorMsg);
        }
        onError(errorMsg);
    }

    @Override
    public void doOnNext(String string) {
        onSuccess(string);
    }


    @Override
    public void doOnCompleted() {
    }

}
