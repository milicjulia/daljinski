package com.example.daljinski.baza;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface OmiljeniDAO {
    @Query("SELECT * FROM OmiljeniEntity")
    List<OmiljeniEntity> getOmiljene();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOmiljen(OmiljeniEntity omiljen);

    @Query("UPDATE OmiljeniEntity SET kolicina = :kol WHERE tip =:t")
    void update(int kol, String t);


    @Query("DELETE FROM OmiljeniEntity")
    void deleteAllOmiljeni();
}
