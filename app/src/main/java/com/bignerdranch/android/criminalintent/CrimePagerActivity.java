package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.criminalintent.models.Crime;
import com.bignerdranch.android.criminalintent.models.CrimeLab;

import java.util.Calendar;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity implements CrimeFragment.OnFragmentInteractionListener,DatePickerFragment.OnDatePickedListener{

    private static final String CRIME_UUID = "CrimePagerActivity:CRIME_UUID";
    ViewPager mCrimePager;

    CrimeFragment mCrimeFragment;
    private CrimePagerAdapter mCrimePageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);



        mCrimePager=findViewById(R.id.crime_view_pager);


        mCrimePager.setPageTransformer(true, new DepthPageTransformer());

        mCrimePageAdapter = new CrimePagerAdapter(getSupportFragmentManager());
        mCrimePager.setAdapter(mCrimePageAdapter);





        UUID crime_ID = (UUID) getIntent().getSerializableExtra(CRIME_UUID);


        Crime crime = CrimeLab.getInstance(getApplicationContext()).getCrime(crime_ID);

        int index=0;
        if (crime != null) {
            index=CrimeLab.getInstance(getApplicationContext()).getCrimes().indexOf(crime);

        }

        mCrimePager.setCurrentItem(index);




    }

    public static Intent newIntent(Context ctx, UUID crimeID) {
        Intent intent = new Intent(ctx, CrimePagerActivity.class);
        intent.putExtra(CRIME_UUID, crimeID);
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

        public CrimePagerAdapter(final FragmentManager fm) {

            super(fm);



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
//            if (position >= CrimeLab.
//                    getInstance(getApplicationContext()).
//                    getCrimes().size()) {
//
//
//                position = 0;
//
//
//
//            }

            Fragment f= CrimeFragment.newInstance(CrimeLab.
                    getInstance(getApplicationContext()).
                    getCrimes().get(position).getID());


            return f;

        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {

            return CrimeLab.getInstance(getApplicationContext()).
                    getCrimes().size();
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
