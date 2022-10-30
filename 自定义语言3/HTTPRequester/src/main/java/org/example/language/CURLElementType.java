package org.example.language;

import com.intellij.psi.tree.IElementType;

class CURLElementType extends IElementType {

    public CURLElementType(String debugName) {
        super(debugName, HTTPLanguage.INSTANCE);
    }
}