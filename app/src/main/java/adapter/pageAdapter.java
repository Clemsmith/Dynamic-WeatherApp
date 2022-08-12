package adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import Fragments.Later;
import Fragments.Today;
import Fragments.Tomorrow;

public class pageAdapter extends FragmentPagerAdapter {
    int totalFragment;

    public pageAdapter(@NonNull FragmentManager fm, int behavior) {

        super(fm, behavior);
        totalFragment = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: return new Today();
            case 1: return new Tomorrow();
            case 2: return new Later();
            default:return null;

        }
    }

    @Override
    public int getCount() {

        return this.totalFragment;
    }
}
