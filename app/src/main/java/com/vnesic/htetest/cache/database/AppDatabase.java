package com.vnesic.htetest.cache.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Post.class}, version = 2, exportSchema = false )
public abstract class AppDatabase extends RoomDatabase {
    public abstract PostDao userDao();
}
