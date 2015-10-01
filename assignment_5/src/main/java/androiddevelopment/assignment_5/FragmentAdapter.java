package androiddevelopment.assignment_5;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


public class FragmentAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "QUOTES", "MOVIES"};
    private Context context;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        Log.i("ITEM1", "" + position);
        Fragment fragment = null;
        switch (position) {
            case 0:
                Log.i("ITEM", "" + position);
                fragment = new ZenFragment();
                break;
            case 1:
                Log.i("ITEM", "" + position);
                fragment = new MasterScreenFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}