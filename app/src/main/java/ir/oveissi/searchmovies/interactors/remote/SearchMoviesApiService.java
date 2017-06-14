package ir.oveissi.searchmovies.interactors.remote;

import io.reactivex.Observable;
import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.pojo.TmpMovies;

/**
 * Created by abbas on 7/18/16.
 */
public interface SearchMoviesApiService {

    Observable<TmpMovies> getMoviesByTitle(String query, Integer page);

    Observable<Movie> getMovieById(String id);
}
