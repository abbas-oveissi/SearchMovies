package ir.oveissi.searchmovies.interactors.remote;

import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.pojo.TmpMovies;
import ir.oveissi.searchmovies.utils.Constants;
import rx.Observable;

/**
 * Created by abbas on 7/18/16.
 */
public class SearchMoviesApiServiceImpl implements SearchMoviesApiService{
    private static SearchMoviesApiService INSTANCE;

    public static SearchMoviesApiService getInstance(ApiInterface api) {
        if (INSTANCE == null) {
            INSTANCE = new SearchMoviesApiServiceImpl(api);
        }
        return INSTANCE;
    }

    private final ApiInterface api;
    private SearchMoviesApiServiceImpl(ApiInterface api) {
        this.api = api;
    }

    @Override
    public Observable<TmpMovies> getMoviesByTitle(String query,Integer page) {
        return api.getMoviesByTitle(Constants.API_KEY,query,page);
    }

    @Override
    public Observable<Movie> getMovieById(String id) {
        return api.getMovieById(id,Constants.API_KEY);
    }
}
