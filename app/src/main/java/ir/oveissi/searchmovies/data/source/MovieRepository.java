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

package ir.oveissi.searchmovies.data.source;


import java.util.List;

import ir.oveissi.searchmovies.data.Movie;
import rx.Observable;


public class MovieRepository implements MovieDataSource {

    private static MovieRepository INSTANCE = null;

    private final MovieDataSource mMovieRemoteDataSource;


    // Prevent direct instantiation.
    private MovieRepository(MovieDataSource mMovieRemoteDataSource) {
        this.mMovieRemoteDataSource = mMovieRemoteDataSource;
    }

    public static MovieRepository getInstance(MovieDataSource mPostsRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new MovieRepository(mPostsRemoteDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<List<Movie>> getMoviesByTitle(String title,Integer page) {
        return this.mMovieRemoteDataSource.getMoviesByTitle(title,page);
    }

    @Override
    public Observable<Movie> getMovieByID( String movieId) {
        return null;
    }


//    @Override
//    public Observable<List<Task>> getTasks() {
//        // Respond immediately with cache if available and not dirty
//        if (mCachedTasks != null && !mCacheIsDirty) {
//            return Observable.from(mCachedTasks.values()).toList();
//        } else if (mCachedTasks == null) {
//            mCachedTasks = new LinkedHashMap<>();
//        }
//
//        Observable<List<Task>> remoteTasks = mTasksRemoteDataSource
//                .getTasks()
//                .flatMap(new Func1<List<Task>, Observable<Task>>() {
//                    @Override
//                    public Observable<Task> call(List<Task> tasks) {
//                        return Observable.from(tasks);
//                    }
//                })
//                .doOnNext(new Action1<Task>() {
//                    @Override
//                    public void call(Task task) {
//                        mTasksLocalDataSource.saveTask(task);
//                        mCachedTasks.put(task.getId(), task);
//                    }
//                })
//                .toList()
//                .doOnCompleted(new Action0() {
//                    @Override
//                    public void call() {
//                        mCacheIsDirty = false;
//                    }
//                });
//        if (mCacheIsDirty) {
//            return remoteTasks;
//        } else {
//            // Query the local storage if available. If not, query the network.
//            Observable<List<Task>> localTasks = mTasksLocalDataSource.getTasks();
//            return Observable.concat(localTasks, remoteTasks).first();
//        }
//    }
//
//
//
//    /**
//     * Gets tasks from local data source (sqlite) unless the table is new or empty. In that case it
//     * uses the network data source. This is done to simplify the sample.
//     */
//    @Override
//    public Observable<Task> getTask(@NonNull final String taskId) {
//        checkNotNull(taskId);
//
//        final Task cachedTask = getTaskWithId(taskId);
//
//        // Respond immediately with cache if available
//        if (cachedTask != null) {
//            return Observable.just(cachedTask);
//        }
//
//        // Load from server/persisted if needed.
//
//        // Is the task in the local data source? If not, query the network.
//        Observable<Task> localTask = mTasksLocalDataSource
//                .getTask(taskId)
//                .doOnNext(new Action1<Task>() {
//                    @Override
//                    public void call(Task task) {
//                        mCachedTasks.put(taskId, task);
//                    }
//                });
//        Observable<Task> remoteTask = mTasksRemoteDataSource
//                .getTask(taskId)
//                .doOnNext(new Action1<Task>() {
//                    @Override
//                    public void call(Task task) {
//                        mTasksLocalDataSource.saveTask(task);
//                        mCachedTasks.put(task.getId(), task);
//                    }
//                });
//
//        return Observable.concat(localTask, remoteTask).first();
//    }

}
