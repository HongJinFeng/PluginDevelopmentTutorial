package org.example.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.example.config.PluginConfig;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.ResourceBundle;

public class NotifyAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Notifications.Bus.notify(new Notification("Print", getLocalMessage(PluginConfig.langConfig, "translate"), NotificationType.INFORMATION));
    }

    public static String getLocalMessage(String langCode, String key) {
        return ResourceBundle.getBundle("i18n.plugin", Locale.forLanguageTag("zh")).getString(key);
    }
}
