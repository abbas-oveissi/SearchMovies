package ir.oveissi.searchmovies.features.moviesearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.oveissi.searchmovies.R;
import ir.oveissi.searchmovies.customviews.LoadingLayout;
import ir.oveissi.searchmovies.customviews.SearchView;
import ir.oveissi.searchmovies.interactors.MovieInteractor;
import ir.oveissi.searchmovies.interactors.MovieInteractorImpl;
import ir.oveissi.searchmovies.interactors.remote.ApiClient;
import ir.oveissi.searchmovies.interactors.remote.SearchMoviesApiServiceImpl;
import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.utils.EndlessRecyclerOnScrollListener;
import ir.oveissi.searchmovies.utils.SchedulerProviderImpl;

public class MovieSearchActivity extends AppCompatActivity implements MovieSearchContract.View {

    private MovieSearchPresenter mPresenter;
    private MovieSearchAdapter mListAdapter;
    RecyclerView rv;
    SearchView mSearchView;
    LoadingLayout loadinglayout;
    public String title="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

//        MoviesFragment mFragment =(MoviesFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
//        if (mFragment == null) {
//            mFragment = MoviesFragment.newInstance();
//            ActivityUtils.addFragmentToActivity(
//                    getSupportFragmentManager(), mFragment, R.id.content_frame);
//        }

        rv=(RecyclerView)findViewById(R.id.rvMovies);
        mSearchView=(SearchView)findViewById(R.id.svMovies);
        loadinglayout=(LoadingLayout)findViewById(R.id.loadinglayout);
        loadinglayout.setState(LoadingLayout.STATE_SHOW_DATA);

        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mListAdapter=new MovieSearchAdapter(this, new ArrayList<Movie>());
        rv.setAdapter(mListAdapter);


        mSearchView.setListener(new SearchView.performSearchListener() {
            @Override
            public void performSearch(String terms) {
                title=terms;
                mPresenter.performSearch(terms);
            }
        });

        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener((LinearLayoutManager) rv.getLayoutManager()) {
            @Override
            public void onLoadMore(int current_page) {
                mPresenter.getMoviesByTitle(title,current_page);
            }
        });


        MovieInteractor mvInteractor = MovieInteractorImpl.getInstance(
                SearchMoviesApiServiceImpl.getInstance(ApiClient.getClient()),
                new SchedulerProviderImpl());

        mPresenter = new MovieSearchPresenter(mvInteractor,this);

    }



    public void clearMovies()
    {
        mListAdapter.clear();
    }

    @Override
    public void showToast(String txt) {
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }


    public void showLoadingForMovies()
    {
        loadinglayout.setState(LoadingLayout.STATE_LOADING);

    }

    public void hideLoadingForMovies()
    {
        if(loadinglayout.getState()!=LoadingLayout.STATE_SHOW_DATA)
            loadinglayout.setState(LoadingLayout.STATE_SHOW_DATA);
    }

    @Override
    public void showMoreMovies(List<Movie> movies) {
        for(Movie p:movies)
        {
            mListAdapter.addItem(p);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

}
