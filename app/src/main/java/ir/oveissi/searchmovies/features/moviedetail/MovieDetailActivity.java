package ir.oveissi.searchmovies.features.moviedetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import ir.oveissi.searchmovies.R;
import ir.oveissi.searchmovies.interactors.MovieInteractor;
import ir.oveissi.searchmovies.interactors.MovieInteractorImpl;
import ir.oveissi.searchmovies.interactors.remote.ApiClient;
import ir.oveissi.searchmovies.interactors.remote.SearchMoviesApiServiceImpl;
import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.utils.Constants;
import ir.oveissi.searchmovies.utils.SchedulerProviderImpl;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailContract.View {

    private MovieDetailPresenter mPresenter;
    public String title="";
    TextView tvMovieTitle,tvOverview;
    private String movie_id;
    private String image_path;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        tvMovieTitle=(TextView) findViewById(R.id.tvMovieTitle);
        tvOverview=(TextView)findViewById(R.id.tvOverview);
        imageView=(ImageView)findViewById(R.id.imPoster);

        MovieInteractor mvInteractor = MovieInteractorImpl.getInstance(
                SearchMoviesApiServiceImpl.getInstance(ApiClient.getClient()),
                new SchedulerProviderImpl());

        mPresenter = new MovieDetailPresenter(mvInteractor,this);


        if(getIntent().getExtras()!=null)
        {
            movie_id=getIntent().getStringExtra("movie_id");
            image_path=getIntent().getStringExtra("image_path");
            mPresenter.getMovieDetailFromWebservice(movie_id);
        }

        Picasso.with(this)
                .load(Constants.BASE_IMAGE_URL+image_path)
                .noFade()
                .placeholder(R.drawable.placeholder)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView);


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

}
