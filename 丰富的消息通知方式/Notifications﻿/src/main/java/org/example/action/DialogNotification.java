package org.example.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class DialogNotification extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        new MyDialog(e.getProject()).show();
    }

    static class MyDialog extends DialogWrapper {

        protected MyDialog(@Nullable Project project) {
            super(project);
            init();
        }

        @Override
        protected @Nullable JComponent createCenterPanel() {
            return new JLabel("对话框提示");
        }
    }

}
