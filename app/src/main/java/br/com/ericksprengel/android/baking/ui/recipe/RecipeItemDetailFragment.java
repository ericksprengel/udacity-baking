package br.com.ericksprengel.android.baking.ui.recipe;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.ericksprengel.android.baking.R;
import br.com.ericksprengel.android.baking.data.Step;
import br.com.ericksprengel.android.baking.data.source.RecipesRepository;
import br.com.ericksprengel.android.baking.ui.recipe.dummy.DummyContent;

/**
 * A fragment representing a single RecipeItem detail screen.
 * This fragment is either contained in a {@link RecipeItemListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeItemDetailActivity}
 * on handsets.
 */
public class RecipeItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the step ID that this fragment
     * represents.
     */
    public static final String ARG_STEP_ID = "step_id";

    /**
     * The step content this fragment is presenting.
     */
    private Step mStep;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeItemDetailFragment() {
    }

    public static RecipeItemDetailFragment newInstance(int stepId) {
        RecipeItemDetailFragment fragment = new RecipeItemDetailFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_STEP_ID, stepId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int stepId = getArguments().getInt(ARG_STEP_ID);
        //RecipesRepository mRepository = null;
        //mRepository.getStep(stepId, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipeitem_detail, container, false);
        ((TextView) rootView.findViewById(R.id.recipeitem_detail)).setText("TODO");
        return rootView;
    }
}
