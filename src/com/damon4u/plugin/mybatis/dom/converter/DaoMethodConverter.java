package com.damon4u.plugin.mybatis.dom.converter;

import com.damon4u.plugin.mybatis.dom.model.Mapper;
import com.damon4u.plugin.mybatis.util.JavaUtils;
import com.damon4u.plugin.mybatis.util.MapperUtils;
import com.intellij.psi.PsiMethod;
import com.intellij.util.xml.ConvertContext;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

/**
 * Description:
 *
 * @author damon4u
 * @version 2018-10-26 11:22
 */
public class DaoMethodConverter extends ConverterAdaptor<PsiMethod> {

    @Nullable
    @Override
    public PsiMethod fromString(@Nullable @NonNls String id, ConvertContext context) {
        Mapper mapper = MapperUtils.getMapper(context.getInvocationElement());
        return JavaUtils.findMethod(context.getProject(), MapperUtils.getNamespace(mapper), id).orElse(null);
    }
}
