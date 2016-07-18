package ir.oveissi.searchmovies.features.moviedetail;


import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.utils.BasePresenter;
import ir.oveissi.searchmovies.utils.BaseView;

/**
 * Created by Abbas on 24/05/2016.
 */
public interface MovieDetailContract {

    interface View extends BaseView<Presenter> {

        void showMovieDetail(Movie movie);

        void hideLoadingLayout();

        void showLoadingLayout();
    }

    interface Presenter extends BasePresenter {
        void getMovieDetailFromWebservice(String id);
    }
}
