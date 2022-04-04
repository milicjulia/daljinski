package com.example.daljinski.baza;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface ProgramDAO {
    @Query("SELECT * FROM ProgramEntity")
    List<ProgramEntity> getAllPrograms();

    @Query("SELECT ProgramEntity.url FROM ProgramEntity WHERE id = :idp ")
    Maybe<String> getImageForProgram(long idp);

    @Query("SELECT ProgramEntity.id FROM ProgramEntity WHERE id = :idp ")
    int getIdProgram(long idp);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProgram(ProgramEntity program);

    @Query("DELETE FROM ProgramEntity")
    void deleteAllPrograms();
}
