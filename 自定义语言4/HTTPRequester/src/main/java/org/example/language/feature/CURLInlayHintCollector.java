package org.example.language.feature;

import com.intellij.codeInsight.hints.BlockConstraints;
import com.intellij.codeInsight.hints.FactoryInlayHintsCollector;
import com.intellij.codeInsight.hints.InlayHintsSink;
import com.intellij.codeInsight.hints.presentation.InlayPresentation;
import com.intellij.codeInsight.hints.presentation.RecursivelyUpdatingRootPresentation;
import com.intellij.codeInsight.hints.presentation.RootInlayPresentation;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.example.language.HTTPFile;
import org.example.language.psi.CURLCommand;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class CURLInlayHintCollector extends FactoryInlayHintsCollector {

    public CURLInlayHintCollector(@NotNull Editor editor) {
        super(editor);
    }

    @Override
    public boolean collect(@NotNull PsiElement psiElement, @NotNull Editor editor, @NotNull InlayHintsSink inlayHintsSink) {
        // 只处理我们定义的 HTTP 文件，如果要在其他文件内如 Java 文件，只需要对应的修改判断逻辑
        if (!(psiElement instanceof HTTPFile)) {
            return false;
        }
        HTTPFile file = (HTTPFile) psiElement;
        // 遍历整个 HTTP 文件，获取到所有的 CURL 命令
        List<PsiElement> elements = collectCURLCommandElements(file);
        // 遍历所有的 CURL 命令
        elements.forEach(elem -> {
            CURLCommand command = (CURLCommand) elem;
            // 如果是 http ，提示用户使用 https
            if (command.getUrl().getText().startsWith("http:")) {
                // 添加编辑器内联提示信息
                addHint(editor, elem, inlayHintsSink);
            }
        });
        return true;
    }

    private List<PsiElement> collectCURLCommandElements(HTTPFile file) {
        List<PsiElement> elements = new LinkedList<>();
        // 遍历整个 HTTP 文件，获取到所有的 CURL 命令
        PsiTreeUtil.processElements(file, element -> {
            if (element instanceof CURLCommand) {
                elements.add(element);
            }
            return true;
        });
        return elements;
    }

    private void addHint(Editor editor, PsiElement element, InlayHintsSink sink) {
        InlayPresentation text = getFactory().text("recommend use https");
        InlayPresentation roundText = getFactory().roundWithBackground(text);
        int line = editor.getDocument().getLineNumber(element.getTextOffset());
        // 块提示，根据行号显示在某一行上
        RootInlayPresentation blockText = new RecursivelyUpdatingRootPresentation(roundText);
        sink.addBlockElement(line, true, blockText, new BlockConstraints(false, 0));

        // 行内提示，根据 offset 决定显示位置
        CURLCommand command = (CURLCommand) element;
        text = getFactory().text("use https");
        roundText = getFactory().roundWithBackground(text);
        sink.addInlineElement(command.getUrl().getTextOffset(), true, roundText, false);

    }
}
