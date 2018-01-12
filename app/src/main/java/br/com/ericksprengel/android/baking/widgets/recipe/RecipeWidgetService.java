package br.com.ericksprengel.android.baking.widgets.recipe;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.RemoteViewsService;

import java.util.List;

import br.com.ericksprengel.android.baking.data.Ingredient;
import br.com.ericksprengel.android.baking.data.source.RecipesRepository;
import br.com.ericksprengel.android.baking.util.Inject;

/**
 * Created by erick.sprengel on 08/01/2018.
 */

public class RecipeWidgetService extends RemoteViewsService {

    private static final String PARAMS_RECIPE_ID = "recipe_id";

    @NonNull
    static Intent getRemoteAdapterIntent(Context context, int appWidgetId, int recipeId) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.putExtra(PARAMS_RECIPE_ID, recipeId);
        return intent;
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        int recipeId = intent.getIntExtra(PARAMS_RECIPE_ID, 0);
        return new RecipeWidgetViewsFactory(this.getApplicationContext(), appWidgetId, recipeId);
    }
}
