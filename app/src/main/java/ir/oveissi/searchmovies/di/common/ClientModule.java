package ir.oveissi.searchmovies.di.common;


import android.content.Context;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.oveissi.searchmovies.utils.ConnectivityInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Abbas on 5/17/16.
 */
@Module
public class ClientModule {
    @Singleton
    @Provides
    public static OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor,
                                                   Context context,
                                                   @Named("networkTimeoutInSeconds") int networkTimeoutInSeconds,
                                                   @Named("isDebug") boolean isDebug) {

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .readTimeout(networkTimeoutInSeconds, TimeUnit.SECONDS)
                .addInterceptor(new ConnectivityInterceptor(context))
                .connectTimeout(networkTimeoutInSeconds, TimeUnit.SECONDS);

        if (isDebug)
            okHttpClient.addInterceptor(loggingInterceptor);

        return okHttpClient.build();
    }

    @Singleton
    @Provides
    public static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return logging;

    }


}
