package com.comp5425.visiontranslator;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TranslationItemDao {
    @Query("SELECT * FROM translationlist")
    List<Translation> listAll();

    @Insert
    void insert(Translation translationItem);

    @Insert
    void insertAll(Translation... translationItems);

    @Query("DELETE FROM translationlist")
    void deleteAll();
}
