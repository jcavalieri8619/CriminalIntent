package com.bignerdranch.android.criminalintent.ui.crimedetail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.criminalintent.Injection;
import com.bignerdranch.android.criminalintent.R;
import com.bignerdranch.android.criminalintent.model.Crime;
import com.bignerdranch.android.criminalintent.ui.DatePickerFragment;
import com.bignerdranch.android.criminalintent.ui.ViewModelFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity implements CrimeFragment.OnFragmentInteractionListener, DatePickerFragment.OnDatePickedListener {

    private static final String CRIME_UUID_EXTRA = CrimePagerAdapter.class.getSimpleName().concat(":CRIME_UUID_EXTRA");

    private static final String NEW_CRIME_EXTRA = CrimePagerAdapter.class.getSimpleName().concat(":NEWCRIME_EXTRA");


    private static final String TAG = CrimePagerActivity.class.getSimpleName().concat("::JPC");


    private ViewPager mCrimePager;

    private CrimePagerAdapter mCrimePageAdapter;

    private DetailPagerViewModel mDetailPagerViewModel;

    private UUID mCrime_ID = null;

    private boolean mPositionAlreadySet = false;


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: lifecycle");

    }

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
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: lifecycle");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: lifecycle");

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: lifecycle");

        setContentView(R.layout.activity_crime_pager);


        final ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        mDetailPagerViewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailPagerViewModel.class);


        mPositionAlreadySet = false;
        setupViewPager();

        setupViews();

        subscribeToModel();



        if (getIntent().getBooleanExtra(NEW_CRIME_EXTRA, false)) {
            Log.d(TAG, "onCreate: Received NEW_CRIME_EXTRA to add new crime");


        } else {
            Log.d(TAG, "onCreate: Received CRIME_UUID_EXTRA to update existing crime");

            // CRIME__UUID_EXTRA is only added by ListFrag if we ARE_NOT adding new
            // crime so still need to check NEW_CRIME_EXTRA value
            mCrime_ID = (UUID) getIntent().getSerializableExtra(CRIME_UUID_EXTRA);


        }


    }

    private int indexOfUUID(List<? extends Crime> list, UUID uuid) {
        if (list == null) {
            Log.d(TAG, "indexOfUUID: list from ListViewModel is null");

            return 0;
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getID().equals(uuid)) {
                Log.d(TAG, "indexOfUUID: found index of UUID at " + i);

                return i;
            }
        }
        Log.d(TAG, "indexOfUUID: cound't find UUID in list");

        return 0;
    }

    private void setupViewPager() {
        mCrimePageAdapter = new CrimePagerAdapter(getSupportFragmentManager());
    }

    private void subscribeToModel() {
        mDetailPagerViewModel.getAllCrimes().observe(this, crimes -> {

            if ((crimes == null) || crimes.size() == 0) {
                //FIXME shouldnt need this given the HEADER ROW and logs indicate it wasn't called on first statup
                // so probably dont need it but leave for now

                Log.d(TAG, "subscribeToModel: emitted crimes was null/empty despite header row?!");

                mCrimePageAdapter.submitItems(mDetailPagerViewModel.initEmptyPager());

            } else {
                Log.d(TAG, "subscribeToModel: updating pager's crimes list of size: "
                        + crimes.size() + "\n\tcrimes[0] = " + crimes.get(0).toString());
                mCrimePageAdapter.submitItems(crimes);


                // position should only be set when Pager is first created, but this callback
                // is called on each update to Repo so use boolean to track whether we
                // already set position because this activity is just starting else
                // we do not set the item position
                if (!mPositionAlreadySet) {

                    mCrimePager.setCurrentItem(indexOfUUID(crimes, mCrime_ID));
                    mPositionAlreadySet = true;
                }

            }


        });
    }

    private void setupViews() {
        mCrimePager = findViewById(R.id.crime_view_pager);
        mCrimePager.setPageTransformer(true, new DepthPageTransformer());


        mCrimePager.setAdapter(mCrimePageAdapter);
    }


    public static Intent newIntentForNewCrime(Context ctx) {

        Intent intent = new Intent(ctx, CrimePagerActivity.class);

        intent.putExtra(NEW_CRIME_EXTRA, true);

        return intent;

    }


    public static Intent newIntentForUUID(Context ctx, UUID crimeID) {

        Intent intent = new Intent(ctx, CrimePagerActivity.class);

        intent.putExtra(NEW_CRIME_EXTRA, false);

        intent.putExtra(CRIME_UUID_EXTRA, crimeID);

        return intent;
    }

    @Override
    public void onFragmentInteraction(final Uri uri) {


    }

    @Override
    public void onDatePicked(final Calendar picked_date) {


        ((CrimeFragment) mCrimePageAdapter.getCurrentFragment()).onUserChangedDateTime(picked_date);

    }


    private class CrimePagerAdapter extends FragmentStatePagerAdapter {

        private final String TAG_CPA = CrimePagerAdapter.class.getSimpleName().concat("::JPC");
        private Fragment mCurrentFragment;

        List<? extends Crime> mItems = new ArrayList<>();


        public CrimePagerAdapter(final FragmentManager fm) {

            super(fm);


        }

        public Fragment getCurrentFragment() {
            return mCurrentFragment;
        }


        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            if (getCurrentFragment() != object) {
                mCurrentFragment = ((Fragment) object);
            }
            super.setPrimaryItem(container, position, object);
        }

        public void submitItems(List<? extends Crime> items) {
            Log.d(TAG_CPA, "submitItems: items size: " + items.size());

            mItems = items;
            super.notifyDataSetChanged();

        }


        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            return super.instantiateItem(container, position);

        }

        /**
         * Return the Fragment associated with a specified position.
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {


            // could be HEADER ID or
            // depending on how fast asynch DB queries return list<CrimeEntity> from Repo
            // OR it could be valid crime ID if mItems.size > 1 strictly

            UUID nextID = mItems.get(position).getID();

            return CrimeFragment.newInstanceForUUID(nextID);

        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {

            return mItems.size();
        }

    }


    private class ZoomOutPager implements ViewPager.PageTransformer {

        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        /**
         * Apply a property transformation to the given page.
         *
         * @param page     Apply the transformation to this page
         * @param position Position of page relative to the current front-and-center
         *                 position of the pager. 0 is front and center. 1 is one full
         */
        @Override
        public void transformPage(@NonNull final View page, final float position) {
            int pageWidth = page.getWidth();
            int pageHeight = page.getHeight();


            if (position < -1) {
                // position in [ -Inf , -1)

                page.setAlpha(0);


            } else if (position <= 1) {
                // position in [ -1 , 1]

                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;

                if (position < 0) {
                    page.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    page.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                page.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));


            } else {
                // position in ( 1 , Inf)
                page.setAlpha(0);


            }


        }
    }


    private class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(@NonNull View page, float position) {
            int pageWidth = page.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                page.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                page.setAlpha(1);
                page.setTranslationX(0);
                page.setScaleX(1);
                page.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                page.setAlpha(1 - position);

                // Counteract the default slide transition
                page.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                page.setAlpha(0);
            }
        }
    }
}
