package ir.oveissi.searchmovies.features.moviedetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import ir.oveissi.searchmovies.R;
import ir.oveissi.searchmovies.customviews.LoadingLayout;
import ir.oveissi.searchmovies.interactors.MovieInteractor;
import ir.oveissi.searchmovies.interactors.MovieInteractorImpl;
import ir.oveissi.searchmovies.interactors.remote.ApiClient;
import ir.oveissi.searchmovies.interactors.remote.SearchMoviesApiServiceImpl;
import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.utils.SchedulerProviderImpl;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailContract.View {

    private MovieDetailPresenter mPresenter;
    LoadingLayout loadinglayout;
    public String title="";
    TextView tvMovieTitle,tvOverview;
    private String movie_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        loadinglayout=(LoadingLayout)findViewById(R.id.loadinglayout);
        tvMovieTitle=(TextView) findViewById(R.id.tvMovieTitle);
        tvOverview=(TextView)findViewById(R.id.tvOverview);

        loadinglayout.setState(LoadingLayout.STATE_SHOW_DATA);

        MovieInteractor mvInteractor = MovieInteractorImpl.getInstance(
                SearchMoviesApiServiceImpl.getInstance(ApiClient.getClient()),
                new SchedulerProviderImpl());

        mPresenter = new MovieDetailPresenter(mvInteractor,this);


        if(getIntent().getExtras()!=null)
        {
            movie_id=getIntent().getStringExtra("movie_id");
            mPresenter.getMovieDetailFromWebservice(movie_id);
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

    @Override
    public void showMovieDetail(Movie movie) {
        tvMovieTitle.setText(movie.original_title);
        tvOverview.setText(movie.overview);
    }

    @Override
    public void hideLoadingLayout() {
        loadinglayout.setState(LoadingLayout.STATE_SHOW_DATA);
    }

    @Override
    public void showLoadingLayout() {
        loadinglayout.setState(LoadingLayout.STATE_LOADING);

    }
}
