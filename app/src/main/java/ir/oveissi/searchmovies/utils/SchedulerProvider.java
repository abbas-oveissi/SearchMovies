package ir.oveissi.searchmovies.utils;

import rx.Scheduler;

/**
 * Created by Abbas on 30/04/16.
 */
public interface SchedulerProvider {

    Scheduler mainThread();

    Scheduler backgroundThread();

}
