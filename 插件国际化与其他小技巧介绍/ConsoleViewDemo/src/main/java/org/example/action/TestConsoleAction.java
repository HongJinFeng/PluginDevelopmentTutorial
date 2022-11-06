package org.example.action;

import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.example.toolwindow.ConsoleOutputToolWindow;
import org.jetbrains.annotations.NotNull;

public class TestConsoleAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ConsoleOutputToolWindow.getConsoleView(e.getProject()).print("测试控制台输出", ConsoleViewContentType.NORMAL_OUTPUT);
    }
}
