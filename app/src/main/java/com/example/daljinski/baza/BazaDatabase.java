package com.example.daljinski.baza;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ChannelEntity.class, ProgramEntity.class, OmiljeniEntity.class, ZanroviEntity.class, ZanrProgramEntity.class}, version = 2, exportSchema = false)
public abstract class BazaDatabase extends RoomDatabase {
    public abstract ChannelDAO channelDao();
    public abstract ProgramDAO programDao();
    public abstract OmiljeniDAO omiljeniDAO();
    public abstract ZanrDAO zanrDAO();
    public abstract ZanrProgramDAO zanrProgramDAO();
}
