package org.example.language.feature;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.execution.lineMarker.RunLineMarkerProvider;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiElement;
import org.example.action.CURLRunAction;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class CURLLineMarkerProvider extends RunLineMarkerProvider {

    @Override
    public LineMarkerInfo<?> getLineMarkerInfo(@NotNull PsiElement element) {
        if (!(element.getText().equalsIgnoreCase("curl"))) {
            return null;
        }
        return new RunLineMarkerInfo(element, AllIcons.Actions.Execute);
    }

    static class RunLineMarkerInfo extends LineMarkerInfo<PsiElement> {

        RunLineMarkerInfo(PsiElement element, Icon icon) {
            super(element, element.getTextRange(), icon, psiElement -> "Run", null, GutterIconRenderer.Alignment.CENTER, () -> "run");
        }

        @Override
        public GutterIconRenderer createGutterRenderer() {
            return new LineMarkerGutterIconRenderer<>(this) {
                @Override
                public AnAction getClickAction() {
                    return new CURLRunAction(this.getLineMarkerInfo().
                            getElement());
                }
            };
        }
    }
}
