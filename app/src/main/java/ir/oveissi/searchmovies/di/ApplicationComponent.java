package ir.oveissi.searchmovies.di;


import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import ir.oveissi.searchmovies.SearchMovieApplication;
import ir.oveissi.searchmovies.di.common.ApiModule;
import ir.oveissi.searchmovies.di.common.ClientModule;
import ir.oveissi.searchmovies.features.moviedetail.MovieDetailComponent;
import ir.oveissi.searchmovies.features.moviedetail.MovieDetailPresenterModule;
import ir.oveissi.searchmovies.features.moviesearch.MovieSearchComponent;
import ir.oveissi.searchmovies.features.moviesearch.MovieSearchPresenterModule;


@Singleton
@Component( modules = {
        AndroidModule.class,
        ApplicationModule.class,
        ApiModule.class,
        InteractorModule.class,
        ClientModule.class,
})
public interface ApplicationComponent {

    MovieDetailComponent plus(MovieDetailPresenterModule module);
    MovieSearchComponent plus(MovieSearchPresenterModule module);


    @Component.Builder
    interface Builder
    {
        ApplicationComponent build();
        @BindsInstance Builder application(SearchMovieApplication application);
    }

}

