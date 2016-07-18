package ir.oveissi.searchmovies.interactors.remote;

import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.pojo.TmpMovies;
import rx.Observable;

/**
 * Created by abbas on 7/18/16.
 */
public interface SearchMoviesApiService {

    Observable<TmpMovies> getMoviesByTitle( String query,Integer page);

    Observable<Movie> getMovieById(String id);
}
