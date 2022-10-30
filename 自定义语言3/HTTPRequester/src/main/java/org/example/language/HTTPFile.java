package org.example.language;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

public class HTTPFile extends PsiFileBase {

    protected HTTPFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, HTTPLanguage.INSTANCE);
    }

    @Override
    public @NotNull FileType getFileType() {
        return HTTPFileType.INSTANCE;
    }
}
