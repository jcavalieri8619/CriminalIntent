package com.bignerdranch.android.criminalintent.repository;

import android.arch.lifecycle.LiveData;

import com.bignerdranch.android.criminalintent.database.dao.CrimeDAO;
import com.bignerdranch.android.criminalintent.database.entity.CrimeEntity;

import java.util.List;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Single;

public class CrimeRepositoryImpl implements CrimeRepository {

    private static final String TAG = CrimeRepository.class.getSimpleName().concat("::JPC");


    final CrimeDAO mDAO;


    public CrimeRepositoryImpl(final CrimeDAO DAO) {
        mDAO = DAO;
    }



    @Override
    public LiveData<List<CrimeEntity>> getAllCrimes() {
        return mDAO.loadAll();
    }

    @Override
    public List<CrimeEntity> getForIDs(final UUID... crimeIDs) {
        return mDAO.loadAllForIDs(crimeIDs);
    }

    @Override
    public LiveData<List<CrimeEntity>> getBySerious(final boolean isSerious) {
        return mDAO.loadAllBySerious(isSerious);
    }

    @Override
    public Single<CrimeEntity> getForID(final UUID crime_uuid) {
        return mDAO.loadForID(crime_uuid);

    }

    @Override
    public Single<CrimeEntity> getForTitle(final String title) {
        return  mDAO.loadForTitle(title);



    }

    @Override
    public LiveData<List<CrimeEntity>> getBySolved(final boolean isSolved) {
        return mDAO.loadAllBySolved(isSolved);
    }

    @Override
    public Single<CrimeEntity> getHeadRow() {
        return mDAO.loadHeaderRow();
    }

    @Override
    public Completable insert(final CrimeEntity... crimes) {
        return Completable.fromAction(() -> mDAO.insert(crimes));
    }



    @Override
    public Completable delete(final CrimeEntity... crimes) {
        return Completable.fromAction(() -> mDAO.delete(crimes));
    }

    @Override
    public Completable update(final CrimeEntity crime) {
        return Completable.fromAction(() -> mDAO.update(crime));
    }

}
