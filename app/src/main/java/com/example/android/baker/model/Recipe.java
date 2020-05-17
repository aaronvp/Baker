package com.example.android.baker.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Entity(tableName = "recipe")
@Data
public class Recipe implements Parcelable {

    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @Ignore
    @SerializedName("ingredients")
    private List<Ingredient> ingredientList;
    @Ignore
    @SerializedName("steps")
    private List<RecipeStep> recipeStepList;
    @SerializedName("servings")
    private String servings;
    @SerializedName("image")
    private String image;

    public Recipe(int id, String name,
                  String servings, String image) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredientList = in.createTypedArrayList(Ingredient.CREATOR);
        recipeStepList = in.createTypedArrayList(RecipeStep.CREATOR);
        servings = in.readString();
        image = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(ingredientList);
        dest.writeTypedList(recipeStepList);
        dest.writeString(servings);
        dest.writeString(image);
    }
}

