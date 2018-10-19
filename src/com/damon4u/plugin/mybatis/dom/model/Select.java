package com.damon4u.plugin.mybatis.dom.model;

import com.intellij.psi.PsiClass;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.GenericAttributeValue;
import org.jetbrains.annotations.NotNull;

/**
 * Description:
 * 查询语句
 *
 * @author damon4u
 * @version 2018-10-18 17:38
 */
public interface Select extends ParameteredDynamicQueryableDomElement, ResultMapAttributeDomElement {
    
    @NotNull
    @Attribute("resultType")
    // TODO: converter
    GenericAttributeValue<PsiClass> getResultType();
}
