package br.com.ericksprengel.android.baking.widgets.recipe;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViewsService;

import java.util.List;

import br.com.ericksprengel.android.baking.data.Ingredient;
import br.com.ericksprengel.android.baking.data.source.RecipesRepository;
import br.com.ericksprengel.android.baking.util.Inject;

/**
 * Created by erick.sprengel on 08/01/2018.
 */

public class RecipeWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        int recipeId = intent.getIntExtra("recipe_id", 0);
        return new RecipeWidgetViewsFactory(this.getApplicationContext(), appWidgetId, recipeId);
    }
}
