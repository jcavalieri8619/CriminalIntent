package com.bignerdranch.android.criminalintent.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bignerdranch.android.criminalintent.HeaderGenerator;
import com.bignerdranch.android.criminalintent.database.converter.Converters;
import com.bignerdranch.android.criminalintent.database.dao.CrimeDAO;
import com.bignerdranch.android.criminalintent.database.entity.CrimeEntity;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Database(entities = {CrimeEntity.class}, version = 1, exportSchema = true)

@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = AppDatabase.class.getSimpleName().concat("::JPC");
    public static final String DATABASE_NAME = "crime_app_db";

    private static volatile AppDatabase INSTANCE;

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();


    public abstract CrimeDAO crimeDAO();

    public static AppDatabase getInstance(Context context) {

        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {

                    Log.d(TAG, "getInstance: building ROOM DB and attempting to add header row");

                    INSTANCE = buildDatabase(context);

                }
            }
        }
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(Context context) {

        return Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    /**
                     * Called when the database is created for the first time. This is called after all the
                     * tables are created.
                     *
                     * @param db The database.
                     */
                    @Override
                    public void onCreate(@NonNull final SupportSQLiteDatabase db) {
                        super.onCreate(db);

                        Log.d(TAG, "onCreate: ");

                        CrimeEntity headerRow = HeaderGenerator.getHeaderEntity();

                        insertHeaderRow(getInstance(context), headerRow)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(final Disposable d) {

                                    }

                                    @Override
                                    public void onComplete() {

                                        Log.d(TAG, "onComplete: INSERTED HEADER ROW SUCESSFULLY");
                                        getInstance(context).setDatabaseCreated();

                                    }

                                    @Override
                                    public void onError(final Throwable e) {

                                        // this means we added headRow to DB last app run and no need to again
                                        Log.d(TAG, "onError: FAILED TO INSERT HEADER ROW-->DB already initialized", e);

                                    }
                                });

                    }

                    /**
                     * Called when the database has been opened.
                     *
                     * @param db The database.
                     */
                    @Override
                    public void onOpen(@NonNull final SupportSQLiteDatabase db) {
                        super.onOpen(db);
                        Log.d(TAG, "onOpen: ");
                    }
                })
                .build();

    }

    private static Completable insertHeaderRow(final AppDatabase db, final CrimeEntity header_row) {


        return Completable.fromAction(() -> {
            db.runInTransaction(() -> db.crimeDAO().insert(header_row));
        });

    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }



}
