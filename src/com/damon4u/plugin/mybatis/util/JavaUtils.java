package com.damon4u.plugin.mybatis.util;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.util.PropertyUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Description:
 *
 * @author damon4u
 * @version 2018-10-19 14:44
 */
public final class JavaUtils {

    private JavaUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 从类中查找成员变量
     *
     * @param clazz        类
     * @param propertyName 属性名称
     * @return
     */
    @NotNull
    public static Optional<PsiField> findSettablePsiField(@NotNull final PsiClass clazz,
                                                          @Nullable final String propertyName) {
        final PsiField field = PropertyUtil.findPropertyField(clazz, propertyName, false);
        return Optional.ofNullable(field);
    }

    @NotNull
    public static PsiField[] findSettablePsiFields(final @NotNull PsiClass clazz) {
        final PsiField[] allFields = clazz.getAllFields();
        final List<PsiField> settableFields = new ArrayList<>(allFields.length);
        for (final PsiField field : allFields) {
            final PsiModifierList modifierList = field.getModifierList();
            if (modifierList != null && 
                    (modifierList.hasModifierProperty(PsiModifier.STATIC) 
                            || modifierList.hasModifierProperty(PsiModifier.FINAL))) {
                continue;
            }
            settableFields.add(field);
        }
        return settableFields.toArray(new PsiField[0]);
    }
}
