package cdio.desert_eagle.project_bts.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import cdio.desert_eagle.project_bts.fragment.HomeFragment;
import cdio.desert_eagle.project_bts.fragment.MessageFragment;
import cdio.desert_eagle.project_bts.fragment.PostFragment;
import cdio.desert_eagle.project_bts.fragment.ProfileFragment;
import cdio.desert_eagle.project_bts.fragment.SearchFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new PostFragment();
            case 3:
                return new MessageFragment();
        }
        return new ProfileFragment();
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
