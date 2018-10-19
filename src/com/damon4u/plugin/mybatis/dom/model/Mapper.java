package com.damon4u.plugin.mybatis.dom.model;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.NameValue;
import com.intellij.util.xml.Required;
import com.intellij.util.xml.SubTagList;
import com.intellij.util.xml.SubTagsList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Description:
 *
 * @author damon4u
 * @version 2018-10-18 16:01
 */
public interface Mapper extends DomElement {

    /**
     * 一次返回mapper文件中增删改查子tag，在寻找候选tag时需要
     * @return 增删改查子tag列表，每个子tag都包含id属性
     */
    @NotNull
    @SubTagsList({"insert", "update", "delete", "select"})
    List<IdDomElement> getDaoElements();

    /**
     * mapper标签包含namespace属性
     */
    @Required
    @NameValue
    @NotNull
    @Attribute("namespace")
    GenericAttributeValue<String> getNamespace();
    
    @NotNull
    @SubTagList("resultMap")
    List<ResultMap> getResultMaps();
    
    @NotNull
    @SubTagList("parameterMap")
    List<ParameterMap> getParameterMaps();
    
    @NotNull
    @SubTagList("sql")
    List<Sql> getSqls();
    
    @NotNull
    @SubTagList("insert")
    List<Insert> getInserts();
    
    @NotNull
    @SubTagList("update")
    List<Update> getUpdates();
    
    @NotNull
    @SubTagList("delete")
    List<Delete> getDeletes();
    
    @NotNull
    @SubTagList("select")
    List<Select> getSelects();
    
}
