package ir.oveissi.searchmovies.features.moviesearch;

import java.util.List;

import ir.oveissi.searchmovies.pojo.Movie;

public interface MovieSearchViewState {
    final class Loading implements MovieSearchViewState {

    }

    final class Error implements MovieSearchViewState {
        public String message;

        public Error(String message) {
            this.message = message;
        }
    }

    final class Data implements MovieSearchViewState {
        public List<Movie> movie;
        public String title;
        public int page;
        public boolean lastPage=false;


    }
}
