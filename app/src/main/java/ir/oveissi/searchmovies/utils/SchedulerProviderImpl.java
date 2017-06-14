package ir.oveissi.searchmovies.utils;


import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Abbas on 30/04/16.
 */
public class SchedulerProviderImpl implements SchedulerProvider {

    public SchedulerProviderImpl() {
    }

    @Override
    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler backgroundThread() {
        return Schedulers.io();
    }

}
