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

public class CURLOptionsImpl extends ASTWrapperPsiElement implements CURLOptions {

  public CURLOptionsImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CURLVisitor visitor) {
    visitor.visitOptions(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CURLVisitor) accept((CURLVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CURLOption1Statement getOption1Statement() {
    return findChildByClass(CURLOption1Statement.class);
  }

  @Override
  @Nullable
  public CURLOption2Statement getOption2Statement() {
    return findChildByClass(CURLOption2Statement.class);
  }

  @Override
  @Nullable
  public CURLOption3Statement getOption3Statement() {
    return findChildByClass(CURLOption3Statement.class);
  }

  @Override
  @Nullable
  public CURLOption4Statement getOption4Statement() {
    return findChildByClass(CURLOption4Statement.class);
  }

  @Override
  @Nullable
  public CURLOption5Statement getOption5Statement() {
    return findChildByClass(CURLOption5Statement.class);
  }

  @Override
  @Nullable
  public CURLOption6Statement getOption6Statement() {
    return findChildByClass(CURLOption6Statement.class);
  }

  @Override
  @Nullable
  public CURLOption7Statement getOption7Statement() {
    return findChildByClass(CURLOption7Statement.class);
  }

}
