package com.bignerdranch.android.criminalintent.UI.cimedetail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.criminalintent.Injection;
import com.bignerdranch.android.criminalintent.R;
import com.bignerdranch.android.criminalintent.UI.DatePickerFragment;
import com.bignerdranch.android.criminalintent.UI.ViewModelFactory;
import com.bignerdranch.android.criminalintent.database.entity.CrimeEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity implements CrimeFragment.OnFragmentInteractionListener,DatePickerFragment.OnDatePickedListener {

    private static final String CRIME_UUID = CrimePagerAdapter.class.getSimpleName().concat(":CRIME_UUID");

    private static final String TAG = CrimePagerAdapter.class.getSimpleName();

    private List<CrimeEntity> mCrimeList = new ArrayList<>();


    private ViewPager mCrimePager;

    private CrimePagerAdapter mCrimePageAdapter;

    private ViewModelFactory mViewModelFactory;

    private DetailViewModel mDetailViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        
        setContentView(R.layout.activity_crime_pager);



        mViewModelFactory = Injection.provideViewModelFactory(this);
        mDetailViewModel = ViewModelProviders.of(this, mViewModelFactory).get(DetailViewModel.class);

        mCrimePageAdapter = new CrimePagerAdapter(getSupportFragmentManager());
        mDetailViewModel.getAllCrimes().observe(this, crimes -> {

            if (crimes == null || crimes.size()==0) {
                Log.d(TAG, "onCreate: received crimes null -- creating mock crime for adapter");
                
                mCrimeList.add(new CrimeEntity());
                mCrimePageAdapter.submitItems(mCrimeList);
                
            }else {
                Log.d(TAG, "onCreate: received crimes length " + crimes.size());
                mCrimeList = crimes;
                mCrimePageAdapter.submitItems(crimes);
            }

        });



        mCrimePager=findViewById(R.id.crime_view_pager);


        mCrimePager.setPageTransformer(true, new DepthPageTransformer());


        mCrimePager.setAdapter(mCrimePageAdapter);


        UUID crime_ID = (UUID) getIntent().getSerializableExtra(CRIME_UUID);


//        CrimeEntity crime = CrimeLab.getInstance(getApplicationContext()).getCrime(crime_ID);
//
//        int index=0;
//        if (crime != null) {
//            index=CrimeLab.getInstance(getApplicationContext()).getCrimes().indexOf(crime);
//
//        }
//
//        mCrimePager.setCurrentItem(index);




    }




    public static Intent newIntent(Context ctx, UUID crimeID) {
        Log.d(TAG, "newIntent: ");

        Intent intent = new Intent(ctx, CrimePagerActivity.class);

        if (crimeID != null) {
            Log.d(TAG, "newIntent: crimeID != null");

            intent.putExtra(CRIME_UUID, crimeID);

        }
        return intent;
    }

    @Override
    public void onFragmentInteraction(final Uri uri) {


    }

    @Override
    public void onDatePicked(final Calendar picked_date) {

        ((CrimeFragment) mCrimePageAdapter.instantiateItem(mCrimePager,
                mCrimePager.getCurrentItem())).onUserChangedDateTime(picked_date);

    }


    private class CrimePagerAdapter extends FragmentStatePagerAdapter {

        List<CrimeEntity> mItems = new ArrayList<>();

        public CrimePagerAdapter(final FragmentManager fm) {

            super(fm);




        }

        public void submitItems(List<CrimeEntity> items) {
            Log.d(TAG, "submitItems: ");
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
        public Fragment getItem( int position) {


            return CrimeFragment.newInstance(mItems.get(position).getID());

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



            }else{
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
