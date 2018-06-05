package com.bignerdranch.android.criminalintent.repository;

import android.arch.lifecycle.LiveData;

import com.bignerdranch.android.criminalintent.database.entity.CrimeEntity;
import com.bignerdranch.android.criminalintent.model.Crime;

import java.util.List;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface CrimeRepository {


    LiveData<List<CrimeEntity>> getAllCrimes();

    List<CrimeEntity> getForIDs(UUID... crimeIDs);


    LiveData<List<CrimeEntity>> getBySerious(boolean isSerious);



    Single<CrimeEntity> getForID(UUID crime_uuid);



    Single<CrimeEntity> getForTitle(String title);


    LiveData<List<CrimeEntity>> getBySolved(boolean isSolved);


    Single<CrimeEntity> getHeadRow();


    Completable insert(CrimeEntity... crimes);



    Completable delete(CrimeEntity... crime);


    Completable update(CrimeEntity  crime);



}
