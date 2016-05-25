package ir.oveissi.searchmovies.data.source.remote;

import ir.oveissi.searchmovies.data.source.remote.jsontemplate.TmpMovies;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Abbas on 24/05/2016.
 */
public interface ApiInterface {
        @GET("?")
        Observable<TmpMovies> getMoviesByTitle(@Query("s") String title, @Query("page") int page);
}
