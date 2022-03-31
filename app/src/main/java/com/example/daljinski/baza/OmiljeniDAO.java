package com.example.daljinski.baza;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface OmiljeniDAO {
    @Query("SELECT * FROM OmiljeniEntity")
    Flowable<List<OmiljeniEntity>> getOmiljene();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOmiljen(OmiljeniEntity omiljen);


    @Query("DELETE FROM OmiljeniEntity")
    void deleteAllOmiljeni();
}
