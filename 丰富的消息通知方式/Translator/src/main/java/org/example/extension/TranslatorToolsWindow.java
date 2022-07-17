package org.example.extension;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.example.ui.TranslatorWindow;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class TranslatorToolsWindow implements ToolWindowFactory {

    private static JTable table;
    public static TranslatorWindow translatorWindow;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // ContentFactory 在 IntelliJ 平台 SDK 中负责UI界面的管理
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        // 创建我们的工具栏界面
        // TranslatorNote note = new TranslatorNote();
        // table = note.getTable();
        TranslatorWindow translatorWindow = new TranslatorWindow();
        TranslatorToolsWindow.translatorWindow = translatorWindow;
        table = translatorWindow.getNoteTable();
        // 在界面工厂中创建翻译插件的界面
        Content content = contentFactory.createContent(translatorWindow.getMainPanel(), "", false);
        // 将被界面工厂代理后创建的content，添加到工具栏窗口管理器中
        toolWindow.getContentManager().addContent(content);
    }

    static class TranslatorNote {

        private final JScrollPane notePanel;
        private final JTable table;

        public TranslatorNote() {
            String[] header = {"原文", "译文"};
            DefaultTableModel tableModel = new DefaultTableModel(null, header);
            this.table = new JTable();
            this.table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
            this.table.setModel(tableModel);
            this.notePanel = new JBScrollPane(table);
            this.notePanel.setSize(200, 800);
        }

        public JScrollPane getNotePanel() {
            return notePanel;
        }

        public JTable getTable() {
            return table;
        }

    }

    public static void addNote(String from, String to) {
        if (table == null) {
            return;
        }
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.addRow(new Object[]{from, to});
    }

}
