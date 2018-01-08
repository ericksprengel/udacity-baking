package br.com.ericksprengel.android.baking.ui.recipe;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.ericksprengel.android.baking.R;
import br.com.ericksprengel.android.baking.data.Step;
import br.com.ericksprengel.android.baking.data.source.RecipesDataSource;
import br.com.ericksprengel.android.baking.data.source.RecipesRepository;
import br.com.ericksprengel.android.baking.util.Inject;

public class StepFragment extends Fragment {

    private static final String ARG_RECIPE_ID = "recipe_id";
    private static final String ARG_STEP_ID = "step_id";

    private Step mStep;
    private TextView mDescription;

    public StepFragment() {}

    public static StepFragment newInstance(int recipeId, int stepId) {
        StepFragment fragment = new StepFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_RECIPE_ID, recipeId);
        args.putInt(ARG_STEP_ID, stepId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void loadStep(int recipeId, int stepId) {
        RecipesRepository mRepository = Inject.getRecipeRepository(getContext());
        mRepository.getStep(recipeId, stepId, new RecipesDataSource.LoadStepCallback() {
            @Override
            public void onStepLoaded(Step step) {
                mStep = step;
                mDescription.setText(mStep.getDescription());
            }

            @Override
            public void onDataNotAvailable() {
                Snackbar.make(getView(), getString(R.string.internal_error), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        mDescription = rootView.findViewById(R.id.step_frag_description_textview);

        int recipeId = getArguments().getInt(ARG_RECIPE_ID);
        int stepId = getArguments().getInt(ARG_STEP_ID);
        loadStep(recipeId, stepId);

        return rootView;
    }
}
