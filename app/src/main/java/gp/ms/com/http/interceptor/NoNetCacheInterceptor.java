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

public class NoNetCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        boolean connected = isNetworkConnected();
        //如果没有网络，则启用 FORCE_CACHE
//        if (!connected) {
//            request = request.newBuilder()
//                    .cacheControl(CacheControl.FORCE_CACHE)
//                    .build();
//
//            Response response = chain.proceed(request);
//
//            //没网的时候如果也没缓存的话就走网络
//            if (response.code() == 504) {
//                request = request.newBuilder()
//                        .cacheControl(CacheControl.FORCE_NETWORK)
//                        .build();
//                return chain.proceed(request);
//            }
//            return response.newBuilder()
//                    .header("Cache-Control", "public, only-if-cached, max-stale=3600")
//                    .removeHeader("Pragma")
//                    .build();
//        }
        //有网络的时候，这个拦截器不做处理，直接返回
        return chain.proceed(request);
    }

}