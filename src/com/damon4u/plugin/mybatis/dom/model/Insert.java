package com.damon4u.plugin.mybatis.dom.model;

import com.intellij.util.xml.SubTagList;

import java.util.List;

/**
 * Description:
 * 插入语句
 *
 * @author damon4u
 * @version 2018-10-18 17:32
 */
public interface Insert extends ParameteredDynamicQueryableDomElement {
    
    @SubTagList("selectKey")
    List<SelectKey> getSelectKey();
}
