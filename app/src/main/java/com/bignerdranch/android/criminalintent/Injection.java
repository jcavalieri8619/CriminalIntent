package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Context;

import com.bignerdranch.android.criminalintent.UI.ViewModelFactory;
import com.bignerdranch.android.criminalintent.UI.crimedetail.DetailViewModel;
import com.bignerdranch.android.criminalintent.database.AppDatabase;
import com.bignerdranch.android.criminalintent.repository.CrimeRepository;
import com.bignerdranch.android.criminalintent.repository.CrimeRepositoryImpl;

import java.util.UUID;

public class Injection {

    public static CrimeRepository provideCrimeRepository(Context ctx) {
        return new CrimeRepositoryImpl(AppDatabase.getInstance(ctx).crimeDAO());

//        return new MockCrimeRepositoryImpl();

    }

    public static ViewModelFactory provideViewModelFactory(Context ctx) {

        return new ViewModelFactory(provideCrimeRepository(ctx),((Activity)ctx) );
    }

    public static DetailViewModel.Factory provideDetailModelFactory(Context ctx,UUID ID) {
        return new DetailViewModel.Factory(ID, provideCrimeRepository(ctx));
    }


}
