package org.example.language.feature;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public class CURLCommandInsertHandler implements InsertHandler<LookupElement> {

    @Override
    public void handleInsert(@NotNull InsertionContext context, @NotNull LookupElement item) {
        PsiElement element = context.getFile().findElementAt(context.getStartOffset());
        context.getDocument().replaceString(element.getTextOffset(), context.getTailOffset(), item.getLookupString());
        context.getEditor().getCaretModel().moveToOffset(context.getStartOffset() + context.getTailOffset() + 1);
    }

}
