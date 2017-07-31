package ir.oveissi.searchmovies.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.oveissi.searchmovies.SearchMovieApplication;

/**
 * Created by Abbas on 22/05/16.
 */
@Module
public class AndroidModule {
    int PRIVATE_MODE = 0;

    @Provides
    @Singleton
    public Context provideContext(SearchMovieApplication application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public Resources provideResources(SearchMovieApplication application) {
        return application.getResources();
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(SearchMovieApplication application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

}
