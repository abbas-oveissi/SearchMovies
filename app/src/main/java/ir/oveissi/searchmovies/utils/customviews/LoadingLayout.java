package ir.oveissi.searchmovies.utils.customviews;

import android.content.Context;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import ir.oveissi.searchmovies.R;
import ir.oveissi.searchmovies.utils.Utility;


public class LoadingLayout extends RelativeLayout {

    public static final int STATE_LOADING = 1;
    public static final int STATE_SHOW_DATA = 2;
    public static final int STATE_SHOW_Error = 3;
    public Context mContext;
    //public  ProgressBar pb;
    public View vError;
    public View mainLayout;
    public String mError="";
    private ProgressWheel wheel;


    private onErrorClickListener listener;
    private int mState=2;

    public void setListener(onErrorClickListener listener) {
        this.listener = listener;
    }

    public interface onErrorClickListener
    {
        public void onClick();
    }

    public LoadingLayout(Context context) {
        super(context);
        initializeViews(context,null);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeViews(context, attrs);
    }


    String whereShowLoading="";
    private void initializeViews(Context context,AttributeSet attrs) {
        this.mContext=context;
//            if(attrs!=null)
//            {
//                TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingLayout, 0, 0);
//                whereShowLoading = a.getString(R.styleable.LoadingLayout_whereShowLoading);
//                a.recycle();
//            }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        vError=inflater.inflate(R.layout.row_error,null);
        vError.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null)
                    listener.onClick();
            }
        });


        mainLayout=getChildAt(0);

        wheel = new ProgressWheel(mContext);
        wheel.setBarColor(mContext.getResources().getColor(R.color.colorPrimary));
        wheel.spin();
        wheel.setCircleRadius(Utility.dpToPx(60));
        wheel.setBarWidth(Utility.dpToPx(4));
        wheel.setSpinSpeed(.5f);
        LayoutParams lp=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        if(whereShowLoading!=null&&whereShowLoading.equals("top"))
        {
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

        }
        else
        {
            lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        }
        wheel.setLayoutParams(lp);
        //wheel.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        vError.setLayoutParams(lp);


        addView(wheel);
        addView(vError);


        setState(LoadingLayout.STATE_SHOW_DATA);

    }

    public int getState() {
        return mState;
    }

    public void setState(int mState)
    {
        this.mState = mState;
        if(mState==STATE_LOADING)
        {
            mainLayout.setVisibility(GONE);
            wheel.setVisibility(VISIBLE);
            vError.setVisibility(GONE);
        }
        else if(mState==STATE_SHOW_DATA)
        {
            wheel.setVisibility(GONE);
            vError.setVisibility(GONE);

            mainLayout.setAlpha(0);
            mainLayout.setVisibility(VISIBLE);
            mainLayout.animate()
                    .alpha(1)
                    .setInterpolator(new FastOutLinearInInterpolator())
                    .setDuration(500)  ;
            if(isInEditMode())
            {
                mainLayout.setAlpha(1);

            }
        }
        else if(mState==STATE_SHOW_Error)
        {
            mainLayout.setVisibility(GONE);
            wheel.setVisibility(GONE);
            vError.setVisibility(VISIBLE);

        }

    }


    public void setError(String mError)
    {
        this.mError=mError;
        TextView tvError=(TextView) vError.findViewById(R.id.tvError);
        tvError.setText(mError);
        setState(STATE_SHOW_Error);
    }

}