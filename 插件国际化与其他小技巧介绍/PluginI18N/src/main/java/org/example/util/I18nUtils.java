package org.example.util;


import java.util.Locale;
import java.util.ResourceBundle;

public class I18nUtils {

    public static String getLocalMessage(String langCode, String key) {
        return ResourceBundle.getBundle("i18n.plugin", Locale.forLanguageTag(langCode)).getString(key);
    }
}
