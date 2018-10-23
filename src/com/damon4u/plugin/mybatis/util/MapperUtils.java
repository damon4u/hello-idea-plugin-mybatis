package com.damon4u.plugin.mybatis.util;

import com.damon4u.plugin.mybatis.dom.model.Association;
import com.damon4u.plugin.mybatis.dom.model.Collection;
import com.damon4u.plugin.mybatis.dom.model.ResultMap;
import com.intellij.psi.PsiClass;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlElement;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Description:
 *
 * @author damon4u
 * @version 2018-10-23 15:56
 */
public final class MapperUtils {

    private MapperUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取property从属的类型
     *
     * @param attributeValue property属性
     * @return
     */
    public static Optional<PsiClass> getPropertyClazz(XmlAttributeValue attributeValue) {
        DomElement domElement = DomUtil.getDomElement(attributeValue);
        if (null == domElement) {
            return Optional.empty();
        }
        // collection标签下的property，那么类型为ofType指定
        Collection collection = DomUtil.getParentOfType(domElement, Collection.class, true);
        if (null != collection && isNotWithinSameTag(collection, attributeValue)) {
            return Optional.ofNullable(collection.getOfType().getValue());
        }
        // association标签下的property，那么类型为javaType指定
        Association association = DomUtil.getParentOfType(domElement, Association.class, true);
        if (null != association && isNotWithinSameTag(association, attributeValue)) {
            return Optional.ofNullable(association.getJavaType().getValue());
        }
        // resultMap标签下的property，那么类型为type指定
        ResultMap resultMap = DomUtil.getParentOfType(domElement, ResultMap.class, true);
        if (null != resultMap && isNotWithinSameTag(resultMap, attributeValue)) {
            return Optional.ofNullable(resultMap.getType().getValue());
        }
        return Optional.empty();

    }

    private static boolean isNotWithinSameTag(@NotNull DomElement domElement, @NotNull XmlElement xmlElement) {
        XmlTag xmlTag = PsiTreeUtil.getParentOfType(xmlElement, XmlTag.class);
        return !domElement.getXmlTag().equals(xmlTag);
    }
}
