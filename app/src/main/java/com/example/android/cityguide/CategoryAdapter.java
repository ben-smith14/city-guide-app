package com.example.android.cityguide;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CategoryAdapter extends FragmentPagerAdapter {

    private Context mContext;

    CategoryAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    // Return an instance of the locations fragment and pass the current page position to it
    @Override
    public Fragment getItem(int position) {
        LocationsFragment newTabFragment = new LocationsFragment();
        Bundle tabArguments = new Bundle();
        tabArguments.putInt(Constants.TAB_POSITION_KEY, position);
        newTabFragment.setArguments(tabArguments);
        return newTabFragment;
    }

    // Return the number of fragments that can be swiped between
    @Override
    public int getCount() {
        return Constants.FRAGMENT_COUNT;
    }

    /*
    Return the title of the current fragment to display in the tab. As we want to use string
    resources, a context is required from the constructor and it is stored as a global variable
    */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 1) {
            return mContext.getString(R.string.category_coffee);
        } else if (position == 2) {
            return mContext.getString(R.string.category_nightlife);
        } else if (position == 3) {
            return mContext.getString(R.string.category_fun);
        } else if (position == 4) {
            return mContext.getString(R.string.category_sights);
        } else if (position == 5) {
            return mContext.getString(R.string.category_transport);
        } else {
            return mContext.getString(R.string.category_food);
        }
    }
}
