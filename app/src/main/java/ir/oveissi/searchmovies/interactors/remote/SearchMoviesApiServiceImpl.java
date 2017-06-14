package ir.oveissi.searchmovies.interactors.remote;

import com.google.gson.Gson;

import java.io.IOException;

import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.pojo.TmpMovies;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by abbas on 7/18/16.
 */
public class SearchMoviesApiServiceImpl implements SearchMoviesApiService{
    private final ApiInterface api;

    public SearchMoviesApiServiceImpl(ApiInterface api) {
        this.api = api;
    }

    @Override
    public Observable<TmpMovies> getMoviesByTitle(String query,Integer page) {
        return api.getMoviesByTitle(query,page)
                .compose(this.<TmpMovies>parseHttpErrors());
    }

    @Override
    public Observable<Movie> getMovieById(String id) {
        return api.getMovieById(id)
                .compose(this.<Movie>parseHttpErrors());
    }


    <T> Observable.Transformer<T, T> parseHttpErrors() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {
                    @Override
                    public Observable<? extends T> call(Throwable throwable) {
                        if (throwable instanceof HttpException) {

                            Gson gson=new Gson();
                            GeneralApiException generalApiException=null;
                            try {
                                generalApiException=gson.fromJson(((HttpException) throwable).response().errorBody().string(),GeneralApiException.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if(generalApiException==null)
                                return Observable.error(throwable);
                            else
                                return Observable.error(generalApiException);
                        }
                        // if not the kind we're interested in, then just report the same error to onError()
                        return Observable.error(throwable);
                    }
                });
            }
        };
    }
}
