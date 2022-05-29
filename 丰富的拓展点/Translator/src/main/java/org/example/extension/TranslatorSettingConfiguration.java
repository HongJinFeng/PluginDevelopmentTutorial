package org.example.extension;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.ui.JBColor;
import org.example.util.TranslatorUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class TranslatorSettingConfiguration implements Configurable {

    private final JComponent component;
    private final JTextField appID;
    private final JTextField securityKey;
    private final static String appIDHint = "请输入appID";
    private final static String securityKeyHint = "请输入securityKey";

    public TranslatorSettingConfiguration() {
        this.component = new JPanel();
        this.component.setSize(200, 20);
        this.component.setLayout(new GridLayout(15, 1));

        // 创建appID、securityKey文本框
        this.appID = new JTextField();
        this.securityKey = new JTextField();

        //设置输入框提示语
        this.appID.setText(appIDHint);
        this.appID.setForeground(JBColor.GRAY);
        this.appID.addFocusListener(new TextFieldListener(this.appID, appIDHint));

        this.securityKey.setText(securityKeyHint);
        this.securityKey.setForeground(JBColor.GRAY);
        this.securityKey.addFocusListener(new TextFieldListener(this.securityKey, securityKeyHint));

        this.component.add(this.appID);
        this.component.add(this.securityKey);
    }

    @Override
    public String getDisplayName() {
        return "Translator";
    }

    @Override
    public @Nullable JComponent createComponent() {
        return component;
    }

    @Override
    public boolean isModified() {
        return !appID.getText().equals(appIDHint) || !securityKey.getText().equals(securityKeyHint);
    }

    @Override
    public void apply() throws ConfigurationException {
        TranslatorUtils.appid = appID.getText();
        TranslatorUtils.securityKey = securityKey.getText();
    }

    static class TextFieldListener implements FocusListener {

        private final String defaultHint;
        private final JTextField textField;

        public TextFieldListener(JTextField textField, String defaultHint) {
            this.defaultHint = defaultHint;
            this.textField = textField;
        }

        @Override
        public void focusGained(FocusEvent e) {
            // 清空提示语，设置为黑色字体
            if (textField.getText().equals(defaultHint)) {
                textField.setText("");
                textField.setForeground(JBColor.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            // 如果内容为空，设置提示语
            if (textField.getText().equals("")) {
                textField.setText(defaultHint);
                textField.setForeground(JBColor.GRAY);
            }
        }
    }
}
