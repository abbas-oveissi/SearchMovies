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
    private SearchMovieApplication searchMovieApplication;
    int PRIVATE_MODE = 0;


    public AndroidModule(SearchMovieApplication searchMovieApplication) {
        this.searchMovieApplication = searchMovieApplication;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return searchMovieApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    public Resources provideResources() {
        return searchMovieApplication.getResources();
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(searchMovieApplication);
    }

}
