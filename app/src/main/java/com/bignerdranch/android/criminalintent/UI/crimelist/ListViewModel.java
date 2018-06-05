package com.bignerdranch.android.criminalintent.UI.crimelist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.bignerdranch.android.criminalintent.database.entity.CrimeEntity;
import com.bignerdranch.android.criminalintent.model.Crime;
import com.bignerdranch.android.criminalintent.repository.CrimeRepository;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {

    private static final String TAG = ListViewModel.class.getSimpleName().concat("::JPC");



    final private CrimeRepository mDataSource;

    private boolean mIsSubtitleVisible;


    public ListViewModel(final CrimeRepository dataSource) {
        Log.d(TAG, "ListViewModel: viewModel lifecycle START");
        mDataSource = dataSource;

        mIsSubtitleVisible=false;

    }

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     * <p>
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "onCleared: viewModel lifecycle END");

    }

    boolean isSubtitleVisible() {
        return mIsSubtitleVisible;
    }

    void negateSubtitleVisiblity() {
        mIsSubtitleVisible = !mIsSubtitleVisible;
    }


    public void deleteCrime(Crime crime) {
        Log.d(TAG, "deleteCrime: deleting crime from repo");

        mDataSource.delete((CrimeEntity) crime ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(final Disposable d) {
                Log.d(TAG, "onSubscribe: for deleting from repo  "+Thread.currentThread().getName() );
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete:  deleted crime from list view on: "+Thread.currentThread().getName());
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
