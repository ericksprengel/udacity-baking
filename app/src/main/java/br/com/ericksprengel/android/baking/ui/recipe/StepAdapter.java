package br.com.ericksprengel.android.baking.ui.recipe;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.ericksprengel.android.baking.R;
import br.com.ericksprengel.android.baking.data.Step;

/**
 * Created by erick.sprengel on 04/01/2018.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Step mStep;

        private final TextView mIndex;
        private final TextView mShortDescription;

        ViewHolder(View v) {
            super(v);
            mIndex = v.findViewById(R.id.recipe_ac_index_textview);
            mShortDescription = v.findViewById(R.id.recipe_ac_shortdescription_textview);
            v.setOnClickListener(this);
        }

        void updateData(int index, Step step) {
            mStep = step;
            mIndex.setText(String.valueOf(index));
            mShortDescription.setText(mStep.getShortDescription());
        }

        @Override
        public void onClick(View view) {
            mOnClickStepListener.onStepClick(mStep);
        }
    }


    public interface OnStepClickListener {
        void onStepClick(Step step);
    }

    private List<Step> mSteps;
    private OnStepClickListener mOnClickStepListener;

    StepAdapter(OnStepClickListener listener) {
        this.mOnClickStepListener = listener;
    }

    void setSteps(List<Step> steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }

    @Override
    public StepAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_recipe_steps_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Step step = mSteps.get(position);
        holder.updateData(position, step);
    }

    @Override
    public int getItemCount() {
        return mSteps == null ? 0 : mSteps.size();
    }

}
