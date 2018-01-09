package br.com.ericksprengel.android.baking.ui.recipe;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import br.com.ericksprengel.android.baking.R;
import br.com.ericksprengel.android.baking.data.Step;
import br.com.ericksprengel.android.baking.data.source.RecipesDataSource;
import br.com.ericksprengel.android.baking.data.source.RecipesRepository;
import br.com.ericksprengel.android.baking.util.Inject;

public class StepFragment extends Fragment {

    private static final String ARG_RECIPE_ID = "recipe_id";
    private static final String ARG_STEP_ID = "step_id";

    private Step mStep;
    private TextView mDescription;
    private SimpleExoPlayerView mSimpleExoPlayerView;

    Target mThumbnailTarget = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            mSimpleExoPlayerView.setDefaultArtwork(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };

    public StepFragment() {}

    public static StepFragment newInstance(int recipeId, int stepId) {
        StepFragment fragment = new StepFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_RECIPE_ID, recipeId);
        args.putInt(ARG_STEP_ID, stepId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void loadStep(int recipeId, int stepId) {
        RecipesRepository mRepository = Inject.getRecipeRepository(getContext());
        mRepository.getStep(recipeId, stepId, new RecipesDataSource.LoadStepCallback() {
            @Override
            public void onStepLoaded(Step step) {
                mStep = step;
                mDescription.setText(mStep.getDescription());
                if(!TextUtils.isEmpty(mStep.getThumbnailURL())) {
                    Picasso.with(getContext())
                            .load(mStep.getThumbnailURL())
                            .into(mThumbnailTarget);
                }
                if(!TextUtils.isEmpty(mStep.getVideoURL())) {
                    loadVideo(Uri.parse(mStep.getVideoURL()));
                }
            }

            @Override
            public void onDataNotAvailable() {
                Snackbar.make(getView(), getString(R.string.internal_error), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        mDescription = rootView.findViewById(R.id.step_frag_description_textview);
        mSimpleExoPlayerView = rootView.findViewById(R.id.step_frag_simpleexoplayerview);

        int recipeId = getArguments().getInt(ARG_RECIPE_ID);
        int stepId = getArguments().getInt(ARG_STEP_ID);
        loadStep(recipeId, stepId);

        return rootView;
    }

    private void loadVideo(Uri videoUri) {
        // 1. Create a default TrackSelector
        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create the player
        SimpleExoPlayer player =
                ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

        // Bind the player to the view.
        mSimpleExoPlayerView.setPlayer(player);


        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), "yourApplicationName"), null);
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoUri);
        // Prepare the player with the source.
        player.prepare(videoSource);
    }
}
