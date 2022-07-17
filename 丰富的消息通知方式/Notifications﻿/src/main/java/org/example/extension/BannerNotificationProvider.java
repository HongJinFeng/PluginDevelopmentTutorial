package org.example.extension;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.EditorNotificationPanel;
import com.intellij.ui.EditorNotifications;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BannerNotificationProvider extends EditorNotifications.Provider<EditorNotificationPanel> {

    private static final Key<EditorNotificationPanel> KEY = Key.create("banner.demo.notification");

    @Override
    public @NotNull Key<EditorNotificationPanel> getKey() {
        return KEY;
    }

    @Override
    public @Nullable EditorNotificationPanel createNotificationPanel(@NotNull VirtualFile file, @NotNull FileEditor fileEditor, @NotNull Project project) {
        // 前面用于判断是否需要展示横幅提示，例如横幅提示用户配置 SDK ，若是用户已经配置，返回 null
        // 此处我们在 java 文件中显示横幅，打开其他类型的文件不显示
        if (!file.getFileType().getName().equalsIgnoreCase("java")) {
            return null;
        }
        // IDE 中的横幅提示原生实现界面
        EditorNotificationPanel banner = new EditorNotificationPanel();
        banner.setText("Banner 提示，请配置项目 SDK。");
        banner.setToolTipText("需要配置项目 SDK 。");
        banner.createActionLabel("去配置", new Runnable() {
            @Override
            public void run() {
                // 打开配置界面等逻辑，ShowSettingsUtil 工具在我们前面的翻译插件中使用过。
                ShowSettingsUtil.getInstance().showSettingsDialog(project, "Editor");
            }
        });
        return banner;
    }
}
