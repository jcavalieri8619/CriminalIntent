package com.bignerdranch.android.criminalintent.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.bignerdranch.android.criminalintent.database.entity.CrimeEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import io.reactivex.Completable;

public class MockCrimeRepositoryImpl implements CrimeRepository {

    private static final String TAG = MockCrimeRepositoryImpl.class.getSimpleName();

    List<CrimeEntity> mCrimeList;

    MutableLiveData<List<CrimeEntity>> mListMutableLiveData;

    public MockCrimeRepositoryImpl() {

        Log.d(TAG, "MockCrimeRepositoryImpl: CTOR");

        mCrimeList = new ArrayList<>();
        mListMutableLiveData = new MutableLiveData<>();

        for (int i = 0; i < 10; i++) {
            CrimeEntity crime = new CrimeEntity();

            crime.setTitle("CrimeEntity: " + i);
            crime.setSolved(i % 2 == 0);

            mCrimeList.add(crime);

        }


        mListMutableLiveData.setValue(mCrimeList);

    }



    @Override
    public LiveData<List<CrimeEntity>> getAllCrimes() {
        return mListMutableLiveData;
    }

    @Override
    public LiveData<List<CrimeEntity>> getBySerious(final boolean isSerious) {
        return null;
    }

    @Override
    public LiveData<CrimeEntity> getByUUID(final UUID crime_uuid) {
        return null;
    }

    @Override
    public LiveData<CrimeEntity> getByTitle(final String title) {
        return null;
    }

    @Override
    public LiveData<List<CrimeEntity>> getBySolved(final boolean isSolved) {
        return null;
    }

    @Override
    public Completable insert(final CrimeEntity... crimes) {
        return Completable.fromAction(() ->
                {
                    Log.d(TAG, "insert: ");

                    mCrimeList.addAll(Arrays.asList(crimes));
                    mListMutableLiveData.postValue(mCrimeList);

                }

        );
    }


    @Override
    public Completable delete(final CrimeEntity... crime) {
        return null;
    }

    @Override
    public Completable update(final CrimeEntity... crimes) {
        return null;
    }
}
