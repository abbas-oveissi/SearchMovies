package ir.oveissi.searchmovies.interactors.remote;

import com.google.gson.Gson;

import org.reactivestreams.Publisher;

import java.io.IOException;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;
import ir.oveissi.searchmovies.interactors.remote.exceptions.GeneralApiException;
import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.pojo.Pagination;
import retrofit2.HttpException;


/**
 * Created by abbas on 7/18/16.
 */
public class SearchMoviesApiService {
    private final ApiInterface api;

    public SearchMoviesApiService(ApiInterface api) {
        this.api = api;
    }

    public Flowable<Pagination<Movie>> getMoviesByTitle(String query, Integer page) {
        return api.getMoviesByTitle(query, page)
                .compose(this.parseHttpErrors());
    }

    public Flowable<Movie> getMovieById(String id) {
        return api.getMovieById(id)
                .compose(this.parseHttpErrors());
    }

    <T> FlowableTransformer<T, T> parseHttpErrors() {
        return flowable -> flowable.onErrorResumeNext((Function<Throwable, Publisher<? extends T>>) throwable -> {

                if (throwable instanceof HttpException) {

                    Gson gson = new Gson();
                    GeneralApiException generalApiException = null;
                    try {
                        generalApiException = gson.fromJson(((HttpException) throwable).response().errorBody().string(), GeneralApiException.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (generalApiException == null)
                        return Flowable.error(throwable);
                    else
                        return Flowable.error(generalApiException);
                }
                // if not the kind we're interested in, then just report the same error to onError()
                return Flowable.error(throwable);
            }
        );
    }
}
