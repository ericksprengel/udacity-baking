package br.com.ericksprengel.android.baking.ui.recipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.List;

import br.com.ericksprengel.android.baking.R;
import br.com.ericksprengel.android.baking.data.Recipe;
import br.com.ericksprengel.android.baking.data.Step;
import br.com.ericksprengel.android.baking.data.source.RecipesDataSource;
import br.com.ericksprengel.android.baking.data.source.RecipesRepository;
import br.com.ericksprengel.android.baking.ui.BaseActivity;
import br.com.ericksprengel.android.baking.util.Inject;
import okhttp3.OkHttpClient;


public class StepActivity extends BaseActivity {

    private static final String ARG_RECIPE_ID = "recipe_id";
    private static final String ARG_STEP_ID = "step_id";

    private int mRecipeId;
    private int mStepId;

    private RecipesRepository mRecipesRepository;
    private StepPagerAdapter mStepPagerAdapter;
    private ViewPager mViewPager;

    public static Intent getStartIntent(Context context, Step step) {
        Intent intent = new Intent(context, StepActivity.class);
        intent.putExtra(ARG_RECIPE_ID, step.getRecipeId());
        intent.putExtra(ARG_STEP_ID, step.getId());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        mRecipeId = getIntent().getIntExtra(ARG_RECIPE_ID, -1);
        mStepId = getIntent().getIntExtra(ARG_STEP_ID, -1);
        mRecipesRepository = Inject.getRecipeRepository(this);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mStepPagerAdapter = new StepPagerAdapter(
                getSupportFragmentManager());
        mViewPager = findViewById(R.id.step_ac_steps_viewpager);
        mViewPager.setAdapter(mStepPagerAdapter);

        loadSteps();
    }

    private void loadSteps() {
        mRecipesRepository.getSteps(mRecipeId, new RecipesDataSource.LoadStepsCallback() {
            @Override
            public void onStepsLoaded(List<Step> steps) {
                mStepPagerAdapter.setSteps(steps);
                for(int position = 0; position < steps.size(); position++) {
                    Step step = steps.get(position);
                    if(step.getId() == mStepId) {
                        mViewPager.setCurrentItem(mStepId);
                    }
                }
            }

            @Override
            public void onDataNotAvailable() {
                Snackbar.make(mViewPager, getString(R.string.internal_error), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
