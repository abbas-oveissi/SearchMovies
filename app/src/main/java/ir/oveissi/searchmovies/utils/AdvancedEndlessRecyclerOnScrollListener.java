package ir.oveissi.searchmovies.utils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


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
