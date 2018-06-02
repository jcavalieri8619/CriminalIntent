package com.bignerdranch.android.criminalintent.UI.crimelist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.bignerdranch.android.criminalintent.database.entity.CrimeEntity;
import com.bignerdranch.android.criminalintent.model.Crime;
import com.bignerdranch.android.criminalintent.repository.CrimeRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {

    private static final String TAG = ListViewModel.class.getSimpleName();
    final private CrimeRepository mDataSource;


    public List<CrimeEntity> currCrimesList = new ArrayList<>();

    public MutableLiveData<List<CrimeEntity>> mCrimeMutableLiveData = new MutableLiveData<>();


    public ListViewModel(final CrimeRepository dataSource) {
        mDataSource = dataSource;


    }


    public void deleteCrime(CrimeEntity crime) {
        mDataSource.delete(crime).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(final Disposable d) {
                Log.d(TAG, "onSubscribe: ");

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: deleted crime from list view");
            }

            @Override
            public void onError(final Throwable e) {
                Log.e(TAG, "onError: ", e);
            }
        });

    }

    public LiveData<List<CrimeEntity>> getAllCrimes() {

        return mDataSource.getAllCrimes();
    }
}
