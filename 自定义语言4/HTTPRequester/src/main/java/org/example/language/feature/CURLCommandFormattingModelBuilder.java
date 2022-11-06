package org.example.language.feature;

import com.intellij.formatting.*;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import org.example.language.CURLTokenSets;
import org.example.language.HTTPLanguage;
import org.jetbrains.annotations.NotNull;

public class CURLCommandFormattingModelBuilder implements FormattingModelBuilder {

    private static SpacingBuilder createSpaceBuilder(CodeStyleSettings settings) {
        return new SpacingBuilder(settings, HTTPLanguage.INSTANCE)
                .after(CURLTokenSets.CURL).spaces(1).
                before(CURLTokenSets.CURL).spaces(0).
                around(CURLTokenSets.OPTION).spaces(1).
                around(CURLTokenSets.QUOTED_STRING).spaces(1).
                before(CURLTokenSets.URL).spaces(1).
                after(CURLTokenSets.URL).lineBreakInCode();
    }

    @Override
    public @NotNull FormattingModel createModel(@NotNull FormattingContext formattingContext) {
        CodeStyleSettings codeStyleSettings = formattingContext.getCodeStyleSettings();
        return FormattingModelProvider
                .createFormattingModelForPsiFile(formattingContext.getContainingFile(),
                        new CURLCommandBlock(formattingContext.getNode(),
                                Wrap.createWrap(WrapType.NONE, false),
                                Alignment.createAlignment(),
                                createSpaceBuilder(codeStyleSettings)),
                        codeStyleSettings);
    }
}
