package com.carlos.capstone.iretrofit;

import android.util.Log;



import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Carlos on 18/07/2016.
 */
public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request =chain.request();
        request=request.newBuilder()
                .addHeader("User-Agent","Mozilla/5.0 (Linux; Android 6.0.1; MotoG3 Build/MPI24.107-55) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.81 Mobile Safari/537.36")
                .build();
        Log.d("Retrofit", request.headers().toString());
        Response response=chain.proceed(request);
        return response;

    }
}
