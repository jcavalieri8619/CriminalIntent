package com.bignerdranch.android.criminalintent.UI;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

/**
 * fields to be used inside VIEW (in MVVM arch) and handed off to ViewModel
 */
public class CrimeFields {

    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private boolean mSeriousCrime;

    public CrimeFields() {
        mDate = new Date();

    }

    public static final DateFormat sDateFormat = new SimpleDateFormat("MMMM d, yyyy  h:m:s a", Locale.ENGLISH);


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(final String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public String getFormattedDate() {
        return sDateFormat.format(getDate());
    }

    public void setDate(final Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(final boolean solved) {
        mSolved = solved;
    }

    public boolean isSeriousCrime() {
        return mSeriousCrime;
    }

    public void setSeriousCrime(final boolean seriousCrime) {
        mSeriousCrime = seriousCrime;
    }
}
