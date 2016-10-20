package ir.oveissi.searchmovies.di.common;


import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.oveissi.searchmovies.interactors.remote.ApiInterface;
import ir.oveissi.searchmovies.interactors.remote.SearchMoviesApiService;
import ir.oveissi.searchmovies.interactors.remote.SearchMoviesApiServiceImpl;
import okhttp3.OkHttpClient;
import retrofit2.BaseUrl;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Abbas on 5/17/16.
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    public SearchMoviesApiService provideSearchMoviesApiService(Retrofit retrofit) {
        return new SearchMoviesApiServiceImpl(retrofit.create(ApiInterface.class));
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(BaseUrl baseUrl, Converter.Factory converterFactory, CallAdapter.Factory callAdapterFactory, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(okHttpClient)
                .build();
    }


}
