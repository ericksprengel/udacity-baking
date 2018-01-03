/*
 * Copyright 2017, The Android Open Source Project
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

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import br.com.ericksprengel.android.baking.data.Recipe;

/**
 * Data Access Object for the recipes table.
 */
@Dao
public interface RecipesDao {

    /**
     * Select all recipes from the recipes table.
     *
     * @return all recipes.
     */
    @Query("SELECT * FROM recipe")
    List<Recipe> getRecipes();

    /**
     * Select a recipe by id.
     *
     * @param recipeId the recipe id.
     * @return the recipe with recipeId.
     */
    @Query("SELECT * FROM recipe WHERE id = :recipeId")
    Recipe getRecipeById(int recipeId);

    /**
     * Insert the recipes in the database. If the recipe already exists, replace it.
     *
     * @param recipes the recipes to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Recipe... recipes);

    /**
     * Insert a recipe in the database. If the recipe already exists, replace it.
     *
     * @param recipe the recipe to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(Recipe recipe);

    /**
     * Delete all recipes.
     */
    @Query("DELETE FROM recipe")
    void deleteRecipes();

}
