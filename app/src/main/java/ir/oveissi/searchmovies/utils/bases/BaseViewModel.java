package ir.oveissi.searchmovies.utils.bases;

import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel{

    private CompositeDisposable mCompositeDisposable  =new  CompositeDisposable();

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
    }

    protected CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

}
