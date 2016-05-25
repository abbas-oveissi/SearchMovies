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

package ir.oveissi.searchmovies.data.source.remote;

import java.util.List;

import ir.oveissi.searchmovies.data.Movie;
import ir.oveissi.searchmovies.data.source.MovieDataSource;
import ir.oveissi.searchmovies.data.source.remote.jsontemplate.TmpMovies;
import rx.Observable;
import rx.functions.Func1;

/**
 * Implementation of the data source that adds a latency simulating network.
 */
public class MovieRemoteDataSource implements MovieDataSource {

    private static MovieRemoteDataSource INSTANCE;

    public static MovieRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MovieRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private MovieRemoteDataSource() {}


//    @Override
//    public Observable<List<Task>> getTasks() {
//        return Observable
//                .from(TASKS_SERVICE_DATA.values())
//                .delay(SERVICE_LATENCY_IN_MILLIS, TimeUnit.MILLISECONDS)
//                .toList();
//    }
//
//    @Override
//    public Observable<Task> getTask(@NonNull String taskId) {
//        final Task task = TASKS_SERVICE_DATA.get(taskId);
//        if(task != null) {
//            return Observable.just(task).delay(SERVICE_LATENCY_IN_MILLIS, TimeUnit.MILLISECONDS);
//        } else {
//            return Observable.empty();
//        }
//    }


    @Override
    public Observable<List<Movie>> getMoviesByTitle(String title,Integer page) {
        return ApiClient.getClient().create(ApiInterface.class).getMoviesByTitle(title,page)
                .flatMap(new Func1<TmpMovies,Observable<List<Movie>>>() {
                @Override
                public Observable<List<Movie>> call(TmpMovies mMovies) {
                    return Observable.from(mMovies.Search).toList();
                }
        });
    }

    @Override
    public Observable<Movie> getMovieByID( String movieId) {
        return null;
    }
}
