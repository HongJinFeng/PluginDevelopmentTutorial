package org.example.ui;

import com.intellij.openapi.project.ProjectManager;
import com.intellij.util.textCompletion.TextFieldWithCompletion;
import org.example.listener.TranslatorButtonActionListener;
import org.example.util.TranslatorTextProvider;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TranslatorWindow {
    private JTabbedPane tabbedPane1;
    private JPanel mainPanel;
    private JPanel translatorPanel;
    private JPanel notePanel;
    private JTable noteTable;
    private JComboBox<String> comboBox1;
    private TextFieldWithCompletion originalTextArea;
    private JComboBox<String> comboBox2;
    private JButton translateButton;
    private JTextArea translateTextArea;
    private JScrollPane scrollPane1;
    private JScrollPane scrollPane2;

    public TranslatorWindow() {
        // 下拉框添加下拉选项
        comboBox1.addItem("英文");
        comboBox1.addItem("中文");
        comboBox2.addItem("中文");
        comboBox2.addItem("英文");
        // 备忘录表格属性设置
        String[] header = {"原文", "译文"};
        DefaultTableModel tableModel = new DefaultTableModel(null, header);
        noteTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        noteTable.setModel(tableModel);
        // 添加按钮监听器
        translateButton.addActionListener(new TranslatorButtonActionListener(this));

        originalTextArea.setEnabled(true);
    }

    public JTabbedPane getTabbedPane1() {
        return tabbedPane1;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getTranslatorPanel() {
        return translatorPanel;
    }

    public JPanel getNotePanel() {
        return notePanel;
    }

    public JTable getNoteTable() {
        return noteTable;
    }

    public JComboBox<String> getComboBox1() {
        return comboBox1;
    }

    public TextFieldWithCompletion getOriginalTextArea() {
        return originalTextArea;
    }

    public JComboBox<String> getComboBox2() {
        return comboBox2;
    }

    public JButton getTranslateButton() {
        return translateButton;
    }

    public JTextArea getTranslateTextArea() {
        return translateTextArea;
    }

    private void createUIComponents() {
        originalTextArea = new TextFieldWithCompletion(ProjectManager.getInstance().getDefaultProject(),
                new TranslatorTextProvider(), "", true, true, true, true);

    }
}
