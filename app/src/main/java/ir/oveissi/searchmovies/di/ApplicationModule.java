package ir.oveissi.searchmovies.di;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.oveissi.searchmovies.BuildConfig;
import ir.oveissi.searchmovies.utils.Constants;
import ir.oveissi.searchmovies.utils.SchedulerProvider;
import ir.oveissi.searchmovies.utils.SchedulerProviderImpl;

/**
 * Created by abbas on 6/25/16.
 */
@Module
public final class ApplicationModule {
    @Provides
    @Singleton
    @Named("isDebug")
    public boolean provideIsDebug() {
        return BuildConfig.DEBUG;
    }

    @Provides
    @Singleton
    @Named("networkTimeoutInSeconds")
    public int provideNetworkTimeoutInSeconds() {
        return 30;
    }



    @Provides
    @Singleton
    @Named("BaseUrl")
    public String provideBaseUrl() {
        return Constants.BASE_URL;
    }


    @Provides
    @Singleton
    public SchedulerProvider provideAppScheduler() {
        return new SchedulerProviderImpl();
    }

}
