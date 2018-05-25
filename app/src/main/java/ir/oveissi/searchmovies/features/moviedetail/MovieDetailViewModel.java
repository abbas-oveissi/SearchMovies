package ir.oveissi.searchmovies.features.moviedetail;

import javax.inject.Inject;

import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.processors.PublishProcessor;
import ir.oveissi.searchmovies.interactors.MovieInteractor;
import ir.oveissi.searchmovies.utils.MyDisposabObserver;
import ir.oveissi.searchmovies.utils.bases.BaseViewModel;


public class MovieDetailViewModel extends BaseViewModel {

    PublishProcessor<Integer> retryNetworkRequest = PublishProcessor.create();

    MovieInteractor movieInteractor;

    BehaviorProcessor<MovieDetailViewState> bpViewStates = null;

    @Inject
    public MovieDetailViewModel(MovieInteractor movieInteractor) {
        this.movieInteractor = movieInteractor;
        bpViewStates = BehaviorProcessor.create();
        retryNetworkRequest
                .switchMap(aVoid -> movieInteractor.getMovieByID(String.valueOf(aVoid))
                        .map((it) -> (MovieDetailViewState) new MovieDetailViewState.Data(it))
                        .onErrorReturnItem(new MovieDetailViewState.Error(""))
                        .startWith(new MovieDetailViewState.Loading()))
                .subscribeWith(new MyDisposabObserver(bpViewStates));
    }

    BehaviorProcessor<MovieDetailViewState> getViewState() {
        return bpViewStates;
    }

    void getMovieDetailFromNetwork(int task) {
        retryNetworkRequest.onNext(task);
    }
}
