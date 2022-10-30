package org.example.language;

import com.intellij.lexer.FlexAdapter;

public class CURLLexerAdapter extends FlexAdapter {

    public CURLLexerAdapter() {
        super(new _CURLLexer(null));
    }

}