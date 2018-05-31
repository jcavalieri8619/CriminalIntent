package com.bignerdranch.android.criminalintent.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.bignerdranch.android.criminalintent.models.Crime;

@Database(entities = {Crime.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract CrimeDAO crimeDAO();
}
