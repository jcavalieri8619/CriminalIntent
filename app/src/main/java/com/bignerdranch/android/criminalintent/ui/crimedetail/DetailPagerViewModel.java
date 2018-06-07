package com.bignerdranch.android.criminalintent.ui.crimedetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bignerdranch.android.criminalintent.HeaderGenerator;
import com.bignerdranch.android.criminalintent.database.entity.CrimeEntity;
import com.bignerdranch.android.criminalintent.repository.CrimeRepository;

import java.util.Collections;
import java.util.List;

public class DetailPagerViewModel extends ViewModel {

    final private static String TAG = DetailPagerViewModel.class.getSimpleName().concat("::JPC");


    final private CrimeRepository mDataSource;


    private final MediatorLiveData<List<CrimeEntity>> mObservableCrimes;


    public DetailPagerViewModel(final CrimeRepository dataSource) {
        Log.d(TAG, "DetailPagerViewModel: viewModel lifecycle START");
        mDataSource = dataSource;

        mObservableCrimes = new MediatorLiveData<>();

        mObservableCrimes.setValue(null);


        LiveData<List<CrimeEntity>> crimeList = mDataSource.getAllCrimes();

        mObservableCrimes.addSource(crimeList, new Observer<List<CrimeEntity>>() {
            @Override
            public void onChanged(@Nullable final List<CrimeEntity> crimeEntities) {
                Log.d(TAG, "onChanged: inside MediatorLiveData - emitted: "
                        + String.valueOf(crimeEntities.size()));


                mObservableCrimes.setValue(crimeEntities);

            }
        });

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

    public List<CrimeEntity> initEmptyPager() {
        Log.d(TAG, "initEmptyPager: initializing pager with single empty crime");
        return (Collections.singletonList(HeaderGenerator.getHeaderEntity()));

    }




    public LiveData<List<CrimeEntity>> getAllCrimes() {

        return mObservableCrimes;
    }


}
