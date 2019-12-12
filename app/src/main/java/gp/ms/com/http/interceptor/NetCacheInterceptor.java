package gp.ms.com.http.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static gp.ms.com.http.utils.NetUtils.isNetworkConnected;


/**
 * Created by lyj
 * <p>
 * @author lyj
 */

public class NetCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        boolean connected = isNetworkConnected();
        if (connected) {
            Response response = chain.proceed(request);
            int maxTime = 60;
            //如果有网络，缓存60s
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxTime)
                    .build();
        }
        //如果没有网络，不做处理，直接返回
        return chain.proceed(request);
    }

}
