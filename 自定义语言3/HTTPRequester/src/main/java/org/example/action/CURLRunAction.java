package org.example.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Objects;

public class CURLRunAction extends AnAction {

    private final PsiElement info;

    public CURLRunAction(PsiElement info) {
        this.info = info;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        Document document = editor.getDocument();
        int offset = info.getTextOffset();
        int lineNumber = document.getLineNumber(offset);
        int startOffset = document.getLineStartOffset(lineNumber);
        int endOffset = document.getLineEndOffset(lineNumber);
        // 获取某偏移量范围内的文本
        String text = document.getText(new TextRange(startOffset, endOffset));
        if (Objects.equals(text, "")) {
            return;
        }
        try {
            Process process = Runtime.getRuntime().exec(text);
            InputStreamReader isr = new InputStreamReader(process.getInputStream());
            LineNumberReader lineNumberReader = new LineNumberReader(isr);
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = lineNumberReader.readLine()) != null) {
                output.append(line).append("\n");
            }
            Notifications.Bus.notify(new Notification("Print", "请求结果", output.toString(), NotificationType.INFORMATION));
        } catch (IOException ex) {
            Notifications.Bus.notify(new Notification("Print", "执行失败", ex.getMessage(), NotificationType.ERROR));
        }
    }
}
