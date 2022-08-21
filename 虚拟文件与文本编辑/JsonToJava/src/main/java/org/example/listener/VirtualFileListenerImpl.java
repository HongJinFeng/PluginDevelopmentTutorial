package org.example.listener;

import com.alibaba.fastjson.JSON;
import com.intellij.openapi.vfs.*;
import io.github.sharelison.jsontojava.JsonToJava;
import io.github.sharelison.jsontojava.converter.JsonClassResult;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VirtualFileListenerImpl implements VirtualFileListener {
    @Override
    public void contentsChanged(@NotNull VirtualFileEvent event) {
        // 获取事件对应的虚拟文件
        VirtualFile file = event.getFile();
        // text 字段用于保存文件的内容
        String text = "";
        try {
            // 获取当前虚拟文件的内容
            text = VfsUtil.loadText(file);
            // 文件内容为空，则不可能为 json
            if (text.equals("")) {
                return;
            }
            // 如果 json 解析成功，则不会抛出异常
            JSON.parse(text);
        } catch (Exception ignored) {
            // json 解析失败，抛出异常直接 return
            return;
        }
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