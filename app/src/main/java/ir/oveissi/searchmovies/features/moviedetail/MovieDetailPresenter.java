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

package ir.oveissi.searchmovies.features.moviedetail;

import android.util.Log;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import ir.oveissi.searchmovies.interactors.MovieInteractor;
import ir.oveissi.searchmovies.interactors.remote.exceptions.GeneralApiException;
import ir.oveissi.searchmovies.pojo.Movie;
import retrofit2.HttpException;

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private MovieDetailContract.View viewLayer;
    private CompositeDisposable compositeDisposable;
    private final MovieInteractor mMovieInteractor;
    private static final String TAG = "MovieDetailPresenter";

    @Inject
    public MovieDetailPresenter(MovieInteractor mMovieInteractor) {
        this.compositeDisposable = new CompositeDisposable();
        this.mMovieInteractor = mMovieInteractor;
    }

    @Override
    public void onLoadMovieDetail(String id) {
        viewLayer.showLoading();
        Disposable disposable =
                mMovieInteractor.getMovieByID(id)
                        .subscribeWith(new DisposableObserver<Movie>() {
                            @Override
                            public void onComplete() {
                                Log.d(TAG, "onCompleted: ");
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (e instanceof HttpException) {
                                    viewLayer.showError("StatusCode: " + ((HttpException) e).code());
                                } else if (e instanceof GeneralApiException) {
                                    viewLayer.showError(((GeneralApiException) e).message);
                                } else {
                                    viewLayer.showError(e.getMessage());
                                }
                            }

                            @Override
                            public void onNext(Movie movie) {
                                Log.d(TAG, "onNext");
                                viewLayer.showData();
                                viewLayer.showMovieDetail(movie);
                            }
                        });
        compositeDisposable.add(disposable);
    }


    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void onViewAttached(MovieDetailContract.View view) {
        viewLayer = view;
    }
}
