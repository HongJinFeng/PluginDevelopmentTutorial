package org.example.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;


public class GetImportPackageAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        StringBuilder result = new StringBuilder();
        // 获取到 PSI 文件
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        // accept 方法用于遍历访问 psi 文件中的元素
        psiFile.accept(new JavaRecursiveElementVisitor() {
            // PsiImportStatement 表示的是 psi 树中的导入包语句，visitImportStatement 方法
            // 用于访问 PsiImportStatement
            @Override
            public void visitImportStatement(PsiImportStatement statement) {
                result.append(statement.getImportReference().getQualifiedName()).append("<br>");
            }
        });
        Notifications.Bus.notify(new Notification("Print", "PSIDemo", result.toString(), NotificationType.INFORMATION), e.getProject());
    }
}
