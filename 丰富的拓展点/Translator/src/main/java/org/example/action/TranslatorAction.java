package org.example.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import org.example.util.TranslatorUtils;
import org.jetbrains.annotations.NotNull;

public class TranslatorAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (TranslatorUtils.appid == null || TranslatorUtils.securityKey == null) {
            Notifications.Bus.notify(new Notification("Print", "小天才翻译机", "请先设置appID，securityKey", NotificationType.ERROR), e.getProject());
            return;
        }
        String text = editor.getSelectionModel().getSelectedText();
        String transResult = TranslatorUtils.getTransResult(text, "auto", "zh");
        Notifications.Bus.notify(new Notification("Print", "小天才翻译机", transResult, NotificationType.INFORMATION), e.getProject());
    }
}
