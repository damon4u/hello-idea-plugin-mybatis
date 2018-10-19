package com.damon4u.plugin.mybatis.reference;

import com.damon4u.plugin.mybatis.dom.MapperBacktrackingUtils;
import com.damon4u.plugin.mybatis.util.JavaUtils;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiType;
import com.intellij.psi.impl.source.PsiClassReferenceType;
import com.intellij.psi.xml.XmlAttributeValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Description:
 *
 * @author damon4u
 * @version 2018-10-19 11:46
 */
public class PsiFiledReferenceSetResolver extends ContextReferenceSetResolver<XmlAttributeValue, PsiField> {

    protected PsiFiledReferenceSetResolver(XmlAttributeValue from) {
        super(from);
    }

    @NotNull
    @Override
    public String getText() {
        return getElement().getValue() != null ? getElement().getValue() : "";
    }

    /**
     * 获取一级属性
     * 对于简单属性"name"，那么就是这个属性
     * 对于包含引用的情况"user.name"，那么先找到一级属性user
     * @param firstText
     * @return
     */
    @NotNull
    @Override
    public Optional<PsiField> getStartElement(@Nullable String firstText) {
        Optional<PsiClass> clazz = MapperBacktrackingUtils.getPropertyClazz(getElement());
        return clazz.flatMap(psiClass -> JavaUtils.findSettablePsiField(psiClass, firstText));
    }

    @NotNull
    @Override
    public Optional<PsiField> resolve(@NotNull PsiField current, @NotNull String text) {
        PsiType type = current.getType();
        // 引用类型，而且不包含可变参数
        if (type instanceof PsiClassReferenceType && !((PsiClassReferenceType) type).hasParameters()) {
            PsiClass clazz = ((PsiClassReferenceType) type).resolve();
            if (null != clazz) {
                return JavaUtils.findSettablePsiField(clazz, text);
            }
        }
        return Optional.empty();
    }
}
