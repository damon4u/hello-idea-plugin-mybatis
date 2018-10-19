package com.damon4u.plugin.mybatis.dom.model;

import com.intellij.psi.PsiClass;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.SubTagList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Description:
 *
 * @author damon4u
 * @version 2018-10-18 18:23
 */
public interface ParameterMap extends IdDomElement {
    
    @NotNull
    @Attribute("type")
    // TODO: converter
    GenericAttributeValue<PsiClass> getType();
    
    @SubTagList("parameter")
    List<Parameter> getParameters();
}
