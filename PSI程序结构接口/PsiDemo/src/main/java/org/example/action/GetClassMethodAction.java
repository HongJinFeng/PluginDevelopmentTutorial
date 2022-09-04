package org.example.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;

public class GetClassMethodAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        StringBuilder result = new StringBuilder();
        // 获取到 PSI 文件
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        // accept 方法用于遍历访问 psi 文件中的元素
        psiFile.accept(new JavaRecursiveElementVisitor() {
            // PsiMethod 表示的是 psi 树中的方法，visitMethod 方法
            // 用于访问 PsiMethod
            @Override
            public void visitMethod(PsiMethod method) {
                if (method.getName().startsWith("get")){
                    result.append(method.getName()).append("<br>");
                }
            }
        });
        Notifications.Bus.notify(new Notification("Print", "PSIDemo", result.toString(), NotificationType.INFORMATION), e.getProject());
    }
}
