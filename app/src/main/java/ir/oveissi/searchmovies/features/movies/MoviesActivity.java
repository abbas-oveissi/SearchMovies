package ir.oveissi.searchmovies.features.movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ir.oveissi.searchmovies.R;
import ir.oveissi.searchmovies.data.source.MovieRepository;
import ir.oveissi.searchmovies.data.source.remote.MovieRemoteDataSource;
import ir.oveissi.searchmovies.utils.ActivityUtils;

public class MoviesActivity extends AppCompatActivity {

    private MoviesPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        MoviesFragment mFragment =(MoviesFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (mFragment == null) {
            mFragment = MoviesFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mFragment, R.id.content_frame);
        }

        mPresenter = new MoviesPresenter(
                MovieRepository.getInstance(MovieRemoteDataSource.getInstance()),
                mFragment);

        // Load previously saved state, if available.
//        if (savedInstanceState != null) {
//            TasksFilterType currentFiltering =
//                    (TasksFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
//            mTasksPresenter.setFiltering(currentFiltering);
//        }
    }
}
