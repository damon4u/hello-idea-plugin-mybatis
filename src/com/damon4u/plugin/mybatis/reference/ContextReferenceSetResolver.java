package com.damon4u.plugin.mybatis.reference;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.ReferenceSetBase;
import org.fest.util.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * Description:
 *
 * @author damon4u
 * @version 2018-10-19 11:31
 */
public abstract class ContextReferenceSetResolver<F extends PsiElement, K extends PsiElement> {

    private static final Splitter SPLITTER = Splitter.on(String.valueOf(ReferenceSetBase.DOT_SEPARATOR));
    
    protected Project project;
    
    protected F element;
    
    protected List<String> texts;

    protected ContextReferenceSetResolver(@NotNull F element) {
        this.element = element;
        this.project = element.getProject();
        this.texts = Lists.newArrayList(SPLITTER.split(getText()));
    }

    @NotNull
    public abstract String getText();

    public final Optional<? extends PsiElement> resolve(int index) {
        // 先获取一级属性
        // 对于简单属性"name"，那么就是这个属性
        // 对于包含引用的情况"user.name"，那么先找到一级属性user
        Optional<K> startElement = getStartElement();
        return startElement.isPresent() ? (texts.size() > 1 ? parseNext(startElement, texts, index) : startElement) : Optional.empty();
    }
    
    public Optional<K> getStartElement() {
        return getStartElement(Iterables.getFirst(texts, null));
    }

    @NotNull
    public abstract Optional<K> getStartElement(@Nullable String firstText);

    private Optional<K> parseNext(Optional<K> current, List<String> texts, int index) {
        int ind = 1;
        while (current.isPresent() && ind <= index) {
            String text = texts.get(ind);
            if (text.contains(" ")) {
                return Optional.empty();
            }
            current = resolve(current.get(), text);
            ind++;
        }
        return current;
    }

    @NotNull
    public abstract Optional<K> resolve(@NotNull K current, @NotNull String text);

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public F getElement() {
        return element;
    }

    public void setElement(F element) {
        this.element = element;
    }
}
