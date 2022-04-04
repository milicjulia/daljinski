package com.example.daljinski.baza;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ZanrDAO {
    @Query("SELECT * FROM ZanroviEntity")
    Flowable<List<ZanroviEntity>> getZanrove();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertZanr(ZanroviEntity zanr);


    @Query("DELETE FROM ZanroviEntity")
    void deleteAllZanrovi();
}
