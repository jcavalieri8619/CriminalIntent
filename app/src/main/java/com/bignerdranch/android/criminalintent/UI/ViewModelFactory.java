package com.bignerdranch.android.criminalintent.UI;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import com.bignerdranch.android.criminalintent.UI.crimedetail.DetailPagerViewModel;
import com.bignerdranch.android.criminalintent.UI.crimelist.ListViewModel;
import com.bignerdranch.android.criminalintent.repository.CrimeRepository;

public class ViewModelFactory implements android.arch.lifecycle.ViewModelProvider.Factory {
    CrimeRepository mDataSource;

    Activity mActivity;


    public ViewModelFactory(final CrimeRepository dataSource, Activity activity) {
        mDataSource = dataSource;
        this.mActivity = activity;

    }

    /**
     * Creates a new instance of the given {@code Class}.
     * <p>
     *
     * @param modelClass a {code Class} whose instance is requested
     * @return a newly created ViewModel
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull final Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListViewModel.class)) {
            return (T) new ListViewModel(mDataSource, mActivity.getApplication());
        }else if (modelClass.isAssignableFrom(DetailPagerViewModel.class)){
            return (T) new DetailPagerViewModel(mDataSource);

        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
