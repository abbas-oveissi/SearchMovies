package ir.oveissi.searchmovies.customviews;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import ir.oveissi.searchmovies.R;

public class LoadingLayout extends RelativeLayout {

    public static final int STATE_LOADING = 1;
    public static final int STATE_SHOW_DATA = 2;


    public Context mContext;
    public  ProgressBar pb;
    public View mainLayout;
    private int mState;


    public LoadingLayout(Context context) {
        super(context);
        initializeViews(context,null);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);
    }

    public LoadingLayout(Context context,AttributeSet attrs,int defStyle) {
        super(context, attrs, defStyle);
        initializeViews(context, attrs);
    }


    private void initializeViews(Context context,AttributeSet attrs) {
        this.mContext=context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mainLayout=getChildAt(0);
        pb=new ProgressBar(mContext);
        pb.setIndeterminate(true);
        LayoutParams lp=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        pb.setLayoutParams(lp);
        pb.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        addView(pb);
    }

    public void setState(int mState)
    {
        this.mState = mState;
        if(mState==STATE_LOADING)
        {
            mainLayout.setVisibility(GONE);
            pb.setVisibility(VISIBLE);
        }
        else if(mState==STATE_SHOW_DATA)
        {
            pb.setVisibility(GONE);
            mainLayout.setAlpha(0);
            mainLayout.setVisibility(VISIBLE);
            mainLayout.animate()
                    .alpha(1)
                    .setInterpolator(new FastOutLinearInInterpolator())
                    .setDuration(500)  ;
        }
    }

    public int getState() {
        return mState;
    }
}
