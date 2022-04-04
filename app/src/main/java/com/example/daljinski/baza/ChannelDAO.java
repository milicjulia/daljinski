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
public interface ChannelDAO {
    @Query("SELECT * FROM ChannelEntity")
    Flowable<List<ChannelEntity>> getChannels();

    @Query("SELECT * FROM ChannelEntity WHERE id = :idc ")
    Maybe<ChannelEntity> getChannelByID(int idc);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertChannel(ChannelEntity channel);

    @Query("SELECT * FROM ChannelEntity JOIN ProgramEntity ON ChannelEntity.brojKanala = ProgramEntity.idKanala")
    Map<ChannelEntity, List<ProgramEntity>> getProgramsForChannels();

    @Query("DELETE FROM ChannelEntity")
    void deleteAllChannels();
}
