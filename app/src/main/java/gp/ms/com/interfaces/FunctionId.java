package gp.ms.com.interfaces;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static gp.ms.com.interfaces.FunctionId.ORDER_ID;
import static gp.ms.com.interfaces.FunctionId.SIGN_DAKA;


@IntDef({
        SIGN_DAKA,
        ORDER_ID,
})

@Retention(RetentionPolicy.SOURCE)
public @interface FunctionId {
    int SIGN_DAKA = 100;
    int ORDER_ID=101;
}
