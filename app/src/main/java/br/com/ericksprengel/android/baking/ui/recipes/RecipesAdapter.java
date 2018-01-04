package br.com.ericksprengel.android.baking.ui.recipes;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.ericksprengel.android.baking.R;
import br.com.ericksprengel.android.baking.data.Recipe;


public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {


    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    private List<Recipe> mRecipes;
    private OnRecipeClickListener mOnClickRecipeListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Recipe mRecipe;
        TextView mName;

        ViewHolder(View v) {
            super(v);
            mName = v.findViewById(R.id.recipes_ac_name_textview);
            v.setOnClickListener(this);
        }

        void updateData(Recipe recipe) {
            mRecipe = recipe;
            mName.setText(mRecipe.getName());
        }

        @Override
        public void onClick(View view) {
            RecipesAdapter.this.mOnClickRecipeListener.onRecipeClick(mRecipe);
        }
    }

    RecipesAdapter(OnRecipeClickListener listener) {
        this.mOnClickRecipeListener = listener;
    }

    void setRecipes(List<Recipe> recipes) {
        this.mRecipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public RecipesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_recipes_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);

        holder.updateData(recipe);
    }

    @Override
    public int getItemCount() {
        return mRecipes == null ? 0 : mRecipes.size();
    }

}