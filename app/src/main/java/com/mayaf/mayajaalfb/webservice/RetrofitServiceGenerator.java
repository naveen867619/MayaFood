package com.mayaf.mayajaalfb.webservice;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceGenerator {

//    private static final String API_BASE_URL = "http://192.168.6.107/ma/";

    private static final String API_BASE_URL = "http://pmgflic.com/cp/";

    public static Retrofit retrofit;
    // For printing log
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null);
    }

    public static <S> S createServiceWithUserAndPassword(Class<S> serviceClass, String userName, String Password) {
        return createService(serviceClass, userName, Password);
    }

    public static <S> S createService(Class<S> serviceClass, String username, String password) {
   /*   if (username != null && password != null) {
            String credentials = username + ":" + password;
            final String basicAuthorization =
                    "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        }*/

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        // .header("Authorization", basicAuthorization)
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        // add logging as last interceptor
        httpClient.addInterceptor(logging);

        // Timeout handling
        OkHttpClient client = httpClient.readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(serviceClass);
    }
}
