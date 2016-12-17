package ir.oveissi.searchmovies.features.moviedetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import ir.oveissi.searchmovies.R;
import ir.oveissi.searchmovies.SearchMovieApplication;
import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.utils.Constants;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailContract.View {

    @Inject
    public MovieDetailContract.Presenter mPresenter;


    TextView tvMovieTitle,tvOverview;
    private String movie_id;
    private String image_path;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SearchMovieApplication.getComponent().plus(new MovieDetailPresenterModule()).inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        tvMovieTitle=(TextView) findViewById(R.id.tvMovieTitle);
        tvOverview=(TextView)findViewById(R.id.tvOverview);
        imageView=(ImageView)findViewById(R.id.imPoster);


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

        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        this.mPresenter.detachView();
        super.onDestroy();
    }
    @Override
    public void showMovieDetail(Movie movie) {
        tvMovieTitle.setText(movie.original_title);
        tvOverview.setText(movie.overview);
    }

}
