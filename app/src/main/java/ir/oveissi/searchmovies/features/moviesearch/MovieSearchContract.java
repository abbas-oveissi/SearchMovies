package ir.oveissi.searchmovies.features.moviesearch;

import java.util.List;

import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.utils.bases.IView;

/**
 * Created by Abbas on 24/05/2016.
 */
public interface MovieSearchContract {

    interface View extends IView<Presenter> {

        void showMoreMovies(List<Movie> tasks);

        void clearMovies();

        void showToast(String txt);

        void showLoadingForMovies();

        void hideLoadingForMovies();

    }

    interface Presenter {

        void getMoviesByTitle(String title,int page);

        void performSearch(String terms);
    }
}
