package ir.oveissi.searchmovies.di;


import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import ir.oveissi.searchmovies.SearchMovieApplication;
import ir.oveissi.searchmovies.di.common.ApiModule;
import ir.oveissi.searchmovies.di.common.ClientModule;
import ir.oveissi.searchmovies.features.moviedetail.MovieDetailPresenterModule;
import ir.oveissi.searchmovies.features.moviesearch.MovieSearchPresenterModule;


@Singleton
@Component( modules = {
        AndroidModule.class,
        ApplicationModule.class,
        ApiModule.class,
        AndroidInjectionModule.class,
        ActivityBuilder.class,
        InteractorModule.class,
        ClientModule.class,
})
public interface ApplicationComponent {

    void inject(SearchMovieApplication __);

    @Component.Builder
    interface Builder
    {
        ApplicationComponent build();
        @BindsInstance Builder application(SearchMovieApplication application);
    }

}

