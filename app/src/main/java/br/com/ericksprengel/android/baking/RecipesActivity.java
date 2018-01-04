package br.com.ericksprengel.android.baking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.ericksprengel.android.baking.data.Recipe;
import br.com.ericksprengel.android.baking.data.source.RecipesDataSource;
import br.com.ericksprengel.android.baking.data.source.RecipesRepository;
import br.com.ericksprengel.android.baking.data.source.local.BakingDatabase;
import br.com.ericksprengel.android.baking.data.source.local.RecipesLocalDataSource;
import br.com.ericksprengel.android.baking.data.source.remote.RecipesRemoteDataSource;
import br.com.ericksprengel.android.baking.util.AppExecutors;

public class RecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        BakingDatabase database = BakingDatabase.getInstance(this);
        RecipesRepository rr = RecipesRepository.getInstance(
                RecipesRemoteDataSource.getInstance(),
                RecipesLocalDataSource.getInstance(new AppExecutors(), database.recipeDao()));

        rr.getRecipes(new RecipesDataSource.LoadRecipesCallback() {
            @Override
            public void onRecipesLoaded(List<Recipe> recipes) {
                TextView t = RecipesActivity.this.findViewById(R.id.recipe_name);
                t.setText(recipes.get(0).toString());
                Toast.makeText(RecipesActivity.this, recipes.get(0).getName(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
