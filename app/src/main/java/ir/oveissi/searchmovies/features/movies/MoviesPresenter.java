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

import android.util.Log;

import java.util.List;

import ir.oveissi.searchmovies.data.Movie;
import ir.oveissi.searchmovies.data.source.MovieRepository;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MoviesPresenter implements MoviesContract.Presenter {

    private final MovieRepository mPostsRepository;
//
    private final MoviesContract.View mtView;
//
//    private TasksFilterType mCurrentFiltering = TasksFilterType.ALL_TASKS;
//
//    private boolean mFirstLoad = true;
    private CompositeSubscription mSubscriptions;
    private static final String TAG="MoviesPresenter";

    //
    public MoviesPresenter(MovieRepository postsRepository, MoviesContract.View tView) {
        mPostsRepository = postsRepository;
        mtView = tView;
        mSubscriptions = new CompositeSubscription();
        mtView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        //loadTasks(false);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void getMoviesByTitle(String title,int page) {
        mSubscriptions.add(
                mPostsRepository.getMoviesByTitle(title,page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Movie>>() {
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
                    public void onNext(List<Movie> movies) {
                        Log.d(TAG, "onNext");
                        mtView.hideLoadingForMovies();
                        mtView.showMoreMovies(movies);
                    }
                })
        );

    }

    @Override
    public void performSearch(String terms) {
        if(terms.length()<=3)
        {
            mtView.showToast("لطفا بیشتر از 3 کاراکتر وارد کنید.");
            return;
        }
        mtView.showLoadingForMovies();
        mtView.clearMovies();
        getMoviesByTitle(terms,1);
    }

//    @Override
//    public void result(int requestCode, int resultCode) {
//        // If a task was successfully added, show snackbar
//        if (AddEditTaskActivity.REQUEST_ADD_TASK == requestCode && Activity.RESULT_OK == resultCode) {
//            mTasksView.showSuccessfullySavedMessage();
//        }
//    }

//      private void loadTasks(boolean forceUpdate, final boolean showLoadingUI) {
//        mSubscriptions.clear();
//        Subscription subscription = mTasksRepository
//                .getTasks()
//                .flatMap(new Func1<List<Task>, Observable<Task>>() {
//                    @Override
//                    public Observable<Task> call(List<Task> tasks) {
//                        return Observable.from(tasks);
//                    }
//                })
//                .filter(new Func1<Task, Boolean>() {
//                    @Override
//                    public Boolean call(Task task) {
//                        switch (mCurrentFiltering) {
//                            case ACTIVE_TASKS:
//                                return task.isActive();
//                            case COMPLETED_TASKS:
//                                return task.isCompleted();
//                            case ALL_TASKS:
//                            default:
//                                return true;
//                        }
//                    }
//                })
//                .toList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<List<Task>>() {
//                    @Override
//                    public void onCompleted() {
//                        mTasksView.setLoadingIndicator(false);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mTasksView.showLoadingTasksError();
//                    }
//
//                    @Override
//                    public void onNext(List<Task> tasks) {
//                        processTasks(tasks);
//                    }
//                });
//        mSubscriptions.add(subscription);
//    }

//    @Override
//    public void activateTask(@NonNull Task activeTask) {
//        checkNotNull(activeTask, "activeTask cannot be null!");
//        mTasksRepository.activateTask(activeTask);
//        mTasksView.showTaskMarkedActive();
//        loadTasks(false, false);
//    }



}
