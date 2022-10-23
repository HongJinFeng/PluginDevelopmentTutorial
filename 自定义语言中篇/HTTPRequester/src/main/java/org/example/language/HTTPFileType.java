package org.example.language;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class HTTPFileType extends LanguageFileType {

    public static final HTTPFileType INSTANCE = new HTTPFileType();

    public HTTPFileType() {
        super(HTTPLanguage.INSTANCE);
    }

    @Override
    public @NotNull String getName() {
        return "HTTP";
    }

    @Override
    public @NotNull String getDescription() {
        return "HTTP language file";
    }

    @Override
    public @NotNull String getDefaultExtension() {
        return "http";
    }

    @Override
    public @Nullable Icon getIcon() {
        return HTTPIcons.FILE;
    }
}
