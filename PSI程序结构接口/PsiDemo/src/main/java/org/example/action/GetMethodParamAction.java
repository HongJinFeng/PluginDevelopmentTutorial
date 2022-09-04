package org.example.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.refactoring.RefactoringFactory;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class GetMethodParamAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PsiElement psiElement = e.getData(CommonDataKeys.PSI_ELEMENT);
        if (!(psiElement instanceof PsiMethod)) {
            return;
        }
        StringBuilder result = new StringBuilder();
        Project project = e.getProject();
        // 请确保当前插入符号位于方法名上
        PsiMethod psiMethod = (PsiMethod) psiElement;
        // 遍历方法参数列表
        for (PsiParameter parameter : psiMethod.getParameterList().getParameters()) {
            // 获取到参数的类型，通过类型获取到参数的类路径
            String canonicalText = parameter.getType().getCanonicalText();
            // JavaPsiFacade.findClass 方法可通过类路径获取到对应的 PsiClass 对象，非常有用！
            PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(canonicalText, GlobalSearchScope.allScope(project));
            // 遍历对象中的所有字段
            for (PsiField field : psiClass.getAllFields()) {
                result.append(field.getName()).append("<br>");
            }
        }
        Notifications.Bus.notify(new Notification("Print", "PSIDemo", result.toString(), NotificationType.INFORMATION), project);
    }
}
