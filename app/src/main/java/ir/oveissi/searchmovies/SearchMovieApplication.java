package ir.oveissi.searchmovies;

import android.app.Application;

import ir.oveissi.searchmovies.di.ApplicationComponent;
import ir.oveissi.searchmovies.di.DaggerApplicationComponent;

/**
 * Created by abbas on 10/20/16.
 */

public class SearchMovieApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .application(this)
                .build();


    }

    private static ApplicationComponent component;

    public static ApplicationComponent getComponent() {
        return component;
    }

}