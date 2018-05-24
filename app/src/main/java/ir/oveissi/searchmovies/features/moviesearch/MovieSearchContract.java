package ir.oveissi.searchmovies.features.moviesearch;

import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.pojo.Pagination;
import ir.oveissi.searchmovies.utils.bases.IPresenter;
import ir.oveissi.searchmovies.utils.bases.IView;

/**
 * Created by Abbas on 24/05/2016.
 */
public interface MovieSearchContract {

    interface View extends IView<Presenter> {

        void showMoreMovies(Pagination<Movie> tasks);

        void clearMovies();

        void showLoading();

        void showData();

        void showError(String error);
    }

    interface Presenter extends IPresenter<MovieSearchContract.View> {


        void onSearchButtonClick(String terms);

        void onLoadMoviesByTitle(String title, int page);

    }
}
