package ir.oveissi.searchmovies.features.moviesearch;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.oveissi.searchmovies.R;
import ir.oveissi.searchmovies.SearchMovieApplication;
import ir.oveissi.searchmovies.features.moviedetail.MovieDetailActivity;
import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.utils.customviews.EndlessLinearLayoutRecyclerview;
import ir.oveissi.searchmovies.utils.customviews.LoadingLayout;


public class MovieSearchActivity extends AppCompatActivity implements MovieSearchContract.View {

    @Inject
    public MovieSearchPresenter mPresenter;

    private MovieSearchAdapter mListAdapter;

    @BindView(R.id.rvMovies)
    EndlessLinearLayoutRecyclerview rvMovies;

    @BindView(R.id.search_view)
    MaterialSearchView searchView;

    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;

    public String title="";


    @BindView(R.id.myToolbar)
    Toolbar toolbar;



    public int current_page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SearchMovieApplication.getComponent().plus(new MovieSearchPresenterModule()).inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        loadinglayout.setState(LoadingLayout.STATE_SHOW_DATA);
        loadinglayout.setListener(new LoadingLayout.onErrorClickListener() {
            @Override
            public void onClick() {
                mPresenter.onSearchButtonClick(title);
            }
        });


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

        rvMovies.setAdapter(mListAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setOnLoadMoreListener(new EndlessLinearLayoutRecyclerview.onLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.onLoadMoviesByTitle(title,current_page);
                current_page++;
            }
        });


        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                title=query;
                current_page=1;
                mPresenter.onSearchButtonClick(query);
                current_page++;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });


        mPresenter.onViewAttached(this);
        mPresenter.subscribe();

        mPresenter.onLoadMoviesByTitle(title,1);
        current_page++;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.unsubscribe();
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
        rvMovies.setLoading(false);
        for(Movie p:movies)
        {
            mListAdapter.addItem(p);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);

            MenuItem item = menu.findItem(R.id.action_search);
            searchView.setMenuItem(item);

            return true;
    }


}
