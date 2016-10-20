package ir.oveissi.searchmovies.features.moviesearch;

import dagger.Module;
import dagger.Provides;


@Module
public class MovieSearchPresenterModule {

    private final MovieSearchContract.View mView;

    public MovieSearchPresenterModule(MovieSearchContract.View view) {
        mView = view;
    }

    @Provides
    public MovieSearchContract.Presenter providePresenter(MovieSearchPresenter presenter) {
        return presenter;
    }

    @Provides
    MovieSearchContract.View provideView() {
        return mView;
    }

}
