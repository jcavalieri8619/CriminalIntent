package com.bignerdranch.android.criminalintent.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;


import java.util.Date;
import java.util.Locale;
import java.util.UUID;


@Entity(tableName = "crimesDB")
public class Crime {

    @PrimaryKey(autoGenerate = true)
    public long _DB_ID;


    @ColumnInfo(name = "uuid_column")
    private UUID mID;



    @ColumnInfo(name = "title_column")
    private String mTitle;

    @ColumnInfo(name = "date_column")
    private Date mDate;

    @ColumnInfo(name = "is_solved_column")
    private boolean mSolved;

    @ColumnInfo(name = "is_serious_column")
    private boolean mSeriousCrime;

    public Crime() {
        mID = UUID.randomUUID();
        mDate = new Date();

    }

    public UUID getID() {
        return mID;
    }

    public void setID(final UUID ID) {
        mID = ID;
    }

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
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy  h:m:s a", Locale.ENGLISH);
        return format.format(getDate());
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
