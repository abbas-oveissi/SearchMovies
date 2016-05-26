/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ir.oveissi.searchmovies.features.movies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.oveissi.searchmovies.R;
import ir.oveissi.searchmovies.adapters.MoviesAdapter;
import ir.oveissi.searchmovies.customview.LoadingLayout;
import ir.oveissi.searchmovies.customview.SearchView;
import ir.oveissi.searchmovies.data.Movie;
import ir.oveissi.searchmovies.utils.EndlessRecyclerOnScrollListener;


public class MoviesFragment extends Fragment implements MoviesContract.View {

    private MoviesContract.Presenter mPresenter;

    private MoviesAdapter mListAdapter;

    public MoviesFragment() {
        // Requires empty public constructor
    }

    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void setPresenter( MoviesContract.Presenter presenter) {
        mPresenter = presenter;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        mPresenter.result(requestCode, resultCode);
//    }

    RecyclerView rv;
    SearchView mSearchView;
    LoadingLayout loadinglayout;
    public String title="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movies, container, false);
        rv=(RecyclerView)root.findViewById(R.id.rvMovies);
        mSearchView=(SearchView)root.findViewById(R.id.svMovies);
        loadinglayout=(LoadingLayout)root.findViewById(R.id.loadinglayout);
        loadinglayout.setState(LoadingLayout.STATE_SHOW_DATA);

        rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mListAdapter=new MoviesAdapter(getActivity(), new ArrayList<Movie>());
        rv.setAdapter(mListAdapter);


        mSearchView.setListener(new SearchView.performSearchListener() {
            @Override
            public void performSearch(String terms) {
                title=terms;
                mPresenter.performSearch(terms);
            }
        });

        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener((LinearLayoutManager) rv.getLayoutManager()) {
            @Override
            public void onLoadMore(int current_page) {
                mPresenter.getMoviesByTitle(title,current_page);
            }
        });

        return root;
    }

    public void clearMovies()
    {
        mListAdapter.clear();
    }

    @Override
    public void showToast(String txt) {
        Toast.makeText(getActivity(), txt, Toast.LENGTH_SHORT).show();
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
        for(Movie p:movies)
        {
            mListAdapter.addItem(p);
        }
    }
}
