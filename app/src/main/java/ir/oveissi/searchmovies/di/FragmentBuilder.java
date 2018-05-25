package ir.oveissi.searchmovies.di;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ir.oveissi.searchmovies.di.annotation.FragmentScope;
import ir.oveissi.searchmovies.features.moviedetail.MovieDetailFragment;
import ir.oveissi.searchmovies.features.moviesearch.MovieSearchFragment;

@Module
abstract class FragmentBuilder {


    @FragmentScope
    @ContributesAndroidInjector()
    abstract MovieDetailFragment contributeMovieDetailInjector();

    @FragmentScope
    @ContributesAndroidInjector()
    abstract MovieSearchFragment contributeMoviesSearchInjector();
}
