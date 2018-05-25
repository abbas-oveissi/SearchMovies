package ir.oveissi.searchmovies.features.moviedetail;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.ResourceSubscriber;
import ir.oveissi.searchmovies.R;
import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.utils.bases.BaseFragment;
import ir.oveissi.searchmovies.utils.bases.FragmentInteractionListener;
import ir.oveissi.searchmovies.utils.customviews.LoadingLayout;


public class MovieDetailFragment extends BaseFragment {


    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;

    @BindView(R.id.myToolbar)
    Toolbar toolbar;

    @BindView(R.id.tvOverview)
    TextView tvOverview;

    @BindView(R.id.imPoster)
    ImageView imPoster;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private String movie_id;
    MovieDetailViewModel movieDetailViewModel;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private OnMovieDtailFragmentInteractionListener mListener;
    private AppCompatActivity activity;


    public MovieDetailFragment() {
        // Required empty public constructor
    }

    public static MovieDetailFragment newInstance(String movie_id) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putString("movie_id", movie_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie_id = getArguments().getString("movie_id");
        }

        movieDetailViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(MovieDetailViewModel.class);

        if (savedInstanceState == null) {
            movieDetailViewModel.getMovieDetailFromNetwork(Integer.valueOf(movie_id));
        }

    }

    private void render(MovieDetailViewState state) {
        if (state instanceof MovieDetailViewState.Loading) {
            loadinglayout.setState(LoadingLayout.STATE_LOADING);
        } else if (state instanceof MovieDetailViewState.Error) {
            loadinglayout.setErrorText("error");
            loadinglayout.setErrorClickListener(() -> movieDetailViewModel.getMovieDetailFromNetwork(Integer.valueOf(movie_id)));
        } else if (state instanceof MovieDetailViewState.Data) {
            loadinglayout.setState(LoadingLayout.STATE_SHOW_DATA);
            showMovieDetail(((MovieDetailViewState.Data) state).movie);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        activity.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> activity.onBackPressed());
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("");


        mCompositeDisposable.add(movieDetailViewModel.getViewState()
                .subscribeWith(new ResourceSubscriber<MovieDetailViewState>() {
                    @Override
                    public void onNext(MovieDetailViewState movieDetailViewState) {
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);

        if (context instanceof Activity) {
            activity = (AppCompatActivity) context;

        }

        if (context instanceof OnMovieDtailFragmentInteractionListener) {
            mListener = (OnMovieDtailFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMovieDtailFragmentInteractionListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCompositeDisposable.dispose();
    }


    public void showMovieDetail(Movie movie) {
        activity.getSupportActionBar().setTitle(movie.title);

        tvOverview.setText(movie.plot);

        Picasso.with(getContext())
                .load(movie.poster)
                .noFade()
                .placeholder(R.drawable.placeholder)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imPoster);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnMovieDtailFragmentInteractionListener extends FragmentInteractionListener {
    }
}
