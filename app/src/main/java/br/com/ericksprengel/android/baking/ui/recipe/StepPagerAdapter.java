package br.com.ericksprengel.android.baking.ui.recipe;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.List;

import br.com.ericksprengel.android.baking.data.Step;

public class StepPagerAdapter extends FragmentStatePagerAdapter {

    private SparseArray<StepFragment> mFragmentsHolded = new SparseArray<>();
    private List<Step> mSteps;

    StepPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    void setSteps(List<Step> steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        if(fragment instanceof StepFragment) {
            mFragmentsHolded.append(position, (StepFragment) fragment);
        }
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mFragmentsHolded.delete(position);
        super.destroyItem(container, position, object);
    }

    public StepFragment getCachedItem(int position) {
        return mFragmentsHolded.get(position, null);
    }

    @Override
    public StepFragment getItem(int position) {
        Step step = mSteps.get(position);
        return StepFragment.newInstance(step.getRecipeId(), step.getId());
    }

    @Override
    public int getCount() {
        return mSteps == null ? 0 : mSteps.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mSteps.get(position).getShortDescription();
    }
}
