package ir.oveissi.searchmovies.features.moviedetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.oveissi.searchmovies.R;
import ir.oveissi.searchmovies.SearchMovieApplication;
import ir.oveissi.searchmovies.pojo.Movie;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailContract.View {


    private String movie_id;
    private String image_path;


    @BindView(R.id.tvOverview)
    TextView tvOverview;

    @BindView(R.id.imPoster)
    ImageView imPoster;

    @BindView(R.id.myToolbar)
    Toolbar toolbar;

    @BindView(R.id.textview_product_detail_toolbar_title)
    TextView title;

    @OnClick(R.id.imageview_activity_movie_detail_back)
    void performBack() {
        onBackPressed();
    }

    @Inject
    public MovieDetailContract.Presenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SearchMovieApplication.getComponent().plus(new MovieDetailPresenterModule()).inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        mPresenter.onViewAttached(this);
        mPresenter.subscribe();

        if (getIntent().getExtras() != null) {
            movie_id = getIntent().getStringExtra("movie_id");
            image_path = getIntent().getStringExtra("image_path");
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
        title.setText(movie.title);
        tvOverview.setText(movie.plot);
    }

    @Override
    public void showToast(String txt) {
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }

}
