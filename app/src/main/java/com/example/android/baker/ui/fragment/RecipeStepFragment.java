package com.example.android.baker.ui.fragment;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.android.baker.R;
import com.example.android.baker.model.RecipeStep;
import com.example.android.baker.ui.activity.RecipeStepActivity;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepFragment extends Fragment {

    private List<RecipeStep> recipeSteps;
    private int currentStep;
    private TextView textViewRecipeStep;
    private StepButtonClick stepButtonClick;
    private SimpleExoPlayer simpleExoPlayer;
    private SimpleExoPlayerView playerView;

    public RecipeStepFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        playerView = rootView.findViewById(R.id.playerView);
        textViewRecipeStep = rootView.findViewById(R.id.tv_recipe_step_detail);
        LinearLayout buttonHolder = rootView.findViewById(R.id.button_holder);
        if (isPhone()) {
            Button buttonPrev = rootView.findViewById(R.id.button_prev);
            Button buttonNext = rootView.findViewById(R.id.button_next);
            buttonPrev.setOnClickListener(v -> stepButtonClick.onPrevClick(getPrevStep()));
            buttonNext.setOnClickListener(v -> stepButtonClick.onNextClick(getNextStep()));
            if (isLandscapeMode()) {
                // Hide Recipe Step
                textViewRecipeStep.setVisibility(View.GONE);
                // Hide next/prev buttons

                // Hide the status bar/navigation bar
                View decorView = Objects.requireNonNull(getActivity()).getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
                decorView.setSystemUiVisibility(uiOptions);
                // Hide action bar
                Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).hide();
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
            }
        } else {
            // Tablet only
            buttonHolder.setVisibility(View.GONE);
        }
        return rootView;
    }

    public void setRecipeList(List<RecipeStep> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public void setCurrentRecipeStep(int currentStep) {
        this.currentStep = currentStep;
        textViewRecipeStep.setText(recipeSteps.get(currentStep).getDescription());
        releasePlayer();
        if (TextUtils.isEmpty(recipeSteps.get(currentStep).getVideoURL()) &&
                TextUtils.isEmpty(recipeSteps.get(currentStep).getThumbnailURL())) {
            playerView.setVisibility(View.GONE);
        } else {
            playerView.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(recipeSteps.get(currentStep).getVideoURL())) {
                initializePlayer(Uri.parse(recipeSteps.get(currentStep).getVideoURL()));
            } else {
                initializePlayer(Uri.parse(recipeSteps.get(currentStep).getThumbnailURL()));
            }

        }
    }

    public void setStepButtonClick(StepButtonClick stepButtonClick) {
        this.stepButtonClick = stepButtonClick;
    }

    public interface StepButtonClick {
        void onPrevClick(int currentPosition);

        void onNextClick(int currentPosition);
    }

    private int getPrevStep() {
        if (currentStep == 0)
            return (recipeSteps.size() - 1);
        else return currentStep - 1;
    }

    private int getNextStep() {
        if (currentStep == (recipeSteps.size() - 1))
            return 0;
        else return (currentStep + 1);
    }

    private void initializePlayer(Uri mediaUri) {
        if (simpleExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            playerView.setPlayer(simpleExoPlayer);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "Baker");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    Objects.requireNonNull(getContext()), userAgent), new DefaultExtractorsFactory(),
                    null, null);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
        }
    }

    /**
     * Release ExoPlayer
     */
    public void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
            playerView.setPlayer(null);
        }
    }


    public void pausePlayback() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.setPlayWhenReady(false); // Pauses the playback if it's playing
        }
    }

    private boolean isPhone() {
        return (Objects.requireNonNull(getActivity()).getClass().getSimpleName()
                .equals(RecipeStepActivity.class.getSimpleName()));
    }

    private boolean isLandscapeMode() {
        return Configuration.ORIENTATION_LANDSCAPE == (getResources().getConfiguration().orientation);
    }

}
