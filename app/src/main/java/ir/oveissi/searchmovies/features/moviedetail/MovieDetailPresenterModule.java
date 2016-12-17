package ir.oveissi.searchmovies.features.moviedetail;

import dagger.Module;
import dagger.Provides;


@Module
public class MovieDetailPresenterModule {

    @Provides
    public MovieDetailContract.Presenter providePresenter(MovieDetailPresenter presenter) {
        return presenter;
    }

}
