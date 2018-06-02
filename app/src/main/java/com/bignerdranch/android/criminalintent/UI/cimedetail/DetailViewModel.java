package com.bignerdranch.android.criminalintent.UI.cimedetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.bignerdranch.android.criminalintent.UI.CrimeFields;
import com.bignerdranch.android.criminalintent.database.entity.CrimeEntity;
import com.bignerdranch.android.criminalintent.repository.CrimeRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailViewModel extends ViewModel {

    final private static String TAG = DetailViewModel.class.getSimpleName();

    final private CrimeRepository mDataSource;

    private CrimeFields mFields;
    private List<CrimeEntity> currCrimesList = new ArrayList<>();

//    public MutableLiveData<List<CrimeEntity>> mCrimeMutableLiveData = new MutableLiveData<>();


    public DetailViewModel(final CrimeRepository dataSource) {
        mDataSource = dataSource;

        mFields = new CrimeFields();

    }


    private void crime_to_fields(CrimeEntity crime) {
        if (crime == null) {
            Log.d(TAG, "findCrimeInSynListByUUID: NULL ARG IN crime_to_fields ");
            return;
        }
        mFields.setSolved(crime.isSolved());
        mFields.setSeriousCrime(crime.isSeriousCrime());
        mFields.setDate(crime.getDate());
        mFields.setTitle(crime.getTitle());

    }

    private CrimeEntity findCrimeInSynListByUUID(UUID uuid) {
        for (CrimeEntity crime :
                currCrimesList) {
            if (crime.getID().equals(uuid)) {
                return crime;
            }
        }

        Log.d(TAG, "findCrimeInSynListByUUID: COULDNT FIND CRIME IN SYNCH LIST ");
        return null;
    }

    public void initFieldsFromUUID(UUID uuid) {
        crime_to_fields(findCrimeInSynListByUUID(uuid));
    }

    public void setTitle(String title) {
        mFields.setTitle(title);
    }

    public String getTitle() {
        return mFields.getTitle();
    }


    public Date getDate() {
        return mFields.getDate();
    }

    public String getDateString() {
        return mFields.getFormattedDate();
    }

    public void setDate(Date date) {
        mFields.setDate(date);

    }

    public void setSeriousFlag(boolean isSerious) {

        mFields.setSeriousCrime(isSerious);
    }

    public boolean getSeriousFlag() {
        return mFields.isSeriousCrime();
    }

    public void setSolvedFlag(boolean isSolved) {
        mFields.setSolved(isSolved);

    }

    public boolean getSolvedFlag() {
        return mFields.isSolved();
    }

    public LiveData<List<CrimeEntity>> getAllCrimes() {
//        currCrimesList = mDataSource.getAllCrimes_synch();
//        mCrimeMutableLiveData.setValue(currCrimesList);
//        return mCrimeMutableLiveData;

        return mDataSource.getAllCrimes();
    }

    public void addCrime() {


        mDataSource.insert(new CrimeEntity(mFields))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(final Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                        Log.d(TAG, "onComplete: added crime to Repo");
                    }

                    @Override
                    public void onError(final Throwable e) {
                        Log.d(TAG, "onError: failed to add crime to Repo", e);
                    }
                });

    }
}
