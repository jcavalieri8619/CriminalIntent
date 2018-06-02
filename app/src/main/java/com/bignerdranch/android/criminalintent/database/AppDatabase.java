package com.bignerdranch.android.criminalintent.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.bignerdranch.android.criminalintent.database.entity.CrimeEntity;

@Database(entities = {CrimeEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = AppDatabase.class.getSimpleName();
    private static volatile AppDatabase INSTANCE;
    public abstract CrimeDAO crimeDAO();

    public static AppDatabase getInstance(Context context) {
        Log.d(TAG, "getInstance: ");

        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {

                    Log.d(TAG, "getInstance: setting instance to ROOM DB");

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app.DB")
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
