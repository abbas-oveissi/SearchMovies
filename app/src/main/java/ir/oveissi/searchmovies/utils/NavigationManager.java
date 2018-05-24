package ir.oveissi.searchmovies.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class NavigationManager {

    private FragmentManager mFragmentManager;
    private int container;

    public NavigationManager(FragmentManager mFragmentManager, int container) {

        this.mFragmentManager = mFragmentManager;
        this.container = container;

        mFragmentManager.addOnBackStackChangedListener(() -> {
            if (navigationListener != null)
                navigationListener.change();
        });
    }

    private NavigationListener navigationListener;

    public void setNavigationListener(NavigationListener navigationListener) {
        this.navigationListener = navigationListener;
    }

    public interface NavigationListener {
        public void change();
    }

    public boolean isRootFragmentVisible() {
        return mFragmentManager.getBackStackEntryCount() <= 1;
    }

    public void openFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction fragTransaction = mFragmentManager.beginTransaction();
        fragTransaction.replace(container, fragment);
        if (addToBackStack)
            fragTransaction.addToBackStack(fragment.toString());
        fragTransaction.commit();
    }

    public void openAsRoot(Fragment fragment) {
        popEveryFragment();
        openFragment(fragment, false);
    }

    private void popEveryFragment() {
        int backStackCount = mFragmentManager.getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            int backStackId = mFragmentManager.getBackStackEntryAt(i).getId();
            mFragmentManager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public boolean navigateBack() {
        if (mFragmentManager.getBackStackEntryCount() == 0) {
            return false;
        } else {
            mFragmentManager.popBackStackImmediate();
            return true;
        }
    }

}
