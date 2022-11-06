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

public class CURLCommandImpl extends ASTWrapperPsiElement implements CURLCommand {

  public CURLCommandImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CURLVisitor visitor) {
    visitor.visitCommand(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CURLVisitor) accept((CURLVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<CURLOptions> getOptionsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CURLOptions.class);
  }

  @Override
  @NotNull
  public PsiElement getCurl() {
    return findNotNullChildByType(CURL);
  }

  @Override
  @NotNull
  public PsiElement getUrl() {
    return findNotNullChildByType(URL);
  }

}
