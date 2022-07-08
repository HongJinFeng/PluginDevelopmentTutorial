package org.example.extension;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

@State(name = "translator.cache", storages = {@Storage(value = "translator-cache.xml")})
public class TranslatorCache implements PersistentStateComponent<TranslatorCache> {

    public Map<String, String> transCache = new LinkedHashMap<>();

    public static TranslatorCache getInstance(Project project) {
        return project.getService(TranslatorCache.class);
    }

    @Override
    public @Nullable TranslatorCache getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull TranslatorCache state) {
        if (state.transCache == null) {
            return;
        }
        this.transCache = state.transCache;
    }

}
