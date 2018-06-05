package com.bignerdranch.android.criminalintent.UI.crimelist;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.bignerdranch.android.criminalintent.UI.SingleFragmentActivity;

public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.OnFragmentInteractionListener{
    private static final String TAG = CrimeListActivity.class.getSimpleName();


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: lifecycle");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: lifecycle");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: lifecycle");

    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: lifecycle");

    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: lifecycle");

    }

    /**
     * Called after {@link #onStop} when the current activity is being
     * re-displayed to the user (the user has navigated back to it).  It will
     * be followed by {@link #onStart} and then {@link #onResume}.
     * <p>
     * <p>For activities that are using raw {link Cursor} objects (instead of
     * creating them through
     * {@link #managedQuery(Uri, String[], String, String[], String)},
     * this is usually the place
     * where the cursor should be requeried (because you had deactivated it in
     * {@link #onStop}.
     * <p>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onStop
     * @see #onStart
     * @see #onResume
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: lifecycle");

    }

    @Override
    protected Fragment createFragment() {


        return CrimeListFragment.newInstance();
    }

    @Override
    public void onFragmentInteraction(final Uri uri) {

    }
}
