package org.example.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

public class GetRestfulAPIMethodAction extends AnAction {

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
                // method.getAnnotations() 获取到方法上的所有注解
                for (PsiAnnotation psiAnnotation : method.getAnnotations()) {
                    // 获取到注解的路径，用于判断当前方法是否为 API 接口
                    String annotationQualifiedName = psiAnnotation.getQualifiedName();
                    if (annotationQualifiedName.startsWith("org.springframework.web.bind.annotation") &&
                            annotationQualifiedName.endsWith("Mapping")) {
                        result.append(method.getName()).append("<br>");
                    }
                }
            }
        });
        Notifications.Bus.notify(new Notification("Print", "PSIDemo", result.toString(), NotificationType.INFORMATION), e.getProject());
    }
}
