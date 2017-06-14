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

package ir.oveissi.searchmovies.features.moviesearch;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import ir.oveissi.searchmovies.interactors.MovieInteractor;
import ir.oveissi.searchmovies.interactors.remote.GeneralApiException;
import ir.oveissi.searchmovies.pojo.Movie;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class MovieSearchPresenter implements MovieSearchContract.Presenter {


    private MovieSearchContract.View viewLayer;
    private CompositeSubscription compositeSubscription;
    private final MovieInteractor mMovieInteractor;
    private static final String TAG="MovieSearchPresenter";

    @Inject
    public MovieSearchPresenter(MovieInteractor mMovieInteractor) {
        this.compositeSubscription = new CompositeSubscription();
        this.mMovieInteractor = mMovieInteractor;
    }

    @Override
    public void onLoadMoviesByTitle(String title,int page) {
        Subscription mSubscription=
                mMovieInteractor.getMoviesByTitle(title,page)
                        .subscribe(new Observer<List<Movie>>() {
                            @Override
                            public void onCompleted() {
                                Log.d(TAG, "onCompleted: ");
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (e instanceof HttpException) {
                                    Log.d(TAG, "onError StatusCode: "+((HttpException) e).code());
                                }
                                else if(e instanceof GeneralApiException)
                                {
                                    Log.d(TAG, "onError message: "+((GeneralApiException) e).message);
                                }
                                else
                                {
                                    Log.d(TAG, "onError");
                                }
                                viewLayer.showToast("خطا رخ داد.");

                            }

                            @Override
                            public void onNext(List<Movie> movies) {
                                Log.d(TAG, "onNext");
                                viewLayer.hideLoadingForMovies();
                                viewLayer.showMoreMovies(movies);
                            }
                        });
        compositeSubscription.add(mSubscription);
    }

    @Override
    public void onSearchButtonClick(String terms) {
        if(terms.length()<=2)
        {
            viewLayer.showToast("لطفا بیشتر از 2 کاراکتر وارد کنید.");
        }

        viewLayer.showLoadingForMovies();
        viewLayer.clearMovies();
        onLoadMoviesByTitle(terms,1);
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeSubscription.clear();
    }

    @Override
    public void onViewAttached(MovieSearchContract.View view) {
        viewLayer=view;
    }
}
