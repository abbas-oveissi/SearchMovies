package ir.oveissi.searchmovies.utils.bases;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import ir.oveissi.searchmovies.utils.NavigationManager;

public class BaseFragment extends Fragment {


    private NavigationManager navigationManagerInner;
    private FragmentInteractionListener fragmentInteractionInner;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        if (getParentFragment() != null && getParentFragment() instanceof HasNavigationManager)
            navigationManagerInner = ((HasNavigationManager) getParentFragment()).provideNavigationManager();
        else if (context instanceof HasNavigationManager)
            navigationManagerInner = ((HasNavigationManager) context).provideNavigationManager();
        else
            throw new RuntimeException("Activity host must implement HasNavigationManager");

        if (context instanceof Activity)
            fragmentInteractionInner = (FragmentInteractionListener) context;
        else
            throw new RuntimeException("Activity host must implement FragmentInteractionListener");
    }

    public NavigationManager getNavigationManager() {
        return navigationManagerInner;
    }

    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        fragmentInteractionInner.setCurrentFragment(this);
    }
}
