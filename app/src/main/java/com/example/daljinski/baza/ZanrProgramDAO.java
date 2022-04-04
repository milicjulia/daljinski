package com.example.daljinski.baza;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface ZanrProgramDAO {
    @Query("SELECT * FROM ZanrProgramEntity")
    Flowable<List<ZanrProgramEntity>> getZP();

    @Query("SELECT * FROM ZanrProgramEntity WHERE idPrograma = :idc ")
    List<ZanrProgramEntity> getGenresForProgram(int idc);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertZanrProgram(ZanrProgramEntity zp);


    @Query("DELETE FROM ZanrProgramEntity")
    void deleteAllZP();
}
