package org.example.language.feature;

import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class CURLCommandDocumentProvider implements DocumentationProvider {

    @Override
    public @Nullable String generateHoverDoc(@NotNull PsiElement element, @Nullable PsiElement originalElement) {
        switch (element.getText().toLowerCase()) {
            case "curl":
                return "curl is used in command lines or scripts to transfer data. curl is also used in cars, television sets, routers, printers, audio equipment, mobile phones, tablets, settop boxes, media players and is the Internet transfer engine for thousands of software applications in over ten billion installations.";
            case "-a":
            case "--append":
                return getAppendDoc();
            default:
                return "巴拉巴拉";
        }
    }

    private String getAppendDoc() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <style>\n" +
                "    .article-main { display: inline; float: left; }\n" +
                "    .metadata { border-left: 3px solid #2d8d2d8d; color: #ffaa00ff; font-size: 1em; margin: 0 0 1.5em 0; padding: 5px 15px; }\n" +
                "    pre {\n" +
                "      white-space: pre-wrap;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1><a href=\"https://curl.se/docs/manpage.html\">append</a></h1>\n" +
                "<div class=\"article-main\"> \n" +
                " <p>(FTP SFTP) When used in an upload, this makes curl append to the target file instead of overwriting it.</p>" +
                " <p>If the remote file does not exist, it will be created. Note that this flag is ignored by some SFTP servers (including OpenSSH).</p>\n" +
                " <br>"+
                " <p>Providing --append multiple times has no extra effect. Disable it again with --no-append.</p>" +
                " <br>"+
                " <h2><a href=\"#options\" class=\"anchor-link\"></a>Example:</h2> \n" +
                " <br>"+
                " <p>  curl --upload-file local --append ftp://example.com/\n</p> \n" +
                " <br>"+
                " <p>See also <a href=\"https://curl.se/docs/manpage.html#-r\">-r, --range</a>  and <a href=\"https://curl.se/docs/manpage.html#-C\">-C, --continue-at.</a></p>" +
                "</body>\n" +
                "</html>\n";
    }

    @Override
    public @Nullable PsiElement getCustomDocumentationElement(@NotNull Editor editor, @NotNull PsiFile file, @Nullable PsiElement contextElement, int targetOffset) {
        return contextElement;
    }
}
