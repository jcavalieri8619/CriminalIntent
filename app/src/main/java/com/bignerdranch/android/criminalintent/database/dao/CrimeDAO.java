package com.bignerdranch.android.criminalintent.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bignerdranch.android.criminalintent.database.entity.CrimeEntity;
import com.bignerdranch.android.criminalintent.model.Crime;

import java.util.List;
import java.util.UUID;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

import static android.arch.persistence.room.OnConflictStrategy.FAIL;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CrimeDAO {

    @Query("SELECT * FROM crimesDB ")
    LiveData<List<CrimeEntity>> loadAll();

    @Query("SELECT * FROM crimesDB WHERE uuid_column IN (:crimeIDs)")
    List<CrimeEntity> loadAllForIDs(UUID... crimeIDs);


    @Query("SELECT * FROM crimesDB WHERE is_serious_column == :isSerious ")
    LiveData<List<CrimeEntity>> loadAllBySerious(boolean isSerious);


    @Query("SELECT * FROM crimesDB WHERE uuid_column LIKE :crime_uuid "
            + "LIMIT 1")
    Single<CrimeEntity> loadForID(UUID crime_uuid);

    @Query("SELECT * FROM crimesDB WHERE uuid_column LIKE :crime_uuid "
            + "LIMIT 1")
    Flowable<CrimeEntity> loadForID_observable(UUID crime_uuid);


    @Query("SELECT * FROM crimesDB WHERE title_column LIKE :title LIMIT 1")
    Single<CrimeEntity> loadForTitle(String title);

    @Query("SELECT * FROM crimesDB ORDER BY ROWID ASC LIMIT 1")
    Single<CrimeEntity> loadHeaderRow();


    @Query("SELECT * FROM crimesDB WHERE is_solved_column == :isSolved ")
    LiveData<List<CrimeEntity>> loadAllBySolved(boolean isSolved);




    @Insert(onConflict = FAIL)
    void insert(CrimeEntity... crimes);

    @Delete
    void delete(CrimeEntity... crimes);

    @Update(onConflict = REPLACE)
    void update(CrimeEntity crime);

}
