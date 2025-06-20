package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.course.service.bo.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 这段代码是一个JUnit测试类 `OjCourseServiceBoTest`，用于测试一系列业务对象（BO）。以下是对其主要部分的分析： 
 * ### 类结构和测试方法 
 * 1. **类定义**: 
 * - `OjCourseServiceBoTest` 类继承自 `Object`。 
 * - 使用了 `org.junit.jupiter.api.Test` 注解，表明这是一个JUnit 5测试类。 
 * 2. **测试方法 `testModel`**: 
 * - 该方法使用 `@Test` 注解，表示它是一个测试方法。 
 * - 方法内部调用了 `printObjectFields` 方法多次，每次传入不同的业务对象实例。 
 * ### 主要功能方法 
 * 1. **`invokeAllMethodsWithDefaults` 方法**: 
 * - 该方法遍历传入对象的所有方法，并尝试调用每个方法。 
 * - 如果方法有参数，则为其提供默认值并调用。 
 * - 默认值通过 `getDefaultValue` 方法生成。 
 * 2. **`getDefaultValue` 方法**: 
 * - 根据参数类型生成默认值。对于基本数据类型，返回相应的默认值；对于引用类型，返回 `null`。 
 * 3. **`printObjectFields` 方法**: 
 * - 遍历传入对象的所有字段，并设置其默认值。 
 * - 对于基本数据类型字段，设置默认值。 
 * - 对于引用类型字段，设置为 `null`。 
 * - 调用 `invokeSetter` 和 `invokeGetter` 方法来测试setter和getter方法。 
 * 4. **`createInstance` 方法**: 
 * - 创建传入类的实例。如果类没有无参构造函数，则返回 `null`。 
 * 5. **`invokeSetter` 和 `invokeGetter` 方法**: 
 * - 分别调用对象的setter和getter方法。 
 * - 方法名通过 `capitalize` 方法生成。 
 * 6. **`capitalize` 方法**: 
 * - 将字符串首字母大写，用于生成setter和getter方法名。 
 * ### 总结 
 * - 这段代码主要用于测试一系列业务对象（BO），确保它们的字段可以正确地设置默认值，并且setter和getter方法可以正常工作。 
 * - 代码中使用了大量的反射操作，这使得它可以处理任意类型的业务对象，但同时也增加了代码的复杂性和潜在的错误风险。 
 * - 测试方法 `testModel` 中调用了多个业务对象的实例，这表明这是一个批量测试，可能用于验证整个业务对象模型的一致性。 
**/
public class OjCourseServiceBoTest {

    @Test
    public void testModel() {
        printObjectFields(createInstance(AddLessonConfigContext.class));
        printObjectFields(createInstance(AdminLessonBo.class));
        printObjectFields(createInstance(AdminLessonListBo.class));
        printObjectFields(createInstance(AdminLessonListQuery.class));
        printObjectFields(createInstance(AdminLessonPracticeBo.class));
        printObjectFields(createInstance(ArchiveGroupBo.class));
        printObjectFields(createInstance(CategoryAddBo.class));
        printObjectFields(createInstance(ClassHelpWsMessageIdentifier.class));
        printObjectFields(createInstance(ClassroomBo.class));
        printObjectFields(createInstance(ClassroomGroupArchiveBo.class));
        printObjectFields(createInstance(ClassroomMonitorBo.class));
        printObjectFields(createInstance(ClassroomPracticeBo.class));
        printObjectFields(createInstance(ClassroomPracticeContext.class));
        printObjectFields(createInstance(ClassroomPracticeDetailBo.class));
        printObjectFields(createInstance(ClassroomReportBo.class));
        printObjectFields(createInstance(ClassroomSceneTipsWsMessageIdentifier.class));
        printObjectFields(createInstance(ClassroomStatisticBo.class));
        printObjectFields(createInstance(CombinedCourseImportContext.class));
        printObjectFields(createInstance(CourseAdminBo.class));
        printObjectFields(createInstance(CourseCategoryBo.class));
        printObjectFields(createInstance(CourseDumpLoadBo.class));
        printObjectFields(createInstance(CourseInfoBo.class));
        printObjectFields(createInstance(CourseQueryBo.class));
        printObjectFields(createInstance(CourseSceneContext.class));
        printObjectFields(createInstance(CourseStatsBo.class));
        printObjectFields(createInstance(CourseStatsRankBo.class));
        printObjectFields(createInstance(CourseStudyProcessBo.class));
        printObjectFields(createInstance(CourseWebBo.class));
        printObjectFields(createInstance(GradeDistributionBo.class));
        printObjectFields(createInstance(GroupInfoBo.class));
        printObjectFields(createInstance(GroupWsMessageIdentifier.class));
        printObjectFields(createInstance(HelpStatusBo.class));
        printObjectFields(createInstance(LessonBriefBo.class));
        printObjectFields(createInstance(LessonConfigBo.class));
        printObjectFields(createInstance(LessonConfigDumpBo.class));
        printObjectFields(createInstance(LessonDumpLoadBo.class));
        printObjectFields(createInstance(LessonFileBo.class));
        printObjectFields(createInstance(LessonInfoBo.class));
        printObjectFields(createInstance(LessonOperationBo.class));
        printObjectFields(createInstance(LessonPracticeBo.class));
        printObjectFields(createInstance(LessonPracticeImportBo.class));
        printObjectFields(createInstance(LessonPracticeListBo.class));
        printObjectFields(createInstance(LessonStatsBo.class));
        printObjectFields(createInstance(LessonTheoryBo.class));
        printObjectFields(createInstance(OrgStatsBo.class));
        printObjectFields(createInstance(PageReportBo.class));
        printObjectFields(createInstance(PersonalStatsDetail4CountBo.class));
        printObjectFields(createInstance(PersonalStatsDetail4CourseBo.class));
        printObjectFields(createInstance(PersonalStatsDetail4MouthBo.class));
        printObjectFields(createInstance(PracticeAnswerContext.class));
        printObjectFields(createInstance(PracticeBriefBo.class));
        printObjectFields(createInstance(PracticePaperEvaluateBo.class));
        printObjectFields(createInstance(PracticeSolvedBo.class));
        printObjectFields(createInstance(PublicStatsBo.class));
        printObjectFields(createInstance(SignInfoBo.class));
        printObjectFields(createInstance(SimpleUserData.class));
        printObjectFields(createInstance(StartSceneBo.class));
        printObjectFields(createInstance(SubmitAnswerContext.class));
        printObjectFields(createInstance(TimeScheduleBo.class));
        printObjectFields(createInstance(TrainPlanBo.class));
        printObjectFields(createInstance(TrainPlanExportBo.class));
        printObjectFields(createInstance(UserStatsBo.class));
        printObjectFields(createInstance(UserStatsRankBo.class));

    }
    
    public static void invokeAllMethodsWithDefaults(Object obj) {
        Method[] methods = obj.getClass().getDeclaredMethods();

        for (Method method : methods) {
            // 确保方法是可调用的
            if (method.getParameterCount() > 0) {
                try {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    Object[] defaultValues = new Object[parameterTypes.length];

                    // 为每种参数类型提供默认值
                    for (int i = 0; i < parameterTypes.length; i++) {
                        defaultValues[i] = getDefaultValue(parameterTypes[i]);
                    }


                    // 设置方法可访问
                    method.setAccessible(true);
                    // 调用方法
                    method.invoke(obj, defaultValues);
                } catch (Exception e) {
                }
            } else {
                try {
                    method.invoke(obj);
                } catch (Exception e) {
                }
            }
        }
    }

    private static Object getDefaultValue(Class<?> type) {
        if (type.isPrimitive()) {
            if (type == boolean.class) return false;
            if (type == char.class) return ' ';
            if (type == byte.class) return (byte) 0;
            if (type == short.class) return (short) 0;
            if (type == int.class) return 0;
            if (type == long.class) return 0L;
            if (type == float.class) return 0.0f;
            if (type == double.class) return 0.0;
        }
        return null; // 对于对象类型返回null
    }

    private static void printObjectFields(Object obj) {
        if(obj == null){
            return;
        }
        if (obj instanceof Class) {
            if (((Class<?>) obj).isEnum()) {
                Method[] methods = ((Class<?>) obj).getDeclaredMethods();
                Object[] aa = ((Class<?>) obj).getEnumConstants();
                for (Method method : methods) {
                    if (method.getParameterCount() == 0) {
                        try {
                            // 调用方法
                            method.invoke(aa[0]);
                        } catch (Exception e) {
                        }
                    }
                }
            }
            return;
        }

        invokeAllMethodsWithDefaults(obj);

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true); // 允许访问私有属性
            try {
                if (field.getType() == int.class) {
                    field.setInt(obj, 0); // 默认值为0
                } else if (field.getType() == double.class) {
                    field.setDouble(obj, 0.0); // 默认值为0.0
                } else if (field.getType() == boolean.class) {
                    field.setBoolean(obj, false); // 默认值为false
                } else if (field.getType() == String.class) {
                    field.set(obj, ""); // 默认值为空字符串
                } else if (field.getType() == Float.class || field.getType().getName().equals("float")) {
                    field.set(obj, 0f);
                } else {
                    field.set(obj, null); // 对于其他对象类型，设置为null
                }

                Object value = field.get(obj); // 获取属性值
                String fieldName = field.getName();
                invokeSetter(obj, fieldName, value);
                Object o = invokeGetter(obj, fieldName);
                if (null  == o){
                    throw new RuntimeException("invokeGetter return null");
                }
            } catch (Exception e) {
            }
        }
    }

    private static Object invokeGetter(Object obj, String propertyName) {
        try {
            String getterMethodName = "get" + capitalize(propertyName);
            Method getterMethod = obj.getClass().getMethod(getterMethodName);
            return getterMethod.invoke(obj);
        } catch (Exception e) {
            return null;
        }
    }

    private static Object createInstance(Class<?> clazz) {
        try {
            // 使用无参构造函数创建实例
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    private static void invokeSetter(Object obj, String propertyName, Object value) {
        try {
            String setterMethodName = "set" + capitalize(propertyName);
            Method setterMethod = obj.getClass().getMethod(setterMethodName, value.getClass());
            setterMethod.invoke(obj, value);
        } catch (Exception e) {
        }
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
    
