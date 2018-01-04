package br.com.ericksprengel.android.baking.ui.recipe;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.ericksprengel.android.baking.data.Step;

/**
 * Created by erick.sprengel on 04/01/2018.
 */

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.StepViewHolder> {




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Movie mMovie;
        ImageView mPoster;

        ViewHolder(View v) {
            super(v);
            mPoster = v.findViewById(R.id.movie_list_ac_poster_imageview);
            v.setOnClickListener(this);
        }

        void updateData(Movie movie) {
            mMovie = movie;
            Picasso.with(mPoster.getContext())
                    .load(mMovie.getPosterThumbnailUrl(mPoster.getResources()))
                    .placeholder(R.drawable.movie_poster_thumbnail_placeholder)
                    .into(mPoster);
        }

        @Override
        public void onClick(View view) {
            MovieListAdapter.this.mOnClickMovieListener.onMovieClick(mMovie);
        }
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Step mStep;

        private final TextView mShortDescription;

        StepViewHolder(View v) {
            super(v);
            mShortDescription = v.findViewById(R.id.recipe_item_ac_short_description_textview);
            v.setOnClickListener(this);
        }

        void updateData(Movie movie) {
            mMovie = movie;
            Picasso.with(mPoster.getContext())
                    .load(mMovie.getPosterThumbnailUrl(mPoster.getResources()))
                    .placeholder(R.drawable.movie_poster_thumbnail_placeholder)
                    .into(mPoster);

            mTitle.setText(mMovie.getTitle());
            mReleaseDate.setText(mMovie.getReleaseYear());
            mOverview.setText(mMovie.getOverview());
            mVoteAverage.setRating((float) mMovie.getVoteAverage()/2);
            mFavorite.setChecked(mMovie.isFavorite());
        }

        @Override
        public void onClick(View view) {
            mOnMovieFavoriteClickListener.onMovieFavoriteClick(mMovie, mFavorite.isChecked());
        }
    }




    public interface OnStepClickListener {
        void onStepClick(Step step);
    }

    private List<Step> mSteps;
    private OnStepClickListener mOnClickStepListener;

    MovieListAdapter(OnMovieClickListener listener) {
        this.mOnClickMovieListener = listener;
    }

    void setMovies(List<Movie> movies) {
        this.mMovies = movies;
        notifyDataSetChanged();
    }

    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_movie_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Step step = mSteps.get(position);
        holder.updateData(step);
    }

    @Override
    public int getItemCount() {
        return mSteps == null ? 0 : mSteps.size();
    }

}
