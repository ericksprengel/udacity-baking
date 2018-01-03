package br.com.ericksprengel.android.baking.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class Recipe {

    @PrimaryKey
    @SerializedName("id")
    public int id;

    @SerializedName("image")
    public String image;

    @SerializedName("servings")
    public int servings;

    @SerializedName("name")
    public String name;

    @Embedded
    @SerializedName("ingredients")
    public List<Ingredient> ingredients;

    @Embedded
    @SerializedName("steps")
    public List<Step> steps;

    @Override
     public String toString(){
        return 
            "Recipe{" +
            "image = '" + image + '\'' + 
            ",servings = '" + servings + '\'' + 
            ",name = '" + name + '\'' + 
            ",ingredients = '" + ingredients + '\'' + 
            ",id = '" + id + '\'' + 
            ",steps = '" + steps + '\'' + 
            "}";
        }
}