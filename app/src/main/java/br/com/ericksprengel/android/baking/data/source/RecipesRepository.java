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

package br.com.ericksprengel.android.baking.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.ericksprengel.android.baking.data.Recipe;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation to load recipes from the data sources into a cache.
 *
 */
public class RecipesRepository implements RecipesDataSource {

    private volatile static RecipesRepository INSTANCE = null;

    private final RecipesDataSource mRecipesRemoteDataSource;
    private final RecipesDataSource mRecipesLocalDataSource;

    /**
     * Cache for load Recipes in memory.
     */
    private Map<Integer, Recipe> mCachedRecipes;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested.
     */
    private boolean mCacheIsDirty = false;

    // Prevent direct instantiation.
    private RecipesRepository(@NonNull RecipesDataSource recipesRemoteDataSource,
                              @NonNull RecipesDataSource recipesLocalDataSource) {
        mRecipesRemoteDataSource = checkNotNull(recipesRemoteDataSource);
        mRecipesLocalDataSource = checkNotNull(recipesLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param recipesRemoteDataSource the backend data source.
     * @return the {@link RecipesRepository} instance
     */
    public static RecipesRepository getInstance(RecipesDataSource recipesRemoteDataSource, RecipesDataSource recipesLocalDataSource) {
        if (INSTANCE == null) {
            synchronized (RecipesRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RecipesRepository(recipesRemoteDataSource, recipesLocalDataSource);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(RecipesDataSource, RecipesDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Gets recipes from cache or remote data source, whichever is
     * available first.
     * <p>
     * Note: {@link LoadRecipesCallback#onDataNotAvailable(int errorCode, String errorMessage)} is fired if all data sources fail to
     * get the data.
     */
    @Override
    public void getRecipes(@NonNull final LoadRecipesCallback callback) {
        checkNotNull(callback);

        // Respond immediately with cache if available and not dirty
        if (mCachedRecipes != null && !mCacheIsDirty) {
            callback.onRecipesLoaded(new ArrayList<>(mCachedRecipes.values()));
            return;
        }

        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getRecipesFromRemoteDataSource(callback);
        } else {
            // Query the local storage if available. If not, query the network.
            mRecipesLocalDataSource.getRecipes(new LoadRecipesCallback() {
                @Override
                public void onRecipesLoaded(List<Recipe> recipes) {
                    refreshCache(recipes);
                    callback.onRecipesLoaded(new ArrayList<>(mCachedRecipes.values()));
                }

                @Override
                public void onDataNotAvailable(int errorCode, String errorMessage) {
                    getRecipesFromRemoteDataSource(callback);
                }
            });
        }
    }

    @Override
    public void saveRecipe(@NonNull Recipe recipe) {
        checkNotNull(recipe);
        mRecipesRemoteDataSource.saveRecipe(recipe);
        mRecipesLocalDataSource.saveRecipe(recipe);

        // Do in memory cache update to keep the app UI up to date
        if (mCachedRecipes == null) {
            mCachedRecipes = new LinkedHashMap<>();
        }
        mCachedRecipes.put(recipe.getId(), recipe);
    }

    /**
     * Gets recipes from local data source (sqlite).
     * <p>
     * Note: {@link LoadRecipeCallback#onDataNotAvailable()} is fired if local data was cleared
     */
    @Override
    public void getRecipe(final int recipeId, @NonNull final LoadRecipeCallback callback) {
        Recipe cachedRecipe = getRecipeWithId(recipeId);

        // Respond immediately with cache if available
        if (cachedRecipe != null) {
            callback.onRecipeLoaded(cachedRecipe);
            return;
        }

        // Load from persisted if needed.

        // Is the recipe in the local data source? If not, the app data was cleared.
        mRecipesLocalDataSource.getRecipe(recipeId, new LoadRecipeCallback() {
            @Override
            public void onRecipeLoaded(Recipe recipe) {
                // Do in memory cache update to keep the app UI up to date
                if (mCachedRecipes == null) {
                    mCachedRecipes = new LinkedHashMap<>();
                }
                mCachedRecipes.put(recipe.getId(), recipe);

                callback.onRecipeLoaded(recipe);
            }

            @Override
            public void onDataNotAvailable() {
                // it's a invalid recipe or it was removed from cache.
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void refreshRecipes() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllRecipes() {
        mRecipesRemoteDataSource.deleteAllRecipes();
        mRecipesLocalDataSource.deleteAllRecipes();

        if (mCachedRecipes == null) {
            mCachedRecipes = new LinkedHashMap<>();
        }
        mCachedRecipes.clear();
    }

    private void getRecipesFromRemoteDataSource(@NonNull final LoadRecipesCallback callback) {
        mRecipesRemoteDataSource.getRecipes(new LoadRecipesCallback() {
            @Override
            public void onRecipesLoaded(List<Recipe> recipes) {
                refreshCache(recipes);
                refreshLocalDataSource(recipes);
                callback.onRecipesLoaded(new ArrayList<>(mCachedRecipes.values()));
            }

            @Override
            public void onDataNotAvailable(int errorCode, String errorMessage) {
                callback.onDataNotAvailable(errorCode, errorMessage);
            }
        });
    }

    private void refreshCache(List<Recipe> recipes) {
        if (mCachedRecipes== null) {
            mCachedRecipes = new LinkedHashMap<>();
        }
        mCachedRecipes.clear();
        for (Recipe recipe: recipes) {
            mCachedRecipes.put(recipe.getId(), recipe);
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Recipe> recipes) {
        mRecipesLocalDataSource.deleteAllRecipes();
        for (Recipe recipe: recipes) {
            mRecipesLocalDataSource.saveRecipe(recipe);
        }
    }

    @Nullable
    private Recipe getRecipeWithId(int id) {
        checkNotNull(id);
        if (mCachedRecipes == null || mCachedRecipes.isEmpty()) {
            return null;
        } else {
            return mCachedRecipes.get(id);
        }
    }
}
