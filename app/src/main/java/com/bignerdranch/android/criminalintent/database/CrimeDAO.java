package com.bignerdranch.android.criminalintent.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bignerdranch.android.criminalintent.database.entity.CrimeEntity;

import java.util.List;
import java.util.UUID;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CrimeDAO {

    @Query("SELECT * FROM crimesDB")
    LiveData<List<CrimeEntity>> getAll();

//    @Query("SELECT * FROM crimesDB WHERE uuid_column IN (:crimeIDs)")
//    List<CrimeEntity> loadAllByIds(UUID[] crimeIDs);


    @Query("SELECT * FROM crimesDB WHERE is_serious_column == :isSerious")
    LiveData<List<CrimeEntity>> loadAllBySerious(boolean isSerious);


    @Query("SELECT * FROM crimesDB WHERE uuid_column LIKE :crime_uuid "
            + "LIMIT 1")
    LiveData<CrimeEntity> findByUUID(UUID crime_uuid);


    @Query("SELECT * FROM crimesDB WHERE title_column LIKE :title LIMIT 1")
    LiveData<CrimeEntity> findByTitle(String title);


    @Query("SELECT * FROM crimesDB WHERE is_solved_column == :isSolved")
    LiveData<List<CrimeEntity>> loadAllBySolved(boolean isSolved);




    @Insert(onConflict = REPLACE)
    void insert(CrimeEntity... crimes);

    @Delete
    void delete(CrimeEntity... crimes);

    @Update(onConflict = REPLACE)
    void update(CrimeEntity... crimes);

}
