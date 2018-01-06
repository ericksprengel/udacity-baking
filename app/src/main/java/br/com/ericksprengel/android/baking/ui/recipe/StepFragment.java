package br.com.ericksprengel.android.baking.ui.recipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.ericksprengel.android.baking.R;
import br.com.ericksprengel.android.baking.data.Step;
import br.com.ericksprengel.android.baking.data.source.RecipesRepository;
import br.com.ericksprengel.android.baking.util.Inject;

public class StepFragment extends Fragment {

    public static final String ARG_STEP_ID = "step_id";

    private Step mStep;

    public StepFragment() {}

    public static StepFragment newInstance(int stepId) {
        StepFragment fragment = new StepFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_STEP_ID, stepId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int stepId = getArguments().getInt(ARG_STEP_ID);
        RecipesRepository mRepository = Inject.getRecipeRepository(getContext());
        //TODO mRepository.getStep(stepId, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        ((TextView) rootView.findViewById(R.id.recipe_step_frag_description_textview)).setText("TODO");
        return rootView;
    }
}
