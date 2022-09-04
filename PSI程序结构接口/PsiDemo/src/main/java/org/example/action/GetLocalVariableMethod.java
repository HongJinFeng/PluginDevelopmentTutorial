package org.example.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

public class GetLocalVariableMethod extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PsiElement psiElement = e.getData(CommonDataKeys.PSI_ELEMENT);
        PsiElement parent = PsiTreeUtil.getParentOfType(psiElement, PsiMethod.class);;
        if (parent != null) {
            Notifications.Bus.notify(new Notification("Print", "PSIDemo", ((PsiMethod) parent).getName(), NotificationType.INFORMATION), e.getProject());
        }
    }
}
