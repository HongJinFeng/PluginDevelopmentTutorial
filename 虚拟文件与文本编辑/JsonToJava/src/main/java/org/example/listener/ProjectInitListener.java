package org.example.listener;

import com.intellij.openapi.actionSystem.IdeActions;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.vfs.LocalFileSystem;
import org.jetbrains.annotations.NotNull;

public class ProjectInitListener implements ProjectManagerListener {
    @Override
    public void projectOpened(@NotNull Project project) {
        // 虚拟文件监听器临时注释，防止于编辑器监听器冲突
        //LocalFileSystem.getInstance().addVirtualFileListener(new VirtualFileListenerImpl());
        EditorActionManager actionManager = EditorActionManager.getInstance();
        actionManager.setActionHandler(IdeActions.ACTION_EDITOR_PASTE, new EditorPasteListener());
    }
}
