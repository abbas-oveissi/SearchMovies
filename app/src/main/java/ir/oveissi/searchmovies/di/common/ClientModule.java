package ir.oveissi.searchmovies.di.common;


import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Abbas on 5/17/16.
 */
@Module
public class ClientModule {
    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor,
                                            @Named("networkTimeoutInSeconds") int networkTimeoutInSeconds,
                                            @Named("isDebug") boolean isDebug) {

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .readTimeout(networkTimeoutInSeconds, TimeUnit.SECONDS)
                .connectTimeout(networkTimeoutInSeconds, TimeUnit.SECONDS);

        if (isDebug)
            okHttpClient.addInterceptor(loggingInterceptor);

        return okHttpClient.build();
    }
}
