package ir.oveissi.searchmovies.features.moviesearch;

import dagger.Module;
import dagger.Provides;


@Module
public class MovieSearchPresenterModule {

    @Provides
    public MovieSearchContract.Presenter providePresenter(MovieSearchPresenter presenter) {
        return presenter;
    }

}
