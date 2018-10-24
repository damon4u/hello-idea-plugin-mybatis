package com.damon4u.plugin.mybatis.reference;

import com.damon4u.plugin.mybatis.annotation.Annotation;
import com.damon4u.plugin.mybatis.dom.model.IdDomElement;
import com.damon4u.plugin.mybatis.util.JavaUtils;
import com.damon4u.plugin.mybatis.util.MapperUtils;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.xml.XmlToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Description:
 *
 * @author damon4u
 * @version 2018-10-23 21:05
 */
public class SqlParamReference extends PsiReferenceBase<XmlToken> {
    
    private String paramName;

    public SqlParamReference(@NotNull XmlToken xmlToken, TextRange textRange, @NotNull String paramName) {
        super(xmlToken, textRange);
        this.paramName = paramName;
    }
    
    @Nullable
    @Override
    public PsiElement resolve() {
        XmlToken element = getElement();
        Project project = element.getProject();
        IdDomElement domElement = MapperUtils.findParentIdDomElement(element).orElse(null);
        if (domElement == null) {
            return null;
        }
        final PsiMethod method = JavaUtils.findMethod(project, domElement).orElse(null);
        if (method == null) {
            return null;
        }

        final PsiParameter[] parameters = method.getParameterList().getParameters();

        if (parameters.length == 1) {
            PsiParameter parameter = parameters[0];
            if (paramName.equals(parameter.getName())) {
                return parameter;
            } else {
                return null;
            }
        } else {
            for (final PsiParameter parameter : parameters) {
                final Optional<String> value = JavaUtils.getAnnotationValueText(parameter, Annotation.PARAM);
                if (value.isPresent() && paramName.equals(value.get())) {
                    return parameter;
                }
            }
        }
        return null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];
    }
}
