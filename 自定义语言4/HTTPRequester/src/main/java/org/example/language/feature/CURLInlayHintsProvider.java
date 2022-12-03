package org.example.language.feature;

import com.intellij.codeInsight.hints.*;
import com.intellij.lang.Language;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import org.example.language.HTTPLanguage;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CURLInlayHintsProvider implements InlayHintsProvider<NoSettings> {

    @Override
    public boolean isVisibleInSettings() {
        return true;
    }

    @NotNull
    @Override
    public SettingsKey<NoSettings> getKey() {
        return new SettingsKey<>("settings.inlay.curl");
    }

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @NotNull
    @Override
    public String getName() {
        return "Curl.inlay.setting";
    }

    @Nullable
    @Override
    public String getPreviewText() {
        return null;
    }

    @NotNull
    @Override
    public ImmediateConfigurable createConfigurable(@NotNull NoSettings noSettings) {
        return changeListener -> new JPanel();
    }

    @NotNull
    @Override
    public NoSettings createSettings() {
        return new NoSettings();
    }

    @Nullable
    @Override
    public InlayHintsCollector getCollectorFor(@NotNull PsiFile psiFile, @NotNull Editor editor, @NotNull NoSettings noSettings, @NotNull InlayHintsSink inlayHintsSink) {
        return new CURLInlayHintCollector(editor);
    }

    @Override
    public boolean isLanguageSupported(@NotNull Language language) {
        return language == HTTPLanguage.INSTANCE;
    }
}
