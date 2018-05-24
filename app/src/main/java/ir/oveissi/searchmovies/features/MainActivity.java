package ir.oveissi.searchmovies.features;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import butterknife.ButterKnife;
import ir.oveissi.searchmovies.R;
import ir.oveissi.searchmovies.features.moviedetail.MovieDetailFragment;
import ir.oveissi.searchmovies.features.moviesearch.MovieSearchFragment;
import ir.oveissi.searchmovies.utils.NavigationManager;
import ir.oveissi.searchmovies.utils.bases.BaseActivity;
import ir.oveissi.searchmovies.utils.bases.FragmentInteractionListener;
import ir.oveissi.searchmovies.utils.bases.HasNavigationManager;

public class MainActivity extends BaseActivity implements HasNavigationManager,
        FragmentInteractionListener,
        MovieSearchFragment.OnMovieSearchFragmentInteractionListener,
        MovieDetailFragment.OnMovieDtailFragmentInteractionListener {


    private NavigationManager navigationManager;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        navigationManager = new NavigationManager(getSupportFragmentManager(), R.id.container);

        if (savedInstanceState == null) {
            navigationManager.openAsRoot(MovieSearchFragment.newInstance());
        }


    }


    @Override
    public NavigationManager provideNavigationManager() {
        return navigationManager;
    }

    @Override
    public void setCurrentFragment(Fragment fragment) {
        this.currentFragment = fragment;
    }
}
