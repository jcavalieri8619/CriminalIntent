package com.bignerdranch.android.criminalintent.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bignerdranch.android.criminalintent.models.Crime;

import java.util.List;
import java.util.UUID;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CrimeDAO {

    @Query("SELECT * FROM crimesDB")
    LiveData<List<Crime>> getAll();

    @Query("SELECT * FROM crimesDB WHERE uuid_column IN (:crimeIDs)")
    LiveData<List<Crime>> loadAllByIds(UUID[] crimeIDs);


    @Query("SELECT * FROM crimesDB WHERE is_serious_column == :isSerious")
    LiveData<List<Crime>> loadAllBySerious(boolean isSerious);


    @Query("SELECT * FROM crimesDB WHERE uuid_column LIKE :crime_uuid "
            + "LIMIT 1")
    LiveData<Crime> findByUUID(UUID crime_uuid);


    @Query("SELECT * FROM crimesDB WHERE title_column LIKE :title LIMIT 1")
    LiveData<Crime> findByTitle(String title);


    @Query("SELECT * FROM crimesDB WHERE is_solved_column == :isSolved")
    LiveData<List<Crime>> loadAllBySolved(boolean isSolved);




    @Insert(onConflict = REPLACE)
    void insert(Crime... crimes);

    @Delete
    void delete(Crime crime);

    @Update(onConflict = REPLACE)
    void update(Crime... users);

}
