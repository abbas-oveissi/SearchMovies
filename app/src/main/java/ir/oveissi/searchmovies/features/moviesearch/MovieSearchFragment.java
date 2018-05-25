package ir.oveissi.searchmovies.features.moviesearch;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.ResourceSubscriber;
import ir.oveissi.searchmovies.R;
import ir.oveissi.searchmovies.features.moviedetail.MovieDetailFragment;
import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.utils.bases.BaseFragment;
import ir.oveissi.searchmovies.utils.bases.FragmentInteractionListener;
import ir.oveissi.searchmovies.utils.customviews.EndlessLinearLayoutRecyclerview;
import ir.oveissi.searchmovies.utils.customviews.LoadingLayout;


public class MovieSearchFragment extends BaseFragment implements  MovieSearchAdapter.ItemClickListener {


    private MovieSearchAdapter mListAdapter;

    @BindView(R.id.rvMovies)
    EndlessLinearLayoutRecyclerview rvMovies;

    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;

    @BindView(R.id.myToolbar)
    Toolbar toolbar;

    @BindView(R.id.search_view)
    MaterialSearchView searchView;

    private OnMovieSearchFragmentInteractionListener mListener;
    private AppCompatActivity activity;

    public String title = "";

    public int current_page = 1;


    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    MovieSearchViewModel movieSearchViewModel;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieSearchViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(MovieSearchViewModel.class);

        if (savedInstanceState == null) {
            movieSearchViewModel.getMovies("");
        }


    }

    public MovieSearchFragment() {
        // Required empty public constructor
    }

    public static MovieSearchFragment newInstance() {
        MovieSearchFragment fragment = new MovieSearchFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_movie_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        current_page = 1;

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("Search Movies");


        mListAdapter = new MovieSearchAdapter(getActivity(), new ArrayList<>());
        mListAdapter.setItemClickListener(this);
        rvMovies.setAdapter(mListAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovies.setOnLoadMoreListener(() -> {
            movieSearchViewModel.getMoreMovies();
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                submitQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mCompositeDisposable.add(movieSearchViewModel
                .getViewState()
                .subscribeWith(new ResourceSubscriber<MovieSearchViewState>() {
                    @Override
                    public void onNext(MovieSearchViewState movieDetailViewState) {
                        render(movieDetailViewState);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));


    }

    private void submitQuery(String inputSearch) {
        movieSearchViewModel.getMovies(inputSearch);
    }


    private void render(MovieSearchViewState state) {
        if (state instanceof MovieSearchViewState.Loading) {
            loadinglayout.setState(LoadingLayout.STATE_LOADING);
        } else if (state instanceof MovieSearchViewState.Error) {
            loadinglayout.setErrorText(((MovieSearchViewState.Error) state).message);
            Log.e("MyLog",((MovieSearchViewState.Error) state).message);
            loadinglayout.setErrorClickListener(() -> movieSearchViewModel.getMovies(""));
        } else if (state instanceof MovieSearchViewState.Data) {
            if(loadinglayout.getState()!=LoadingLayout.STATE_SHOW_DATA)
            {
                loadinglayout.setState(LoadingLayout.STATE_SHOW_DATA);

            }
            showMoreMovies(((MovieSearchViewState.Data) state).movie,((MovieSearchViewState.Data) state).lastPage);

        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);

        if (context instanceof Activity) {
            activity = (AppCompatActivity) context;
        }
        if (context instanceof OnMovieSearchFragmentInteractionListener) {
            mListener = (OnMovieSearchFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMovieSearchFragmentInteractionListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCompositeDisposable.dispose();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
    }


    public interface OnMovieSearchFragmentInteractionListener extends FragmentInteractionListener {
    }

    public void showMoreMovies(List<Movie> movies,boolean lastpage) {
        if (!lastpage)
            rvMovies.setLoading(false);
        mListAdapter.clear();
        mListAdapter.addAll(movies);
    }


    @Override
    public void ItemClicked(int position, Movie item) {
        getNavigationManager().openFragment(MovieDetailFragment.newInstance(String.valueOf(item.id)), true);

    }
}
