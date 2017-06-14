package ir.oveissi.searchmovies.utils;


import io.reactivex.Scheduler;

/**
 * Created by Abbas on 30/04/16.
 */
public interface SchedulerProvider {

    Scheduler mainThread();

    Scheduler backgroundThread();

}
