package ir.oveissi.searchmovies.di;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ir.oveissi.searchmovies.features.moviedetail.MovieDetailFragment;
import ir.oveissi.searchmovies.features.moviedetail.MovieDetailPresenterModule;
import ir.oveissi.searchmovies.features.moviesearch.MovieSearchFragment;
import ir.oveissi.searchmovies.features.moviesearch.MovieSearchPresenterModule;

@Module
abstract class FragmentBuilder {


    @ContributesAndroidInjector(modules = {MovieDetailPresenterModule.class})
    abstract MovieDetailFragment contributeMovieDetailInjector();

    @ContributesAndroidInjector(modules = {MovieSearchPresenterModule.class})
    abstract MovieSearchFragment contributeMoviesSearchInjector();
}
