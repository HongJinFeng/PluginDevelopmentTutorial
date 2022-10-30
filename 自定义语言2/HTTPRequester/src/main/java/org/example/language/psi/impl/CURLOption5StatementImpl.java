// This is a generated file. Not intended for manual editing.
package org.example.language.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.example.language.CURLTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import org.example.language.psi.*;

public class CURLOption5StatementImpl extends ASTWrapperPsiElement implements CURLOption5Statement {

  public CURLOption5StatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CURLVisitor visitor) {
    visitor.visitOption5Statement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CURLVisitor) accept((CURLVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public CURLOption5 getOption5() {
    return findNotNullChildByClass(CURLOption5.class);
  }

  @Override
  @NotNull
  public PsiElement getMethod() {
    return findNotNullChildByType(METHOD);
  }

}
