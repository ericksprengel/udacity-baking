package br.com.ericksprengel.android.baking.ui.recipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import br.com.ericksprengel.android.baking.R;
import br.com.ericksprengel.android.baking.data.Step;
import br.com.ericksprengel.android.baking.ui.BaseActivity;


public class StepActivity extends BaseActivity {

    private static final String ARG_RECIPE_ID = "recipe_id";
    private static final String ARG_STEP_ID = "step_id";

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
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            int recipeId = getIntent().getIntExtra(ARG_RECIPE_ID, -1);
            int stepId = getIntent().getIntExtra(ARG_STEP_ID, -1);
            StepFragment fragment = StepFragment.newInstance(recipeId, stepId);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_ac_recipeitem_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, RecipeActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
