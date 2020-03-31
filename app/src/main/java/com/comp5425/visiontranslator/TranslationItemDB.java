package com.comp5425.visiontranslator;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Translation.class}, version = 1, exportSchema = false)
public abstract class TranslationItemDB extends RoomDatabase {
    private static final String DATABASE_NAME = "translationitem_db";
    private static TranslationItemDB DBINSTANCE;

    public abstract TranslationItemDao translationItemDao();

    public static TranslationItemDB getDatabase(Context context) {
        if (DBINSTANCE == null) {
            synchronized (TranslationItemDB.class) {
                DBINSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        TranslationItemDB.class, DATABASE_NAME).build();
            }
        }
        return DBINSTANCE;
    }

    public static void destroyInstance() {
        DBINSTANCE = null;
    }
}
