package ir.oveissi.searchmovies.di;


import javax.inject.Singleton;

import dagger.Component;
import ir.oveissi.searchmovies.di.common.CallAdapterModule;
import ir.oveissi.searchmovies.di.common.ClientModule;
import ir.oveissi.searchmovies.di.common.ConverterModule;
import ir.oveissi.searchmovies.di.common.GsonModule;
import ir.oveissi.searchmovies.di.common.LoggerModule;
import ir.oveissi.searchmovies.di.common.ApiModule;
import ir.oveissi.searchmovies.features.moviedetail.MovieDetailComponent;
import ir.oveissi.searchmovies.features.moviedetail.MovieDetailPresenterModule;
import ir.oveissi.searchmovies.features.moviesearch.MovieSearchComponent;
import ir.oveissi.searchmovies.features.moviesearch.MovieSearchPresenterModule;


@Singleton
@Component( modules = {
        AndroidModule.class,
        ApplicationModule.class,
        ApiModule.class,
        ConverterModule.class,
        CallAdapterModule.class,
        InteractorMadule.class,
        ClientModule.class,
        LoggerModule.class,
        GsonModule.class
})
public interface ApplicationComponent {

    MovieDetailComponent plus(MovieDetailPresenterModule module);
    MovieSearchComponent plus(MovieSearchPresenterModule module);

}

