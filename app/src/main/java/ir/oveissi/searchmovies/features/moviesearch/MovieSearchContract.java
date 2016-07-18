package ir.oveissi.searchmovies.features.moviesearch;

import java.util.List;

import ir.oveissi.searchmovies.utils.BasePresenter;
import ir.oveissi.searchmovies.utils.BaseView;
import ir.oveissi.searchmovies.pojo.Movie;

/**
 * Created by Abbas on 24/05/2016.
 */
public interface MovieSearchContract {

    interface View extends BaseView<Presenter> {

        void showMoreMovies(List<Movie> tasks);

        void clearMovies();

        void showToast(String txt);

        void showLoadingForMovies();

        void hideLoadingForMovies();

    }

    interface Presenter extends BasePresenter {

        void getMoviesByTitle(String title,int page);

        void performSearch(String terms);
    }
}
