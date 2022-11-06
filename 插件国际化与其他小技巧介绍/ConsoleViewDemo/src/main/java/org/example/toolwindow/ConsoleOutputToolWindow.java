package org.example.toolwindow;

import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ConsoleOutputToolWindow implements ToolWindowFactory {

    private static final Map<Project, ConsoleView> consoleViews = new HashMap<>();

    public static ToolWindow toolWindow;

    public static final String ID = "My Console";

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        if (consoleViews.get(project) == null) {
            createToolWindow(project, toolWindow);
        }
        ConsoleOutputToolWindow.toolWindow = toolWindow;
    }

    public static ConsoleView getConsoleView(Project project) {
        // 创建过了就不再重复创建了
        if (consoleViews.get(project) == null) {
            ToolWindow toolWindow = getToolWindow(project);
            createToolWindow(project, toolWindow);
        }
        return consoleViews.get(project);
    }

    private static void createToolWindow(Project project, ToolWindow toolWindow) {
        ConsoleView consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
        consoleViews.put(project, consoleView);
        Content content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), "My Console Output", false);
        toolWindow.getContentManager().addContent(content);
        content.getComponent().setVisible(true);
        content.setCloseable(true);
        toolWindow.getContentManager().addContent(content);
    }

    public static ToolWindow getToolWindow(@NotNull Project project) {
        return ToolWindowManager.getInstance(project).getToolWindow(ID);
    }
}
