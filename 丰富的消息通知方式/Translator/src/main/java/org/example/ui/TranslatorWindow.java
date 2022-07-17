package org.example.ui;

import com.intellij.openapi.project.ProjectManager;
import com.intellij.ui.GotItTooltip;
import com.intellij.util.textCompletion.TextFieldWithCompletion;
import org.example.listener.TranslatorButtonActionListener;
import org.example.util.TranslatorTextProvider;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TranslatorWindow {
    private JTabbedPane tabbedPane;
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
        // 为上方的 tab 栏目设置监听器
        tabbedPane.addChangeListener(e -> {
            JTabbedPane tab = (JTabbedPane) e.getSource();
            // 选中备忘录 tab 时直接返回
            if (tab.getSelectedIndex() == 0) {
                return;
            }
            new GotItTooltip("got.it.id", "翻译插件", ProjectManager.getInstance().getDefaultProject()).
                    // 为了方便调试，设置为100，改提示会出现 100 次
                            withShowCount(100).
                    // 引导提示内容
                            withHeader("输入文本，点击翻译按钮即可完成翻译").
                    // 引导提示位置设置在翻译按钮的正下方位置
                            show(translateButton, GotItTooltip.BOTTOM_MIDDLE);
        });
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
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
