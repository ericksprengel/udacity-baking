package br.com.ericksprengel.android.baking.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Ingredient {

    @PrimaryKey
    public int id;

    @SerializedName("quantity")
    public int quantity;

    @SerializedName("measure")
    public String measure;

    @SerializedName("ingredient")
    public String ingredient;

    @Override
     public String toString(){
        return 
            "Ingredient{" +
            "quantity = '" + quantity + '\'' + 
            ",measure = '" + measure + '\'' + 
            ",ingredient = '" + ingredient + '\'' + 
            "}";
        }
}