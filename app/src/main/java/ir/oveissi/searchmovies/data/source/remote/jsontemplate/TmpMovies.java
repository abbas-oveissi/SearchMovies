package ir.oveissi.searchmovies.data.source.remote.jsontemplate;

import java.util.ArrayList;
import java.util.List;

import ir.oveissi.searchmovies.data.Movie;

/**
 * Created by Abbas on 25/05/2016.
 */
public class TmpMovies {
    public List<Movie> Search=new ArrayList<>();
    public Integer totalResults;
    public Boolean Response;
}
