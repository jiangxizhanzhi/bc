package com.cyberpeace.cr.evaluation.utils;

import java.lang.reflect.Field;

public class checkField {

    public static Object getFieldValue(Object obj, String fieldName) {
        Field field;
        Object fieldValue = null;
        try {
            field = obj.getClass().getDeclaredField(fieldName);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            fieldValue = field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Handle exception if the field does not exist or is inaccessible
        }
        return fieldValue;
    }
}
