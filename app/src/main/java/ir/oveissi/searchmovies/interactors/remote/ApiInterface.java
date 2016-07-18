package ir.oveissi.searchmovies.interactors.remote;

import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.pojo.TmpMovies;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Abbas on 24/05/2016.
 */
public interface ApiInterface {

        //http://api.themoviedb.org/3/search/movie?api_key=[API_KEY]&query=[QUERY]
        @GET("search/movie")
        Observable<TmpMovies> getMoviesByTitle(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") Integer page);

        //http://api.themoviedb.org/3/movie/[ID]/movie?api_key=[API_KEY]
        @GET("movie/{id}")
        Observable<Movie> getMovieById(@Query("api_key") String apiKey,@Path("id") String id);
}
