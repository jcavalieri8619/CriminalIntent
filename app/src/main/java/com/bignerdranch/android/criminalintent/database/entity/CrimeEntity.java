package com.bignerdranch.android.criminalintent.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


import com.bignerdranch.android.criminalintent.model.Crime;

import java.util.Date;
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

    @ColumnInfo(name = "suspect_column")
    private String mSuspect;

    @ColumnInfo(name = "photoPath_column")
    private boolean mPhotoPath;

    @ColumnInfo(name = "date_column")
    private Date mDate;

    @ColumnInfo(name = "is_solved_column")
    private boolean mSolved;

    @ColumnInfo(name = "is_serious_column")
    private boolean mSerious;




    public CrimeEntity() {
        mID = UUID.randomUUID();
        mDate = new Date();
        mSerious=false;
        mSolved = false;
        mPhotoPath = false;

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

    @Override
    public String getSuspect() {
        return mSuspect;
    }

    @Override
    public void setSuspect(final String suspect) {

        mSuspect = suspect;
    }

    @Override
    public boolean getPhotoPath() {
        return mPhotoPath;
    }

    @Override
    public void setPhotoPath(boolean hasPath) {
        mPhotoPath = hasPath;
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

    public boolean isSerious() {
        return mSerious;
    }

    public void setSerious(final boolean seriousCrime) {
        mSerious = seriousCrime;
    }

    @Override
    public String toString() {
        return "CrimeEntity{" +
                "mID=" + mID +
                ", mTitle='" + mTitle + '\'' +
                ", mSuspect='" + mSuspect + '\'' +
                ", mDate=" + mDate +
                ", mSolved=" + mSolved +
                ", mSeriousCrime=" + mSerious +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CrimeEntity)) return false;
        final CrimeEntity entity = (CrimeEntity) o;
        return isSolved() == entity.isSolved() &&
                isSerious() == entity.isSerious() &&
                Objects.equals(getID(), entity.getID()) &&
                Objects.equals(getTitle(), entity.getTitle()) &&
                Objects.equals(getSuspect(), entity.getSuspect()) &&
                Objects.equals(getDate(), entity.getDate());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getID(), getTitle(), getSuspect(), getDate(), isSolved(), isSerious());
    }
}
