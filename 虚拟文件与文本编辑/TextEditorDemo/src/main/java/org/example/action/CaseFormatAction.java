package org.example.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.util.TextRange;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import com.google.common.base.CaseFormat;

public class CaseFormatAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = e.getData(CommonDataKeys.EDITOR);

        /*CaretModel caretModel = editor.getCaretModel();
        // 获取主插入符对象
        Caret primaryCaret = caretModel.getPrimaryCaret();
        // 获取到当前插入符的逻辑位置
        LogicalPosition logicalPosition = primaryCaret.getLogicalPosition();
        VisualPosition visualPosition = primaryCaret.getVisualPosition();
        // 虚拟位置
        int visualLine = visualPosition.getLine();
        int visualColumn = visualPosition.getColumn();
        // 逻辑位置
        int LogicalColumn = logicalPosition.column;
        int LogicalLine = logicalPosition.line;
        Document document = editor.getDocument();
        // 获取当前插入符号的文档偏移量
        int offset = document.getLineNumber(caretModel.getOffset());
        // 获取当前偏移量行号
        int lineNumber = document.getLineNumber(offset);
        // 获取当前行的起始、结尾偏移量，通过这两个位置获取到当前行中的文本。
        int startOffset = document.getLineStartOffset(lineNumber);
        int endOffset = document.getLineEndOffset(lineNumber);
        String text = document.getText(new TextRange(startOffset, endOffset));*/

        if (editor == null || StringUtils.isBlank(editor.getSelectionModel().getSelectedText())) {
            return;
        }
        String text = editor.getSelectionModel().getSelectedText();
        String result;
        if (text.contains("_")) {
            result = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, text);
        } else if (Character.isUpperCase(text.charAt(0))) {
            result = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, text);
        } else {
            result = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, text);
        }
        Document document = editor.getDocument();
        Caret currentCaret = editor.getCaretModel().getPrimaryCaret();
        int start = currentCaret.getSelectionStart();
        int end = currentCaret.getSelectionEnd();
        document.replaceString(start, end, result);
    }


}
