/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.ericksprengel.android.baking.data.source.local;

import android.support.annotation.NonNull;

import java.util.List;

import br.com.ericksprengel.android.baking.data.Recipe;
import br.com.ericksprengel.android.baking.data.source.RecipesDataSource;
import br.com.ericksprengel.android.baking.util.AppExecutors;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Concrete implementation of a data source as a db.
 */
public class RecipesLocalDataSource implements RecipesDataSource {

    private static volatile RecipesLocalDataSource INSTANCE;

    private RecipesDao mRecipesDao;

    private AppExecutors mAppExecutors;

    // Prevent direct instantiation.
    private RecipesLocalDataSource(@NonNull AppExecutors appExecutors,
                                   @NonNull RecipesDao recipesDao) {
        mAppExecutors = appExecutors;
        mRecipesDao = recipesDao;
    }

    public static RecipesLocalDataSource getInstance(@NonNull AppExecutors appExecutors,
                                                     @NonNull RecipesDao recipesDao) {
        if (INSTANCE == null) {
            synchronized (RecipesLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RecipesLocalDataSource(appExecutors, recipesDao);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Note: {@link LoadRecipesCallback#onDataNotAvailable(int errorCode, String errorMessage)} is fired if the database doesn't exist
     * or the table is empty.
     */
    @Override
    public void getRecipes(@NonNull final LoadRecipesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Recipe> recipes = mRecipesDao.getRecipes();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (recipes.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable(-1, null);
                        } else {
                            callback.onRecipesLoaded(recipes);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    /**
     * Note: {@link LoadRecipeCallback#onDataNotAvailable()} is fired if the {@link Recipe} isn't
     * found.
     */
    @Override
    public void getRecipe(final int recipeId, @NonNull final LoadRecipeCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Recipe recipe = mRecipesDao.getRecipeById(recipeId);

                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (recipe != null) {
                            callback.onRecipeLoaded(recipe);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveRecipe(@NonNull final Recipe recipe) {
        checkNotNull(recipe);
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mRecipesDao.insertRecipe(recipe);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void refreshRecipes() {
        // Not required because the {@link RecipesRepository} handles the logic of refreshing the
        // recipes from all the available data sources.
    }

    @Override
    public void deleteAllRecipes() {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mRecipesDao.deleteRecipes();
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);
    }

}
