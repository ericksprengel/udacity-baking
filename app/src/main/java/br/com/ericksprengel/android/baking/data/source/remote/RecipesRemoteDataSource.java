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

package br.com.ericksprengel.android.baking.data.source.remote;

import android.support.annotation.NonNull;

import br.com.ericksprengel.android.baking.data.Recipe;
import br.com.ericksprengel.android.baking.data.source.RecipesDataSource;

/**
 * Implementation of the data source that adds a latency simulating network.
 */
public class RecipesRemoteDataSource implements RecipesDataSource {

    private static RecipesRemoteDataSource INSTANCE;

    private static final int SERVICE_LATENCY_IN_MILLIS = 2000;

    public static RecipesRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RecipesRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private RecipesRemoteDataSource() {}


    @Override
    public void getRecipes(@NonNull LoadRecipesCallback callback) {

    }

    @Override
    public void getRecipe(int recipeId, @NonNull LoadRecipeCallback callback) {

    }

    @Override
    public void saveRecipe(@NonNull Recipe recipe) {

    }

    @Override
    public void refreshRecipes() {

    }

    @Override
    public void deleteAllRecipes() {

    }
}
