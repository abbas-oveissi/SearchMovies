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

/**
 * Main entry point for accessing tasks data.
 * <p>
 */
public interface MovieDataSource {

    Observable<List<Movie>> getMoviesByTitle(String title,Integer page);

    Observable<Movie> getMovieByID( String movieId);

}
