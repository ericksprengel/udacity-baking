package br.com.ericksprengel.android.baking.util;

import android.content.Context;

import br.com.ericksprengel.android.baking.data.source.RecipesRepository;
import br.com.ericksprengel.android.baking.data.source.local.BakingDatabase;
import br.com.ericksprengel.android.baking.data.source.local.RecipesLocalDataSource;
import br.com.ericksprengel.android.baking.data.source.remote.BakingServicesBuilder;
import br.com.ericksprengel.android.baking.data.source.remote.RecipesRemoteDataSource;

/**
 * Created by erick.sprengel on 05/01/2018.
 */

public class Inject {
    public static RecipesRepository getRecipeRepository(Context context) {
        BakingDatabase database = BakingDatabase.getInstance(context);
        return RecipesRepository.getInstance(
                RecipesRemoteDataSource.getInstance(BakingServicesBuilder.build(context), context.getResources()),
                RecipesLocalDataSource.getInstance(new AppExecutors(), database.recipeDao()));
    }
}
