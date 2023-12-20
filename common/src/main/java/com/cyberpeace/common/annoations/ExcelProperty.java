package com.cyberpeace.common.annoations;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelProperty {
    /**
     * 标注Excel单元格下标
     * @return  int
     */
    int index();

    String value();
}
