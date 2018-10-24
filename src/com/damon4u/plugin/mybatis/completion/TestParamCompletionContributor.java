package com.damon4u.plugin.mybatis.completion;

import com.damon4u.plugin.mybatis.annotation.Annotation;
import com.damon4u.plugin.mybatis.dom.model.IdDomElement;
import com.damon4u.plugin.mybatis.util.JavaUtils;
import com.damon4u.plugin.mybatis.util.MapperUtils;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.completion.PrioritizedLookupElement;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.patterns.XmlPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.util.PlatformIcons;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Description:
 *
 * @author damon4u
 * @version 2018-10-24 20:01
 */
public class TestParamCompletionContributor extends CompletionContributor {

    private static final double PRIORITY = 400.0;

    public TestParamCompletionContributor() {
        extend(CompletionType.BASIC,
                XmlPatterns.psiElement().inside(XmlPatterns.xmlAttribute().inside(XmlPatterns.xmlAttribute().withName("test"))),
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters,
                                                  ProcessingContext context,
                                                  @NotNull CompletionResultSet result) {
                        final PsiElement position = parameters.getPosition();
                        addCompletionForPsiParameter(position.getProject(), result, MapperUtils.findParentIdDomElement(position).orElse(null));
                    }
                });
    }

    static void addCompletionForPsiParameter(@NotNull final Project project,
                                             @NotNull final CompletionResultSet completionResultSet,
                                             @Nullable final IdDomElement element) {
        if (element == null) {
            return;
        }

        final PsiMethod method = JavaUtils.findMethod(project, element).orElse(null);

        if (method == null) {
            return;
        }

        final PsiParameter[] parameters = method.getParameterList().getParameters();

        if (parameters.length == 1) {
            final PsiParameter parameter = parameters[0];
            completionResultSet.addElement(buildLookupElementWithIcon(parameter.getName(), parameter.getType().getPresentableText()));
        } else {
            for (PsiParameter parameter : parameters) {
                final Optional<String> annotationValueText = JavaUtils.getAnnotationValueText(parameter, Annotation.PARAM);
                completionResultSet.addElement(buildLookupElementWithIcon(annotationValueText.orElseGet(parameter::getName), parameter.getType().getPresentableText()));
            }
        }
        
    }

    private static LookupElement buildLookupElementWithIcon(final String parameterName, 
                                                            final String parameterType) {
        return PrioritizedLookupElement.withPriority(
                LookupElementBuilder.create(parameterName)
                        .withTypeText(parameterType)
                        .withIcon(PlatformIcons.PARAMETER_ICON),
                PRIORITY);
    }
    
}
