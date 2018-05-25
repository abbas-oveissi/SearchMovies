package ir.oveissi.searchmovies.features.moviesearch;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.processors.PublishProcessor;
import ir.oveissi.searchmovies.interactors.MovieInteractor;
import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.utils.MyDisposabObserver;
import ir.oveissi.searchmovies.utils.bases.BaseViewModel;


public class MovieSearchViewModel extends BaseViewModel {


    PublishProcessor<Pair<String, Integer>> retryNetworkRequest = PublishProcessor.create();

    MovieInteractor movieInteractor;

    BehaviorProcessor<MovieSearchViewState> bpViewStates = null;
    private String title;

    @Inject
    public MovieSearchViewModel(MovieInteractor movieInteractor) {
        this.movieInteractor = movieInteractor;
        bpViewStates = BehaviorProcessor.create();
        bpViewStates.onNext(new MovieSearchViewState.Loading());
        retryNetworkRequest
                .switchMap(aVoid ->
                        movieInteractor.getMoviesByTitle(aVoid.first, aVoid.second)
                                .map((it) -> reducer((MovieSearchViewState) bpViewStates.getValue(), new PartialViewState(it)))
                                .onErrorReturn((it)->{return new MovieSearchViewState.Error(it.getMessage());})
                )
                .subscribeWith(new MyDisposabObserver(bpViewStates));
    }

    BehaviorProcessor<MovieSearchViewState> getViewState() {
        return bpViewStates;
    }


    MovieSearchViewState reducer(MovieSearchViewState old, PartialViewState mm) {

        List<Movie> movies = new ArrayList<>();
        MovieSearchViewState.Data mdata = new MovieSearchViewState.Data();

        if(old instanceof MovieSearchViewState.Data)
        {

            MovieSearchViewState.Data molddata = (MovieSearchViewState.Data) old;
            movies.addAll(molddata.movie);
            mdata.title=((MovieSearchViewState.Data) old).title;
        }
        else
        {
            mdata.title=this.title;
        }

        mdata.page=mm.moviePagination.metadata.current_page;
        movies.addAll(mm.moviePagination.data);

        mdata.movie=movies;

        int lastPageNumber = (mm.moviePagination.metadata.total_count / mm.moviePagination.metadata.per_page) + 1;
        if (mm.moviePagination.metadata.current_page >= lastPageNumber)
            mdata.lastPage=true;
        return mdata;
    }

    void getMovies(String title) {
        this.title = title;
        bpViewStates.onNext(new MovieSearchViewState.Loading());
        retryNetworkRequest.onNext(new Pair<>(title, 1));
    }


    void getMoreMovies() {
        MovieSearchViewState msvs = (MovieSearchViewState) bpViewStates.getValue();
        if (msvs instanceof MovieSearchViewState.Data) {
            retryNetworkRequest.onNext(new Pair<>(((MovieSearchViewState.Data) msvs).title, ((MovieSearchViewState.Data) msvs).page + 1));
        }
    }
}
