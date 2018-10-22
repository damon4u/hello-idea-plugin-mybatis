package com.damon4u.plugin.mybatis.dom.model;

import com.intellij.psi.PsiClass;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.GenericAttributeValue;
import org.jetbrains.annotations.NotNull;

/**
 * Description:
 * 可以配置请求参数的mapper语句
 *
 * @author damon4u
 * @version 2018-10-18 17:19
 */
public interface ParameteredDynamicQueryableDomElement extends DynamicQueryableDomElement, IdDomElement {
    
    // TODO: id
    
    @NotNull
    @Attribute("parameterType")
    // TODO: converter
    GenericAttributeValue<PsiClass> getParameterType();
}
