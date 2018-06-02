package com.bignerdranch.android.criminalintent.UI.crimelist;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.bignerdranch.android.criminalintent.UI.SingleFragmentActivity;

public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.OnFragmentInteractionListener{
    private static final String TAG = CrimeListActivity.class.getSimpleName();

    @Override
    protected Fragment createFragment() {
        Log.d(TAG, "createFragment: ");

        final String testArg2="test2:CrimeListActivity";
        final String testArg1 = "test1:CrimeListActivity";
        return CrimeListFragment.newInstance(testArg1, testArg2);
    }

    @Override
    public void onFragmentInteraction(final Uri uri) {

    }
}
