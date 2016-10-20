package ir.oveissi.searchmovies.features.moviedetail;

import dagger.Module;
import dagger.Provides;


@Module
public class MovieDetailPresenterModule {

    private final MovieDetailContract.View mView;

    public MovieDetailPresenterModule(MovieDetailContract.View view) {
        mView = view;
    }

    @Provides
    public MovieDetailContract.Presenter providePresenter(MovieDetailPresenter presenter) {
        return presenter;
    }

    @Provides
    MovieDetailContract.View provideView() {
        return mView;
    }

}
