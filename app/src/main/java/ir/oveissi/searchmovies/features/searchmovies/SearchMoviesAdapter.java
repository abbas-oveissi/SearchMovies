package ir.oveissi.searchmovies.features.searchmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.oveissi.searchmovies.R;
import ir.oveissi.searchmovies.pojo.Movie;
import ir.oveissi.searchmovies.utils.Constants;

public class SearchMoviesAdapter extends RecyclerView.Adapter<SearchMoviesAdapter.ViewHolder> {
    private Context mContext;
    private List<Movie> itemsData;

    public SearchMoviesAdapter(Context mContext, List<Movie> itemsData) {
        this.mContext = mContext;
        this.itemsData = itemsData;
    }

    public void clear()
    {
        this.itemsData.clear();
        notifyDataSetChanged();
    }
    public void addItem(Movie post)
    {
        this.itemsData.add(post);
        notifyItemInserted(this.itemsData.size()-1);
    }

    @Override
    public SearchMoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_movie,  parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.tvMovieTitle.setText(itemsData.get(position).original_title);
        viewHolder.tvMovieType.setText(itemsData.get(position).overview);
        Picasso.with(mContext)
                .load(Constants.BASE_IMAGE_URL+itemsData.get(position).poster_path)
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.imPoster);


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMovieTitle;
        public TextView tvMovieType;
        public ImageView imPoster;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            tvMovieTitle = (TextView) itemLayoutView.findViewById(R.id.tvMovieTitle);
            tvMovieType = (TextView) itemLayoutView.findViewById(R.id.tvMovieType);
            imPoster = (ImageView) itemLayoutView.findViewById(R.id.imPoster);
        }
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }
}