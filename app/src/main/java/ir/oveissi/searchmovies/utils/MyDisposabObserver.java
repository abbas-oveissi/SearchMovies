package ir.oveissi.searchmovies.utils;

import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.subscribers.ResourceSubscriber;

public class MyDisposabObserver<I> extends ResourceSubscriber<I> {

    private final BehaviorProcessor<I> subject;

    public MyDisposabObserver(BehaviorProcessor<I> subject) {
        this.subject = subject;
    }

    @Override
    public void onNext(I value) {
        subject.onNext(value);
    }

    @Override
    public void onError(Throwable e) {
        subject.onError(e);
    }

    @Override
    public void onComplete() {
        subject.onComplete();
    }
}
