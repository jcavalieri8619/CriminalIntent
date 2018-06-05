package com.bignerdranch.android.criminalintent.UI;


import android.databinding.Bindable;
import android.databinding.BaseObservable;

import com.bignerdranch.android.criminalintent.BR;
import com.bignerdranch.android.criminalintent.database.entity.CrimeEntity;
import com.bignerdranch.android.criminalintent.model.Crime;


import java.util.Date;
import java.util.UUID;
//
///**
// * observable with silent set capability; any method prefixed with s indicates that no
// * changes will be notified
// */
//public class ObservableCrime extends BaseObservable {
//    private UUID ID;
//    private Date date;
//    private String title;
//    private boolean serious;
//    private boolean solved;
//
//
//    public ObservableCrime() {
////        CrimeEntity entity = new CrimeEntity();
////        ID = entity.getID();
////        date = entity.getDate();
////        title = entity.getTitle();
////        serious = entity.isSeriousCrime();
////        solved = entity.isSolved();
//
//    }
//
//    public ObservableCrime(CrimeEntity entity) {
//
//        ID = entity.getID();
//        date = entity.getDate();
//        title = entity.getTitle();
//        serious = entity.isSeriousCrime();
//        solved = entity.isSolved();
//    }
//
//    public CrimeEntity retrieveBackingEntity() {
//        CrimeEntity entity = new CrimeEntity();
//
//        entity.setID(getID());
//        entity.setDate(getDate());
//
//        entity.setTitle(getTitle());
//        entity.setSeriousCrime(isSerious());
//        entity.setSolved(isSolved());
//
//        return entity;
//    }
//
//
//    @Bindable
//    public UUID getID() {
//        return ID;
//    }
//
//    public void setID(final UUID ID) {
//        if (ID != this.ID) {
//
//            this.ID = ID;
//
//            notifyPropertyChanged(BR.iD);
//
//        }
//    }
//
//    public void ssetID(final UUID ID) {
//        if (ID != this.ID) {
//
//            this.ID = ID;
//
//
//        }
//
//    }
//
//
//    @Bindable
//    public Date getDate() {
////        return Crime.crimeDateToString(date);
//        return date;
//    }
//
//
//
//    public void setDate(final Date date) {
//        if (this.date != date) {
//
//            this.date = date;
//
//            notifyPropertyChanged(BR.date);
//
//        }
//    }
//
//    public void ssetDate(final Date date) {
//        if (this.date != date) {
//
//            this.date = date;
//
//
//        }
//    }
//
//
//
//    @Bindable
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(final String title) {
//        if (this.title != title) {
//
//            this.title = title;
//
//            notifyPropertyChanged(BR.title);
//
//        }
//    }
//
//    public void ssetTitle(final String title) {
//        if (this.title != title) {
//
//            this.title = title;
//
//        }
//    }
//
//    @Bindable
//    public boolean isSerious() {
//        return serious;
//    }
//
//    public void setSerious(final boolean serious) {
//        if (this.serious != serious) {
//
//            this.serious = serious;
//
//            notifyPropertyChanged(BR.serious);
//
//        }
//    }
//    public void ssetSerious(final boolean serious) {
//        if (this.serious != serious) {
//
//            this.serious = serious;
//        }
//    }
//
//    @Bindable
//    public boolean isSolved() {
//        return solved;
//    }
//
//    public void setSolved(final boolean solved) {
//        if (this.solved != solved) {
//
//            this.solved = solved;
//
//            notifyPropertyChanged(BR.solved);
//
//        }
//    }
//    public void ssetSolved(final boolean solved) {
//        if (this.solved != solved) {
//
//            this.solved = solved;
//
//        }
//    }
//}
