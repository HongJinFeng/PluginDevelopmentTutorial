package org.example.language.feature;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.StandardPatterns;
import com.intellij.util.ProcessingContext;
import org.example.language.HTTPFile;
import org.example.language.HTTPIcons;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CURLCommandCodeContributor extends CompletionContributor {

    private final static List<String> completionItems = List.of("curl", "--append", "--user-agent", "-anyauth", "--user", "get", "--request", "--header",
            "--output", "http://", "https://", "GET", "HEAD", "POST", "PUT", "DELETE", "CONNECT", "OPTIONS", "TRACE");

    public CURLCommandCodeContributor() {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement().inFile(StandardPatterns.instanceOf(HTTPFile.class)),
                new CURLCommandCompletionProvider());
    }

    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        int offset = parameters.getOffset();
        Document document = parameters.getEditor().getDocument();
        int lineStartOffset = document.getLineStartOffset(document.getLineNumber(offset));
        String prefix = document.getText(TextRange.create(lineStartOffset, offset));
        int lastSpace = prefix.lastIndexOf(' ') == -1 ? 0 : prefix.lastIndexOf(' ') + 1;
        super.fillCompletionVariants(parameters, result.withPrefixMatcher(prefix.substring(lastSpace)));
    }

    static class CURLCommandCompletionProvider extends CompletionProvider<CompletionParameters> {
        @Override
        protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context,
                                      @NotNull CompletionResultSet result) {
            for (String item : completionItems) {
                result.addElement(LookupElementBuilder.create(item)
                        .withPresentableText(item)
                        .bold()
                        .withIcon(HTTPIcons.FILE));
            }
        }
    }
}
