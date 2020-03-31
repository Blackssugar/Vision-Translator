package com.comp5425.visiontranslator;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "translationlist")
public class Translation {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "translationID")
    private int translationID;

    @ColumnInfo(name = "translationChinese")
    private String translationChinese;

    @ColumnInfo(name = "translationEnglish")
    private String translationEnglish;

    public Translation(String translationChinese, String translationEnglish){
        this.translationChinese = translationChinese;
        this.translationEnglish = translationEnglish;
    }

    @NonNull
    public int getTranslationID() {
        return translationID;
    }

    public String getTranslationChinese() {
        return translationChinese;
    }

    public void setTranslationChinese(String translationChinese) {
        this.translationChinese = translationChinese;
    }

    public String getTranslationEnglish() { return translationEnglish; }

    public void setTranslationEnglish(String translationEnglish) {
        this.translationEnglish = translationEnglish;
    }

    public void setTranslationID(@NonNull int translationID) {
        this.translationID = translationID;
    }

}

