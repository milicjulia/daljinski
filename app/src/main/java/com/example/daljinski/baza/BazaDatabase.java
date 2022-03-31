package com.example.daljinski.baza;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ChannelEntity.class, ProgramEntity.class, OmiljeniEntity.class}, version = 2, exportSchema = true)
public abstract class BazaDatabase extends RoomDatabase {
    public abstract ChannelDAO channelDao();
    public abstract ProgramDAO programDao();
    public abstract OmiljeniDAO omiljeniDAO();
}
