package org.example.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationAction;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class TopLevelNotification extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Notification notification = new Notification("Print", "顶层通知标题", "顶层通知内容", NotificationType.INFORMATION);
        notification.addAction(new NotificationAction("点我") {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e, @NotNull Notification notification) {
                Notifications.Bus.notify(new Notification("Print", "顶层通知标题", "套娃通知事件", NotificationType.INFORMATION), e.getProject());
            }
        });
        Notifications.Bus.notify(notification, e.getProject());
    }
}
