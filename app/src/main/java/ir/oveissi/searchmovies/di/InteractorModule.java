package ir.oveissi.searchmovies.di;

import dagger.Module;
import dagger.Provides;
import ir.oveissi.searchmovies.interactors.MovieInteractor;
import ir.oveissi.searchmovies.interactors.MovieInteractorImpl;

/**
 * Created by abbas on 7/5/16.
 */
@Module
public class InteractorModule {

    @Provides
    public static MovieInteractor provideMovieInteractor(MovieInteractorImpl interactor) {
        return interactor;
    }

}
