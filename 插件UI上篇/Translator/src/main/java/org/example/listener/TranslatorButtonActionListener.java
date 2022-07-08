package org.example.listener;

import org.example.ui.TranslatorWindow;
import org.example.util.TranslatorUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class TranslatorButtonActionListener extends AbstractAction {

    private final TranslatorWindow window;

    private final Map<String, String> langMap;

    public TranslatorButtonActionListener(TranslatorWindow window) {
        this.window = window;
        langMap = new HashMap<>();
        langMap.put("中文", "zh");
        langMap.put("英文", "en");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 获取原语言文本、原语言、和目标翻译语言
        String originalText = window.getOriginalTextArea().getText();
        String fromLang = langMap.get((String) window.getComboBox1().getSelectedItem());
        String toLang = langMap.get((String) window.getComboBox2().getSelectedItem());
        // 翻译后，将文本设置到翻译结果文本输入框
        String result = TranslatorUtils.getTransResult(originalText, fromLang, toLang);
        window.getTranslateTextArea().setText(result);
    }
}
