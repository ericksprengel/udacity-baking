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
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import br.com.ericksprengel.android.baking.data.Recipe;
import br.com.ericksprengel.android.baking.data.Step;

/**
 * Data Access Object for the recipes table.
 */
@Dao()
public interface StepsDao {

    @Query("SELECT * FROM steps WHERE recipeId = :recipeId")
    List<Step> getSteps(int recipeId);

    @Query("SELECT * FROM steps WHERE id = :id AND recipeId = :recipeId")
    Step getStep(int recipeId, int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Step> steps);

}
