package com.bignerdranch.android.criminalintent;

import android.content.Context;

import com.bignerdranch.android.criminalintent.UI.ViewModelFactory;
import com.bignerdranch.android.criminalintent.database.AppDatabase;
import com.bignerdranch.android.criminalintent.repository.CrimeRepository;
import com.bignerdranch.android.criminalintent.repository.CrimeRepositoryImpl;
import com.bignerdranch.android.criminalintent.repository.MockCrimeRepositoryImpl;

public class Injection {

    public static CrimeRepository provideCrimeRepository(Context ctx) {
        return new CrimeRepositoryImpl(AppDatabase.getInstance(ctx).crimeDAO());

//        return new MockCrimeRepositoryImpl();

    }

    public static ViewModelFactory provideViewModelFactory(Context ctx) {

        return new ViewModelFactory(provideCrimeRepository(ctx));
    }


}
