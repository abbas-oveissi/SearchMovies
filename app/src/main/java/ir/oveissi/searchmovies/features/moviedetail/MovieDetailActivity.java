package ir.oveissi.searchmovies.features.moviedetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.oveissi.searchmovies.R;
import ir.oveissi.searchmovies.SearchMovieApplication;
import ir.oveissi.searchmovies.pojo.Movie;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailContract.View {

    @Inject
    public MovieDetailContract.Presenter mPresenter;



    @BindView(R.id.tvMovieTitle)

    TextView tvMovieTitle;

    @BindView(R.id.tvOverview)
    TextView  tvOverview;

    private String movie_id;
    private String image_path;

    @BindView(R.id.imPoster)
    ImageView imPoster;

    @BindView(R.id.myToolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SearchMovieApplication.getComponent().plus(new MovieDetailPresenterModule()).inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);




        mPresenter.onViewAttached(this);
        mPresenter.subscribe();

        if(getIntent().getExtras()!=null)
        {
            movie_id=getIntent().getStringExtra("movie_id");
            image_path=getIntent().getStringExtra("image_path");
            mPresenter.onLoadMovieDetail(movie_id);
        }

        Picasso.with(this)
                .load(image_path)
                .noFade()
                .placeholder(R.drawable.placeholder)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imPoster);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.unsubscribe();
    }

    @Override
    public void showMovieDetail(Movie movie) {
        tvMovieTitle.setText(movie.title);
        tvOverview.setText(movie.plot);
    }

    @Override
    public void showToast(String txt) {
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }

}
