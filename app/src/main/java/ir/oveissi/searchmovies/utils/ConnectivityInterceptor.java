package ir.oveissi.searchmovies.utils;

import android.content.Context;

import java.io.IOException;

import javax.inject.Inject;

import ir.oveissi.searchmovies.interactors.remote.exceptions.NoConnectivityException;
import ir.oveissi.searchmovies.utils.Utility;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ConnectivityInterceptor implements Interceptor {

    private Context mContext;

    @Inject
    public ConnectivityInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!Utility.isOnline(mContext)) {
            throw new NoConnectivityException();
        }

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }

}
