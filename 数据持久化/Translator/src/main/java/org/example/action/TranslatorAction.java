package org.example.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import org.example.extension.TranslatorCache;
import org.example.extension.TranslatorToolsWindow;
import org.example.util.TranslatorUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class TranslatorAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (TranslatorUtils.appid == null || TranslatorUtils.securityKey == null) {
            Notifications.Bus.notify(new Notification("Translator", "小天才翻译机", "请先设置appID，securityKey", NotificationType.ERROR), e.getProject());
            return;
        }
        String text = editor.getSelectionModel().getSelectedText();
        // 获取到持久化数据对象
        Map<String, String> transCache = TranslatorCache.getInstance(e.getProject()).transCache;
        String transResult;
        // 缓存存在查询缓存，不存在通过 API 查询
        if (transCache.containsKey(text)) {
            transResult = transCache.get(text);
        } else {
            transResult = TranslatorUtils.getTransResult(text, "auto", "zh");
            transCache.put(text, transResult);
        }
        TranslatorToolsWindow.addNote(text, transResult);
        Notifications.Bus.notify(new Notification("Translator", "小天才翻译机", transResult, NotificationType.INFORMATION), e.getProject());
    }
}
