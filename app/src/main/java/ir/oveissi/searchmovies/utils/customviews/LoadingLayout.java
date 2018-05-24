package ir.oveissi.searchmovies.utils.customviews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import ir.oveissi.searchmovies.R;
import ir.oveissi.searchmovies.utils.Utility;


public class LoadingLayout extends RelativeLayout {


    public static final int STATE_LOADING = 1;
    public static final int STATE_SHOW_DATA = 2;
    public static final int STATE_SHOW_ERROR = 3;
    public static final int STATE_EMPTY = 4;

    LayoutInflater inflater;
    Button btnRetry;
    public Context mContext;
    public View vError;
    public View mainLayout;
    public String mError = "";
    private ProgressWheel wheel;

    private onErrorClickListener listener;
    private int mState = 2;
    private View vEmpty;
    String whereShowLoading = "";

    public void setErrorClickListener(onErrorClickListener listener) {
        this.listener = listener;
    }

    public interface onErrorClickListener {
        public void onClick();
    }

    public LoadingLayout(Context context) {
        super(context);
        initializeViews(context, null);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeViews(context, attrs);
    }

    private void initializeViews(Context context, AttributeSet attrs) {
        this.mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mainLayout = getChildAt(0);

        vError = inflater.inflate(R.layout.row_loadinglayout_error, null);
        btnRetry = (Button) vError.findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick();
            }
        });


        vEmpty = inflater.inflate(R.layout.row_loadinglayout_empty, null);


        wheel = new ProgressWheel(mContext);
        wheel.setBarColor(mContext.getResources().getColor(R.color.colorPrimary));
        wheel.spin();
        wheel.setCircleRadius(Utility.dpToPx(60));
        wheel.setBarWidth(Utility.dpToPx(4));
        wheel.setSpinSpeed(.5f);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (whereShowLoading != null && whereShowLoading.equals("top")) {
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

        } else {
            lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        }
        wheel.setLayoutParams(lp);
        //wheel.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        vError.setLayoutParams(lp);
        vEmpty.setLayoutParams(lp);


        addView(wheel);
        addView(vError);
        addView(vEmpty);


        setState(LoadingLayout.STATE_SHOW_DATA);

    }

    public int getState() {
        return mState;
    }

    public void setState(int mState) {
        this.mState = mState;
        if (mState == STATE_LOADING) {
            mainLayout.setVisibility(GONE);
            wheel.setVisibility(VISIBLE);
            vError.setVisibility(GONE);
            vEmpty.setVisibility(GONE);
        } else if (mState == STATE_SHOW_DATA) {
            wheel.setVisibility(GONE);
            vError.setVisibility(GONE);
            vEmpty.setVisibility(GONE);

            mainLayout.setAlpha(0);
            mainLayout.setVisibility(VISIBLE);
            mainLayout.animate()
                    .alpha(1)
                    .setInterpolator(new FastOutLinearInInterpolator())
                    .setDuration(500);
            if (isInEditMode()) {
                mainLayout.setAlpha(1);

            }
        } else if (mState == STATE_EMPTY) {
            mainLayout.setVisibility(GONE);
            wheel.setVisibility(GONE);
            vError.setVisibility(GONE);
            vEmpty.setVisibility(VISIBLE);
        } else if (mState == STATE_SHOW_ERROR) {
            mainLayout.setVisibility(GONE);
            wheel.setVisibility(GONE);
            vEmpty.setVisibility(GONE);
            vError.setVisibility(VISIBLE);

        }

    }

    public void setErrorText(String mError) {
        this.mError = mError;
        TextView tvError = (TextView) vError.findViewById(R.id.tvError);
        tvError.setText(mError);
        setState(STATE_SHOW_ERROR);
    }

    public void setErrorText(String mError, Drawable drawable) {
        this.mError = mError;
        TextView tvError = (TextView) vError.findViewById(R.id.tvError);
        btnRetry = (Button) vError.findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick();
            }
        });
        btnRetry.setVisibility(VISIBLE);
        ImageView im = (ImageView) vError.findViewById(R.id.imgError);
        im.setImageDrawable(drawable);
        tvError.setText(mError);

        setState(STATE_SHOW_ERROR);
    }

    public void setEmptyText(String emptyText) {
        TextView tv = (TextView) vEmpty.findViewById(R.id.tvEmpty);
        ImageView im = (ImageView) vEmpty.findViewById(R.id.imEmpty);
        im.setVisibility(GONE);
        tv.setText(emptyText);
        setState(STATE_EMPTY);
    }

    public void setEmptyText(String emptyText, Drawable drawable) {
        TextView tv = (TextView) vEmpty.findViewById(R.id.tvEmpty);
        ImageView im = (ImageView) vEmpty.findViewById(R.id.imEmpty);
        im.setImageDrawable(drawable);
        tv.setText(emptyText);
        setState(STATE_EMPTY);
    }

    public void setEmptyText(String emptyText, int drawable) {
        TextView tv = (TextView) vEmpty.findViewById(R.id.tvEmpty);
        ImageView im = (ImageView) vEmpty.findViewById(R.id.imEmpty);
        im.setImageResource(drawable);
        tv.setText(emptyText);
        setState(STATE_EMPTY);
    }

}
