package org.example.listener;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;

public class TranslatorActionListenerA implements TranslateListener {

    @Override
    public void beforeTranslated(Project project) {
        Notifications.Bus.notify(new Notification("Print", "小天才翻译机", "第一个监听器：beforeTranslated", NotificationType.INFORMATION), project);
    }

    @Override
    public void afterTranslated(Project project) {
        Notifications.Bus.notify(new Notification("Print", "小天才翻译机", "第一个监听器：afterTranslated", NotificationType.INFORMATION), project);
    }
}
