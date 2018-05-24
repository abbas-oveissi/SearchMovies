package ir.oveissi.searchmovies.interactors.remote;

import io.reactivex.Observable;
import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.pojo.Pagination;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Abbas on 24/05/2016.
 */
public interface ApiInterface {

    //http://moviesapi.ir/api/v1/movies?q=[QUERY]
    @GET("movies")
    Observable<Pagination<Movie>> getMoviesByTitle(@Query("q") String query, @Query("page") Integer page);

    //http://moviesapi.ir/api/v1/movies/{ID}
    @GET("movies/{id}")
    Observable<Movie> getMovieById(@Path("id") String id);
}
