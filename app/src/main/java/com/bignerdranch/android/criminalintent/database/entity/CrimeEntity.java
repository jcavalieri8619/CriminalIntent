package com.bignerdranch.android.criminalintent.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.util.Log;


import com.bignerdranch.android.criminalintent.model.Crime;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;


@Entity(tableName = "crimesDB")
public class CrimeEntity implements Crime{

    @Ignore
    private static final String TAG = CrimeEntity.class.getSimpleName();



    @NonNull
    @PrimaryKey()
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
        mID = UUID.randomUUID();
        mDate = new Date();
        mSeriousCrime=false;
        mSolved = false;

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

    @Override
    public String toString() {
        return "CrimeEntity{" +
                "  mID=" + mID +
                ", mTitle='" + mTitle + '\'' +
                ", mDate=" + mDate +
                ", mSolved=" + mSolved +
                ", mSeriousCrime=" + mSeriousCrime +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CrimeEntity)) return false;
        final CrimeEntity that = (CrimeEntity) o;
        return isSolved() == that.isSolved() &&
                isSeriousCrime() == that.isSeriousCrime() &&
                Objects.equals(getID(), that.getID()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getDate(), that.getDate());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getID(), getTitle(), getDate(), isSolved(), isSeriousCrime());
    }
}
