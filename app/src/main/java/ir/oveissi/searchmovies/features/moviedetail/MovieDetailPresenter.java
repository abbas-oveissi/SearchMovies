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

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import ir.oveissi.searchmovies.interactors.MovieInteractor;
import ir.oveissi.searchmovies.pojo.Movie;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class MovieDetailPresenter  implements MovieDetailContract.Presenter {

    private WeakReference<MovieDetailContract.View> mainView;
    private CompositeSubscription compositeSubscription;
    private final MovieInteractor mMovieInteractor;
    private static final String TAG="MovieDetailPresenter";

    @Inject
    public MovieDetailPresenter(MovieInteractor mMovieInteractor) {
        this.compositeSubscription = new CompositeSubscription();
        this.mMovieInteractor = mMovieInteractor;
    }

    @Override
    public void getMovieDetailFromWebservice(String id) {
        checkCompositeSubscription();
        Subscription mSubscription=
                mMovieInteractor.getMovieByID(id)
                        .subscribe(new Observer<Movie>() {
                            @Override
                            public void onCompleted() {
                                Log.d(TAG, "onCompleted: ");
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (e instanceof HttpException) {
                                    Log.d(TAG, "onError StatusCode: "+((HttpException) e).code());
                                }
                                Log.d(TAG, "onError");
                            }

                            @Override
                            public void onNext(Movie movie) {
                                Log.d(TAG, "onNext");
                                if(doIfView()) {
                                    mainView.get().showMovieDetail(movie);
                                    mainView.get().showMovieDetail(movie);
                                }
                            }
                        });
        compositeSubscription.add(mSubscription);
    }


    public void attachView(MovieDetailContract.View view) {
        this.mainView = new WeakReference<>(view);
    }

    public void detachView() {
        this.mainView.clear();
        if (!this.compositeSubscription.isUnsubscribed())
            this.compositeSubscription.unsubscribe();
    }

    public boolean doIfView() {
        return this.mainView != null && this.mainView.get() != null;
    }

    public void checkCompositeSubscription() {
        if (this.compositeSubscription == null || this.compositeSubscription.isUnsubscribed())
            this.compositeSubscription = new CompositeSubscription();
    }
}
