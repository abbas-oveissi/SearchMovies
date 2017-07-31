package ir.oveissi.searchmovies.di;

import dagger.Binds;
import dagger.Module;
import ir.oveissi.searchmovies.interactors.MovieInteractor;
import ir.oveissi.searchmovies.interactors.MovieInteractorImpl;

/**
 * Created by abbas on 7/5/16.
 */
@Module
public abstract class InteractorModule {

    @Binds
    public abstract MovieInteractor provideMovieInteractor(MovieInteractorImpl interactor);

}
