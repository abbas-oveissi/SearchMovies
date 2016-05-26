package ir.oveissi.searchmovies.features.movies;

import java.util.List;

import ir.oveissi.searchmovies.BasePresenter;
import ir.oveissi.searchmovies.BaseView;
import ir.oveissi.searchmovies.data.Movie;

/**
 * Created by Abbas on 24/05/2016.
 */
public interface MoviesContract {

    interface View extends BaseView<Presenter> {

        void showMoreMovies(List<Movie> tasks);
        void clearMovies();
    }

    interface Presenter extends BasePresenter {

        void getMoviesByTitle(String title,int page);

        void performSearch(String terms);
    }
}
