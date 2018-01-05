package br.com.ericksprengel.android.baking.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(foreignKeys = @ForeignKey(
        entity = Recipe.class,
        parentColumns = "id",
        childColumns = "id"))
public class Step {

    @PrimaryKey
    @SerializedName("id")
    public int id;

    @SerializedName("videoURL")
    public String videoURL;

    @SerializedName("description")
    public String description;

    @SerializedName("shortDescription")
    public String shortDescription;

    @SerializedName("thumbnailURL")
    public String thumbnailURL;

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