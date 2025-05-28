/**
 * 这段代码定义了一个名为 `CourseSlimDumpBo` 的 Java 类，该类位于 `com.cyberpeace.oj.course.service.bo` 包中。这个类的主要功能是作为数据传输对象（DTO），用于封装课程相关的数据。以下是详细的分析： 
 * ### 1. 类定义 
 * - **包名**：`com.cyberpeace.oj.course.service.bo` 
 * - 这个包名表明这个类是与课程服务相关的一个业务对象。 
 * - **类名**：`CourseSlimDumpBo` 
 * - 类名表明这是一个简化的课程信息传输对象。 
 * ### 2. 导入的类 
 * - **`com.cyberpeace.oj.course.dao.entity.Course`** 
 * - **`com.cyberpeace.oj.course.dao.entity.CourseLesson`** 
 * - **`com.cyberpeace.oj.course.dao.entity.LessonConfig`** 
 * - **`com.cyberpeace.oj.practice.dao.entity.PracticeTheoryRef`** 
 * 这些导入的类都是实体类，分别代表： 
 * - `Course`：课程实体。 
 * - `CourseLesson`：课程中的课时实体。 
 * - `LessonConfig`：课时配置实体。 
 * - `PracticeTheoryRef`：实践理论参考实体。 
 * ### 3. 类属性 
 * - **`private List<Course> courseList;`** 
 * - 存储一系列课程信息。 
 * - **`private List<CourseLesson> lessonList;`** 
 * - 存储一系列课程中的课时信息。 
 * - **`private List<LessonConfig> configList;`** 
 * - 存储一系列课时配置信息。 
 * - **`private List<PracticeTheoryRef> refList;`** 
 * - 存储一系列实践理论参考信息。 
 * ### 4. Lombok 注解 
 * - **`@Setter`** 
 * - 自动生成所有属性的 setter 方法。 
 * - **`@Getter`** 
 * - 自动生成所有属性的 getter 方法。 
 * ### 总结 
 * `CourseSlimDumpBo` 类是一个数据传输对象（DTO），用于在不同层之间传递课程及其相关实体的数据。它通过列表形式存储多个课程、课时、配置以及参考信息，并且使用 Lombok 注解简化了代码，减少了样板代码。 
**/
package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheoryRef;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CourseSlimDumpBo {

	private List<Course> courseList;

	private List<CourseLesson> lessonList;

	private List<LessonConfig> configList;

	private List<PracticeTheoryRef> refList;

}
