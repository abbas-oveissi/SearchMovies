package ir.oveissi.searchmovies.di;


import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import ir.oveissi.searchmovies.SearchMovieApplication;
import ir.oveissi.searchmovies.di.common.ApiModule;
import ir.oveissi.searchmovies.di.common.ClientModule;
import ir.oveissi.searchmovies.di.common.ViewModelModule;


@Singleton
@Component(modules = {
        AndroidModule.class,
        ApplicationModule.class,
        ApiModule.class,
        ViewModelModule.class,
        AndroidSupportInjectionModule.class,
        FragmentBuilder.class,
        ClientModule.class,
})
public interface ApplicationComponent {

    void inject(SearchMovieApplication __);

    @Component.Builder
    interface Builder {
        ApplicationComponent build();

        @BindsInstance
        Builder application(SearchMovieApplication application);
    }

}

