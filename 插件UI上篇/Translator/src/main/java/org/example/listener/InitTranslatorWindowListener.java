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

    @Override
    public void toolWindowRegistered(@NotNull String id) {
        if (!id.equals("Translator")) {
            return;
        }
        TranslatorCache.getInstance(project).transCache.forEach(TranslatorToolsWindow::addNote);
    }
}
