package ir.oveissi.searchmovies.utils.customviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by abbas on 6/14/17.
 */

public class EndlessLinearLayoutRecyclerview extends RecyclerView {
    public EndlessLinearLayoutRecyclerview(Context context) {
        super(context);
    }

    public EndlessLinearLayoutRecyclerview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EndlessLinearLayoutRecyclerview(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    AdvancedEndlessRecyclerOnScrollListener aeros;

    @Override
    public void setLayoutManager(LayoutManager layout) {

        if(!(layout instanceof LinearLayoutManager))
        {
            throw new RuntimeException();
        }

        aeros=new AdvancedEndlessRecyclerOnScrollListener(layout) {
            @Override
            public void onLoadMore() {
                if(onLoadMoreListener!=null)
                    onLoadMoreListener.onLoadMore();
            }
        };

        addOnScrollListener(aeros);
        super.setLayoutManager(layout);
    }

    private onLoadMoreListener onLoadMoreListener;
    public void setOnLoadMoreListener(EndlessLinearLayoutRecyclerview.onLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }
    public interface onLoadMoreListener
    {
        void onLoadMore();
    }


    public void setLoading(boolean enable)
    {
        this.aeros.setLoading(enable);
    }


    public abstract class AdvancedEndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
        private int visibleThreshold = 3;
        private int lastVisibleItem, totalItemCount;
        private boolean loading;
        private RecyclerView.LayoutManager linearLayoutManager;
        public AdvancedEndlessRecyclerOnScrollListener(RecyclerView.LayoutManager linearLayoutManager)
        {
            this.linearLayoutManager = linearLayoutManager;
        }
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            totalItemCount = linearLayoutManager.getItemCount();
            lastVisibleItem =((LinearLayoutManager) linearLayoutManager).findLastVisibleItemPosition();
            if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                onLoadMore();
                loading = true;
            }
        }

        public void setLoading(boolean enable)
        {
            this.loading=enable;
        }

        public abstract void onLoadMore();

    }

}
