package ir.oveissi.searchmovies.features.moviesearch;

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

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.ViewHolder> {
    private Context mContext;
    private List<Movie> itemsData;

    MovieSearchAdapter(Context mContext, List<Movie> itemsData) {
        this.mContext = mContext;
        this.itemsData = itemsData;
    }

    void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    ItemClickListener itemClickListener;
    public interface ItemClickListener {
        void ItemClicked(int position, Movie item,ImageView imPoster);
    }


    void clear()
    {
        this.itemsData.clear();
        notifyDataSetChanged();
    }
    void addItem(Movie post)
    {
        this.itemsData.add(post);
        notifyItemInserted(this.itemsData.size()-1);
    }

    @Override
    public MovieSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_movie,  parent, false);
        return new ViewHolder(itemLayoutView,mContext, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Movie tempItem=itemsData.get(position);
        viewHolder.bind(tempItem);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Context mcontext;
        TextView tvMovieTitle;
        final MovieSearchAdapter movieSearchAdapter;
        ImageView imPoster;
        ViewHolder(View itemLayoutView, Context context, MovieSearchAdapter movieSearchAdapter) {
            super(itemLayoutView);
            this.mcontext = context;
            tvMovieTitle = itemLayoutView.findViewById(R.id.tvMovieTitle);
            this.movieSearchAdapter = movieSearchAdapter;
            imPoster = itemLayoutView.findViewById(R.id.imPoster);
            itemLayoutView.setOnClickListener(this);

        }

        void bind(Movie item)
        {
            tvMovieTitle.setText(item.title);
            Picasso.with(mcontext)
                    .load(item.poster)
                    .placeholder(R.drawable.placeholder)
                    .into(imPoster);
        }


        @Override
        public void onClick(View v) {
            if(movieSearchAdapter.itemClickListener!=null)
            {
                movieSearchAdapter.itemClickListener.ItemClicked(getAdapterPosition(),
                        movieSearchAdapter.itemsData.get(getAdapterPosition()),imPoster);
            }

        }
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }
}