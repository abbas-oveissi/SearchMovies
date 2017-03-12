package ir.oveissi.searchmovies.features.moviesearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.oveissi.searchmovies.R;
import ir.oveissi.searchmovies.features.moviedetail.MovieDetailActivity;
import ir.oveissi.searchmovies.pojo.Movie;

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.ViewHolder> {
    private Context mContext;
    private List<Movie> itemsData;

    public MovieSearchAdapter(Context mContext, List<Movie> itemsData) {
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
    public MovieSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_movie,  parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView,mContext);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Movie tempItem=itemsData.get(position);

        viewHolder.item=tempItem;
        viewHolder.tvMovieTitle.setText(tempItem.title);
        viewHolder.tvMovieType.setText(tempItem.country);
        Picasso.with(mContext)
                .load(tempItem.poster)
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.imPoster);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Movie item;
        public Context mcontext;
        public TextView tvMovieTitle;
        public TextView tvMovieType;
        public ImageView imPoster;
        public ViewHolder(View itemLayoutView,Context context) {
            super(itemLayoutView);
            this.mcontext = context;
            tvMovieTitle = (TextView) itemLayoutView.findViewById(R.id.tvMovieTitle);
            tvMovieType = (TextView) itemLayoutView.findViewById(R.id.tvMovieType);
            imPoster = (ImageView) itemLayoutView.findViewById(R.id.imPoster);

            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(v.getContext(),MovieDetailActivity.class);
                    i.putExtra("movie_id",String.valueOf(item.id));
                    i.putExtra("image_path",item.poster);
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
                    {
                        ActivityOptionsCompat option =
                                ActivityOptionsCompat.makeSceneTransitionAnimation(
                                        (Activity)mcontext,imPoster,"imPoster");
                        ((Activity)mcontext).startActivity(i,option.toBundle());


                    }
                    else
                    {
                        v.getContext().startActivity(i);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }
}