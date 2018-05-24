package ir.oveissi.searchmovies.di.common;


import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.oveissi.searchmovies.interactors.remote.ApiInterface;
import ir.oveissi.searchmovies.interactors.remote.SearchMoviesApiService;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Abbas on 5/17/16.
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    public static SearchMoviesApiService provideSearchMoviesApiService(Retrofit retrofit) {
        return new SearchMoviesApiService(retrofit.create(ApiInterface.class));
    }

    @Provides
    @Singleton
    public static Retrofit provideRetrofit(@Named("BaseUrl") String baseUrl, Converter.Factory converterFactory, CallAdapter.Factory callAdapterFactory, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(okHttpClient)
                .build();
    }

    @Singleton
    @Provides
    public static Gson provideGson() {
        return new Gson();
    }


    @Provides
    @Singleton
    public static CallAdapter.Factory provideRxJavaCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    public static Converter.Factory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }
}
