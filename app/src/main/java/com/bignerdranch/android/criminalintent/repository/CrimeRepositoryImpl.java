package com.bignerdranch.android.criminalintent.repository;

import android.arch.lifecycle.LiveData;

import com.bignerdranch.android.criminalintent.database.CrimeDAO;
import com.bignerdranch.android.criminalintent.database.entity.CrimeEntity;

import java.util.List;
import java.util.UUID;

import io.reactivex.Completable;

public class CrimeRepositoryImpl implements CrimeRepository {

    private static final String TAG = CrimeRepository.class.getSimpleName();

    final CrimeDAO mDAO;


    public CrimeRepositoryImpl(final CrimeDAO DAO) {
        mDAO = DAO;
    }



    @Override
    public LiveData<List<CrimeEntity>> getAllCrimes() {
        return mDAO.getAll();
    }

    @Override
    public LiveData<List<CrimeEntity>> getBySerious(final boolean isSerious) {
        return mDAO.loadAllBySerious(isSerious);
    }

    @Override
    public LiveData<CrimeEntity> getByUUID(final UUID crime_uuid) {
        return mDAO.findByUUID(UUID.randomUUID());
    }

    @Override
    public LiveData<CrimeEntity> getByTitle(final String title) {
        return mDAO.findByTitle(title);
    }

    @Override
    public LiveData<List<CrimeEntity>> getBySolved(final boolean isSolved) {
        return mDAO.loadAllBySolved(isSolved);
    }

    @Override
    public Completable insert(final CrimeEntity... crimes) {
        return Completable.fromAction(() -> {
            mDAO.insert(crimes);

        });
    }



    @Override
    public Completable delete(final CrimeEntity... crimes) {
        return Completable.fromAction(() -> mDAO.delete(crimes));
    }

    @Override
    public Completable update(final CrimeEntity... crimes) {
        return Completable.fromAction(() -> mDAO.update(crimes));
    }

}
