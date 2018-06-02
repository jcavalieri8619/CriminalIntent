package com.bignerdranch.android.criminalintent.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.util.Log;


import com.bignerdranch.android.criminalintent.UI.CrimeFields;
import com.bignerdranch.android.criminalintent.model.Crime;

import java.util.Date;
import java.util.UUID;


@Entity(tableName = "crimesDB")
public class CrimeEntity implements Crime{

    @Ignore
    private static final String TAG = CrimeEntity.class.getSimpleName();


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




    public CrimeEntity() {
        Log.d(TAG, "CrimeEntity: default ctor");

        mID = UUID.randomUUID();
        mDate = new Date();
        mSeriousCrime=false;
        mSeriousCrime=false;
    }

    public CrimeEntity(CrimeFields fields) {
        this();

        Log.d(TAG, "CrimeEntity: fields CTOR");

        setDate(fields.getDate());
        setTitle(fields.getTitle());
        setSeriousCrime(fields.isSeriousCrime());
        setSolved(fields.isSolved());

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
