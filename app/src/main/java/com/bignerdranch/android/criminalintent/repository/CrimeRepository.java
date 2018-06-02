package com.bignerdranch.android.criminalintent.repository;

import android.arch.lifecycle.LiveData;

import com.bignerdranch.android.criminalintent.database.entity.CrimeEntity;

import java.util.List;
import java.util.UUID;

import io.reactivex.Completable;

public interface CrimeRepository {


    LiveData<List<CrimeEntity>> getAllCrimes();

//    List<CrimeEntity> loadAllByIds(UUID... crimeIDs);


    LiveData<List<CrimeEntity>> getBySerious(boolean isSerious);



    LiveData<CrimeEntity> getByUUID(UUID crime_uuid);



    LiveData<CrimeEntity> getByTitle(String title);


    LiveData<List<CrimeEntity>> getBySolved(boolean isSolved);





    Completable insert(CrimeEntity... crimes);



    Completable delete(CrimeEntity... crime);


    Completable update(CrimeEntity... crimes);



}
