package ir.oveissi.searchmovies;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;
import ir.oveissi.searchmovies.di.ApplicationComponent;
import ir.oveissi.searchmovies.di.DaggerApplicationComponent;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by abbas on 10/20/16.
 */

public class SearchMovieApplication extends Application implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent
                .builder()
                .application(this)
                .build();
        component.inject(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("vazir.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    private static ApplicationComponent component;

    public static ApplicationComponent getComponent() {
        return component;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}