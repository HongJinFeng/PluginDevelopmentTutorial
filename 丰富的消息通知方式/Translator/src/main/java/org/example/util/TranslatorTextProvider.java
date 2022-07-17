package org.example.util;

import com.intellij.ui.TextFieldWithAutoCompletionListProvider;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class TranslatorTextProvider extends TextFieldWithAutoCompletionListProvider<String> {

    public static Set<String> items = new HashSet<>();

    public TranslatorTextProvider() {
        super(items);
    }

    @Override
    protected @NotNull String getLookupString(@NotNull String item) {
        return item.substring(item.lastIndexOf(" ") + 1);
    }
}
