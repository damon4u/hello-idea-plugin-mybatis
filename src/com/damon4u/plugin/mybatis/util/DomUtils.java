package com.damon4u.plugin.mybatis.util;

import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * Description:
 *
 * @author damon4u
 * @version 2018-10-18 18:36
 */
public final class DomUtils {
    
    private DomUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 判断是否是mybatis的mapper文件
     */
    public static boolean isMybatisFile(@Nullable PsiFile file) {
        if (null == file || isNotXmlFile(file)) {
            return false;
        }
        XmlTag rootTag = ((XmlFile) file).getRootTag();
        return null != rootTag && "mapper".equals(rootTag.getName());
    }

    private static boolean isNotXmlFile(@NotNull PsiFile file) {
        return !(file instanceof XmlFile);
    }
}
