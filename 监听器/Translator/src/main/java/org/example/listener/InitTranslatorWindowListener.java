package org.example.listener;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import org.example.extension.TranslatorCache;
import org.example.extension.TranslatorToolsWindow;
import org.jetbrains.annotations.NotNull;

public class InitTranslatorWindowListener implements ToolWindowManagerListener {

    private final Project project;

    public InitTranslatorWindowListener(Project project) {
        this.project = project;
    }

    // 打开插件的窗口时，加载缓存的翻译结果到表格中，这里每次打开一次翻译窗口就会加载一次，大家可以思考一下，如何解决（例如内存中维护一个静态变量记录一下）。
    @Override
    public void toolWindowShown(@NotNull ToolWindow toolWindow) {
        if (!toolWindow.getId().equals("Translator")) {
            return;
        }
        TranslatorCache.getInstance(project).transCache.forEach(TranslatorToolsWindow::addNote);
    }
}
