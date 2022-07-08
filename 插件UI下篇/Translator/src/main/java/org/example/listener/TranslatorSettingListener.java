package org.example.listener;

import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import org.example.extension.TranslatorSetting;
import org.jetbrains.annotations.NotNull;


public class TranslatorSettingListener implements ProjectManagerListener {

    @Override
    public void projectOpened(@NotNull Project project) {
        if (!TranslatorSetting.getInstance().appID.equals("请输入appID") && !TranslatorSetting.getInstance().securityKey.equals("请输入securityKey")) {
            return;
        }
        Notification notification = new Notification("Print", "小天才翻译器", "请配置翻译 API 的 AppID 与密钥", NotificationType.INFORMATION);
        notification.addAction(new OpenTranslatorSettingAction());
        Notifications.Bus.notify(notification, project);
    }

    static class OpenTranslatorSettingAction extends NotificationAction {

        OpenTranslatorSettingAction() {
            super("打开翻译插件配置界面");
        }

        @Override
        public void actionPerformed(@NotNull AnActionEvent e, @NotNull Notification notification) {
            ShowSettingsUtil.getInstance().showSettingsDialog(e.getProject(), "Translator");
            notification.expire();
        }
    }

}
