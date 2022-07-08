package org.example.listener;

import com.intellij.analysis.problemsView.Problem;
import com.intellij.analysis.problemsView.ProblemsListener;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import org.jetbrains.annotations.NotNull;

public class GlobalProblemListener implements ProblemsListener {

    @Override
    public void problemAppeared(@NotNull Problem problem) {
        Notifications.Bus.notify(new Notification("Print", problem.getDescription(), NotificationType.ERROR), problem.getProvider().getProject());
    }

    @Override
    public void problemDisappeared(@NotNull Problem problem) {
        Notifications.Bus.notify(new Notification("Print", problem.getDescription(), NotificationType.ERROR), problem.getProvider().getProject());
    }

    @Override
    public void problemUpdated(@NotNull Problem problem) {
        Notifications.Bus.notify(new Notification("Print", problem.getDescription(), NotificationType.ERROR), problem.getProvider().getProject());
    }
}
