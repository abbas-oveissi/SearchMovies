package ir.oveissi.searchmovies.features.moviedetail;

import ir.oveissi.searchmovies.pojo.Movie;

public interface MovieDetailViewState {
    final class Loading implements MovieDetailViewState {

    }

    final class Error implements MovieDetailViewState {
        public String message;

        public Error(String message) {
            this.message = message;
        }
    }

    final class Data implements MovieDetailViewState {
        public Movie movie;

        public Data(Movie movie) {
            this.movie = movie;
        }
    }
}
