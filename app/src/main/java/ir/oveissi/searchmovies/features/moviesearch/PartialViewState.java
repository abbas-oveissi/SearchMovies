package ir.oveissi.searchmovies.features.moviesearch;

import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.pojo.Pagination;

public class PartialViewState {

    public Pagination<Movie> moviePagination;

    public PartialViewState(Pagination<Movie> moviePagination) {
        this.moviePagination = moviePagination;
    }

}
