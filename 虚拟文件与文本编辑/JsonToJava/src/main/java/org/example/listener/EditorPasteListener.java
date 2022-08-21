package org.example.listener;

import com.alibaba.fastjson.JSON;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import io.github.sharelison.jsontojava.JsonToJava;
import io.github.sharelison.jsontojava.converter.JsonClassResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.datatransfer.DataFlavor;
import java.util.List;
import java.util.Objects;

public class EditorPasteListener extends EditorActionHandler {

    @Override
    protected void doExecute(@NotNull Editor editor, @Nullable Caret caret, DataContext dataContext) {
        String text = CopyPasteManager.getInstance().getContents(DataFlavor.stringFlavor);
        try {
            // 文件内容为空，则不可能为 json
            if (Objects.equals(text, "")) {
                return;
            }
            // 如果 json 解析成功，则不会抛出异常
            JSON.parse(text);
        } catch (Exception ignored) {
            // json 解析失败，执行默认的粘贴逻辑
            WriteCommandAction.runWriteCommandAction(ProjectManager.getInstance().getDefaultProject(), () -> {
                editor.getDocument().insertString(editor.getCaretModel().getOffset(), text);
            });
            return;
        }
        // 通过 dataContext 获取当前上下文的虚拟文件对象，DataContext 我们之前也多次使用于获取编辑器对象、Project 对象
        VirtualFile file = dataContext.getData(CommonDataKeys.VIRTUAL_FILE);
        // json 转 java 源码工具
        JsonToJava jsonToJava = new JsonToJava();
        List<JsonClassResult> javaClasses = jsonToJava.jsonToJava(text, getFileName(file), getPackage(file), false);
        for (JsonClassResult item : javaClasses) {
            try {
                // 获取当前文件所在文件夹
                VirtualFile parentFile = file.getParent();
                // 在当前文件夹下创建类文件
                VirtualFile javaClassFile = parentFile.createChildData(null, item.getClassName() + ".java");
                // 设置生成的文件内容
                javaClassFile.setBinaryContent(item.getClassDeclaration().getBytes());
            } catch (Exception ignored) {
            }
        }
    }

    private String getFileName(VirtualFile file) {
        return file.getName().replace("." + file.getFileType().getName().toLowerCase(), "");
    }

    private String getPackage(VirtualFile vf) {
        String path = vf.getParent().getPath();
        return path.substring(path.indexOf("src/main/java") + 14).replace('/', '.');
    }
}
