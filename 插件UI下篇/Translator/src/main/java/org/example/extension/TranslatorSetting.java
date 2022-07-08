package org.example.extension;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "translator", storages = {@Storage(value = "translator.xml")})
public class TranslatorSetting implements PersistentStateComponent<TranslatorSetting> {

    public String appID;

    public String securityKey;

    public static TranslatorSetting getInstance() {
        return ApplicationManager.getApplication().getService(TranslatorSetting.class);
    }

    @Override
    public @Nullable TranslatorSetting getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull TranslatorSetting state) {
        this.appID = state.appID;
        this.securityKey = state.securityKey;
    }
}
