package ir.oveissi.searchmovies.di;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ir.oveissi.searchmovies.features.moviedetail.MovieDetailActivity;
import ir.oveissi.searchmovies.features.moviedetail.MovieDetailPresenter;
import ir.oveissi.searchmovies.features.moviedetail.MovieDetailPresenterModule;
import ir.oveissi.searchmovies.features.moviesearch.MovieSearchActivity;
import ir.oveissi.searchmovies.features.moviesearch.MovieSearchPresenterModule;

@Module
abstract class ActivityBuilder {


    @ContributesAndroidInjector(modules = {MovieDetailPresenterModule.class})
    abstract MovieDetailActivity contributeMovieDetailActivityInjector();

    @ContributesAndroidInjector(modules = {MovieSearchPresenterModule.class})
    abstract MovieSearchActivity contributeMoviesSearchActivityInjector();
}
