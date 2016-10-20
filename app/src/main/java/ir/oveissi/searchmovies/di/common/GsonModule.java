package ir.oveissi.searchmovies.di.common;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Abbas on 5/17/16.
 */
@Module
public class GsonModule {
    @Singleton
    @Provides
    public Gson provideGson() {
        return new Gson();
    }
}
