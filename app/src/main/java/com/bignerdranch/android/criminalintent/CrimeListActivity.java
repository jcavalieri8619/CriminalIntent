package com.bignerdranch.android.criminalintent;

import android.net.Uri;
import android.support.v4.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.OnFragmentInteractionListener{
    @Override
    protected Fragment createFragment() {
        final String testArg2="test2:CrimeListActivity";
        final String testArg1 = "test1:CrimeListActivity";
        return CrimeListFragment.newInstance(testArg1, testArg2);
    }

    @Override
    public void onFragmentInteraction(final Uri uri) {

    }
}
