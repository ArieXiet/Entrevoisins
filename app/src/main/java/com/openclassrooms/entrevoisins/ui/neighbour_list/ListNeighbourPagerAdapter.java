package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;



class ListNeighbourPagerAdapter extends FragmentPagerAdapter {

    ListNeighbourPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * getItem is called to instantiate the fragment for the given page.
     * @param position the position in the tab
     * @return the corresponding fragment
     */
    @Override
    public Fragment getItem(int position) {
        Log.d("getItem", "DEBUG: getItem: start");
        Fragment fragment = null;
        switch (position) {
            case 0:
                Log.d("getItem", "DEBUG: getItem: case 0 ");
                fragment = NeighbourFragment.newInstance();
                break;
            case 1:
                Log.d("getItem", "DEBUG: getItem: case 1 ");
                fragment = FavoriteFragment.newInstance();
                break;
        }
        
        return fragment;
    }

    /**
     * get the number of pages
     * @return the number of pages
     */
    @Override
    public int getCount() {
        return 2;
    }
}