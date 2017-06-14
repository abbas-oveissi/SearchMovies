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


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import ir.oveissi.searchmovies.interactors.remote.SearchMoviesApiService;
import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.pojo.TmpMovies;
import ir.oveissi.searchmovies.utils.SchedulerProvider;


public class MovieInteractorImpl implements MovieInteractor {

    private final SearchMoviesApiService searchMoviesApiService;
    private final SchedulerProvider scheduler;

    @Inject
    public MovieInteractorImpl(SearchMoviesApiService searchMoviesApiService, SchedulerProvider scheduler) {
        this.searchMoviesApiService = searchMoviesApiService;
        this.scheduler = scheduler;
    }

    @Override
    public Observable<List<Movie>> getMoviesByTitle(String title, Integer page) {
        return this.searchMoviesApiService.getMoviesByTitle(title,page)
                .map(new Function<TmpMovies, List<Movie>>() {
                    @Override
                    public List<Movie> apply(TmpMovies tmpMovies) {
                        return tmpMovies.data;
                    }
                })
                .subscribeOn(scheduler.backgroundThread())
                .observeOn(scheduler.mainThread());
    }

    @Override
    public Observable<Movie> getMovieByID( String movieId) {
        return this.searchMoviesApiService.getMovieById(movieId)
                .subscribeOn(scheduler.backgroundThread())
                .observeOn(scheduler.mainThread());
    }

}
