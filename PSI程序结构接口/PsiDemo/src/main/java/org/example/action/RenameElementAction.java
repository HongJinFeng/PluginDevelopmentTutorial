package org.example.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.PsiElement;
import com.intellij.refactoring.RefactoringFactory;
import org.jetbrains.annotations.NotNull;

public class RenameElementAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PsiElement psiElement = e.getData(CommonDataKeys.PSI_ELEMENT);
        RefactoringFactory.getInstance(e.getProject()).createRename(psiElement, "newName", false, false).run();
    }
}
