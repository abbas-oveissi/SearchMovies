package ir.oveissi.searchmovies.di.common;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ir.oveissi.searchmovies.di.annotation.ViewModelKey;
import ir.oveissi.searchmovies.features.moviedetail.MovieDetailViewModel;
import ir.oveissi.searchmovies.features.moviesearch.MovieSearchViewModel;
import ir.oveissi.searchmovies.utils.ViewModelProviderFactory;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel.class)
    abstract ViewModel bindUserViewModel1(MovieDetailViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MovieSearchViewModel.class)
    abstract ViewModel bindUserViewModel(MovieSearchViewModel userViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory factory);
}