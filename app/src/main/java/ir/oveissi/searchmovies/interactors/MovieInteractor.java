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

package ir.oveissi.searchmovies.interactors;


import javax.inject.Inject;

import io.reactivex.Observable;
import ir.oveissi.searchmovies.interactors.remote.SearchMoviesApiService;
import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.pojo.Pagination;
import ir.oveissi.searchmovies.utils.SchedulerProvider;


public class MovieInteractor {

    private final SearchMoviesApiService searchMoviesApiService;
    private final SchedulerProvider scheduler;

    @Inject
    public MovieInteractor(SearchMoviesApiService searchMoviesApiService, SchedulerProvider scheduler) {
        this.searchMoviesApiService = searchMoviesApiService;
        this.scheduler = scheduler;
    }

    public Observable<Pagination<Movie>> getMoviesByTitle(String title, Integer page) {
        return this.searchMoviesApiService.getMoviesByTitle(title, page)
                .subscribeOn(scheduler.backgroundThread())
                .observeOn(scheduler.mainThread());
    }

    public Observable<Movie> getMovieByID(String movieId) {
        return this.searchMoviesApiService.getMovieById(movieId)
                .subscribeOn(scheduler.backgroundThread())
                .observeOn(scheduler.mainThread());
    }

}
