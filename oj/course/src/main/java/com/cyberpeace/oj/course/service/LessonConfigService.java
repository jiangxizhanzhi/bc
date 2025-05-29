package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import com.cyberpeace.oj.course.service.bo.AddLessonConfigContext;
import com.cyberpeace.oj.course.service.bo.LessonConfigBo;
import com.cyberpeace.oj.course.service.bo.LessonConfigDumpBo;

import java.util.List;

/**
 * 课配置Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see IService
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LessonConfigService` 的接口，该接口扩展了 `IService<LessonConfig>` 接口。这个服务接口主要用于处理与课程配置相关的业务逻辑。以下是对这段代码的简要分析： 
 * ### 包声明 
 * - `com.cyberpeace.oj.course.service`: 这个包名表明这些类和服务都与在线教育平台中的课程管理有关。 
 * ### 接口定义 
 * - `LessonConfigService`: 这是一个服务接口，它继承自 `IService<LessonConfig>`，表明该接口提供了对 `LessonConfig` 实体进行增删改查等基本操作的能力。 
 * ### 方法 
 * 1. **`getLessonConfig(String courseId, AuthContext authContext)`** 
 * - 功能：根据课程编号和认证上下文信息查询课程目录。 
 * - 返回类型：`LessonConfigBo`，这是一个表示课程配置业务对象的数据传输对象（DTO）。 
 * 2. **`updateLessonConfig(LessonConfigBo configBo)`** 
 * - 功能：更新课时配置。 
 * - 返回类型：`LessonConfigBo`，返回更新后的课时配置信息。 
 * 3. **`packageLessonConfig(AddLessonConfigContext configContext)`** 
 * - 功能：根据课时数据组装课程目录数据。 
 * - 返回类型：`LessonConfig`，表示打包后的课程配置实体。 
 * 4. **`dumpLessonConfig(String courseId, AuthContext authContext)`** 
 * - 功能：导出课程数据目录。 
 * - 返回类型：`LessonConfigDumpBo`，这是用于导出课程配置的DTO。 
 * 5. **`loadLessonConfig(LessonConfigDumpBo lessonConfig, String courseId, List<CourseLesson> courseLessons)`** 
 * - 功能：根据章节数据加载课程配置。 
 * - 返回类型：`List<LessonConfig>`，返回一个包含多个课程配置实体的列表。 
 * ### 使用到的其他类 
 * - `AuthContext`: 认证上下文信息，可能包含了用户身份验证相关信息。 
 * - `CourseLesson`: 课程课时实体类。 
 * - `LessonConfig`: 课程配置实体类。 
 * - `AddLessonConfigContext`: 用于添加课程配置的上下文信息。 
 * - `LessonConfigBo`: 课程配置业务对象。 
 * - `LessonConfigDumpBo`: 用于导出课程配置的业务对象。 
 * ### 总结 
 * `LessonConfigService` 接口提供了一系列方法来处理与课程配置相关的业务逻辑，包括查询、更新、导出和加载课程配置信息。这些方法使用了一些辅助的数据传输对象（DTO）和实体类来传递数据和状态信息。这个接口的设计考虑到了认证和授权的需求，通过 `AuthContext` 参数确保只有经过授权的用户才能访问或修改课程配置信息。 
**/
public interface LessonConfigService extends IService<LessonConfig> {

    /**
     * 根据课程编号用户编号查询课程目录
     *
     * @param courseId    课程编号
     * @param authContext
     * @return
     * @throws
     * @see String
     * @see AuthContext
     * @see LessonConfigBo
     */
    LessonConfigBo getLessonConfig(String courseId, AuthContext authContext);

    /**
     * 更新课时配置
     *
     * @param configBo
     * @return
     * @throws
     * @see LessonConfigBo
     * @see LessonConfigBo
     */
    LessonConfigBo updateLessonConfig(LessonConfigBo configBo);

    /**
     * 根据课时数据 组装课程目录数据
     *
     * @param configContext
     * @return
     * @throws
     * @see AddLessonConfigContext
     * @see LessonConfig
     */
    LessonConfig packageLessonConfig(AddLessonConfigContext configContext);

    /**
     * 导出课程数据目录
     *
     * @param courseId
     * @param authContext
     * @return
     * @throws
     * @see String
     * @see AuthContext
     * @see LessonConfigDumpBo
     */
    LessonConfigDumpBo dumpLessonConfig(String courseId, AuthContext authContext);

    /**
     * 章节数据
     *
     * @param lessonConfig
     * @param courseId
     * @param courseLessons
     * @return
     * @throws
     * @see LessonConfigDumpBo
     * @see String
     * @see List<CourseLesson>
     * @see List<LessonConfig>
     */
    List<LessonConfig> loadLessonConfig(LessonConfigDumpBo lessonConfig, String courseId,
			List<CourseLesson> courseLessons);
}

