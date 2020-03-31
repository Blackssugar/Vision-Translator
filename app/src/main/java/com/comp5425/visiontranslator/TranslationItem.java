package com.comp5425.visiontranslator;

public class TranslationItem {

    private String translation;
    private String recognition;

    public TranslationItem(String translation, String recognition){
        this.translation = translation;
        this.recognition = recognition;
    }

    public String getTranslation() {
        return translation;
    }
    public String getRecogntion() {
        return recognition;
    }

}
