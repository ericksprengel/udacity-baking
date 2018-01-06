package br.com.ericksprengel.android.baking.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "steps",
        primaryKeys = {"id", "recipeId"},
        foreignKeys = {
            @ForeignKey(
                entity = Recipe.class,
                parentColumns = "id",
                childColumns = "recipeId",
                onDelete = ForeignKey.CASCADE)
        })
public class Step {

    @SerializedName("id")
    int id;

    @SerializedName("videoURL")
    String videoURL;

    @SerializedName("description")
    String description;

    @SerializedName("shortDescription")
    String shortDescription;

    @SerializedName("thumbnailURL")
    String thumbnailURL;

    int recipeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    @Override
     public String toString(){
        return 
            "Step{" +
            "videoURL = '" + videoURL + '\'' + 
            ",description = '" + description + '\'' + 
            ",id = '" + id + '\'' + 
            ",shortDescription = '" + shortDescription + '\'' + 
            ",thumbnailURL = '" + thumbnailURL + '\'' + 
            "}";
        }
}