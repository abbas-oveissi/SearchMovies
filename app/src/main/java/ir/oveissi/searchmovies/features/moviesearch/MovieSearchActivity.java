package ir.oveissi.searchmovies.features.moviesearch;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ir.oveissi.searchmovies.R;
import ir.oveissi.searchmovies.SearchMovieApplication;
import ir.oveissi.searchmovies.features.moviedetail.MovieDetailActivity;
import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.utils.AdvancedEndlessRecyclerOnScrollListener;
import ir.oveissi.searchmovies.utils.customviews.LoadingLayout;
import ir.oveissi.searchmovies.utils.customviews.SearchView;


public class MovieSearchActivity extends AppCompatActivity implements MovieSearchContract.View {

    @Inject
    public MovieSearchPresenter mPresenter;

    private MovieSearchAdapter mListAdapter;
    RecyclerView rv;
    SearchView mSearchView;
    LoadingLayout loadinglayout;
    public String title="";
    AdvancedEndlessRecyclerOnScrollListener advancedEndlessRecyclerOnScrollListener;
    public int current_page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SearchMovieApplication.getComponent().plus(new MovieSearchPresenterModule()).inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        rv=(RecyclerView)findViewById(R.id.rvMovies);
        mSearchView=(SearchView)findViewById(R.id.svMovies);
        loadinglayout=(LoadingLayout)findViewById(R.id.loadinglayout);
        loadinglayout.setState(LoadingLayout.STATE_SHOW_DATA);
        loadinglayout.setListener(new LoadingLayout.onErrorClickListener() {
            @Override
            public void onClick() {
                mPresenter.performSearch(title);
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mListAdapter=new MovieSearchAdapter(MovieSearchActivity.this, new ArrayList<Movie>());
        mListAdapter.setItemClickListener(new MovieSearchAdapter.ItemClickListener() {
            @Override
            public void ItemClicked(int position, Movie item, ImageView imPoster) {

                Intent i=new Intent(MovieSearchActivity.this,MovieDetailActivity.class);
                i.putExtra("movie_id",String.valueOf(item.id));
                i.putExtra("image_path",item.poster);
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
                {
                    ActivityOptionsCompat option =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    MovieSearchActivity.this,imPoster,"imPoster");
                    startActivity(i,option.toBundle());
                }
                else
                {
                    startActivity(i);
                }
            }
        });
        rv.setAdapter(mListAdapter);


        mSearchView.setListener(new SearchView.performSearchListener() {
            @Override
            public void performSearch(String terms) {
                title=terms;
                mPresenter.performSearch(terms);
            }
        });

        advancedEndlessRecyclerOnScrollListener=new AdvancedEndlessRecyclerOnScrollListener((LinearLayoutManager) rv.getLayoutManager()) {
            @Override
            public void onLoadMore() {
                mPresenter.getMoviesByTitle(title,current_page);
                current_page++;
            }
        };
        rv.addOnScrollListener(advancedEndlessRecyclerOnScrollListener);

        mPresenter.attachView(this);
        mPresenter.getMoviesByTitle(title,1);
        current_page++;
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
        advancedEndlessRecyclerOnScrollListener.setLoading(false);
        for(Movie p:movies)
        {
            mListAdapter.addItem(p);
        }
    }



    @Override
    protected void onDestroy() {
        this.mPresenter.detachView();
        super.onDestroy();
    }
}
