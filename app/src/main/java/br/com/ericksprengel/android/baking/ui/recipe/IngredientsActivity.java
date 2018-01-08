package br.com.ericksprengel.android.baking.ui.recipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.ericksprengel.android.baking.R;

public class IngredientsActivity extends AppCompatActivity {

    final private static String PARAM_RECIPE = "recipe";

    public static Intent getStartIntent(Context context, int recipeId) {
        Intent intent = new Intent(context, IngredientsActivity.class);
        intent.putExtra(PARAM_RECIPE, recipeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        if (savedInstanceState == null) {
            int recipeId = getIntent().getIntExtra(PARAM_RECIPE, -1);
            IngredientsFragment fragment = IngredientsFragment.newInstance(recipeId);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.ingredients_ac_ingredients_container, fragment)
                    .commit();
        }
    }
}
