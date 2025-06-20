package com.cyberpeace.oj.course.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cs.service.context.AttachmentBo;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 管理课详情结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `AdminLessonDetailRes` 的 Java 类，用于表示管理员查看课程详细信息的结果。以下是该类的主要特性和功能： 
 * ### 类定义与继承 
 * - `AdminLessonDetailRes` 类实现了 `Serializable` 接口，这表明它可以被序列化，通常用于在网络上传输或持久化对象。 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter`，简化了属性的 getter 和 setter 方法的生成。 
 * ### 类文档 
 * - 类文档注释说明了这个类是“后台课时详情”，并提供了作者、版本和日期信息。 
 * ### 属性 
 * - 类中包含多个属性，每个属性都有详细的 Javadoc 注释说明其用途： 
 * - `resourceId`, `course`, `name`: 课程的基本信息。 
 * - `type`, `handoutType`, `difficulty`, `isPublic`, `lessonType`, `duration`, `shareMode`: 课程的各种属性和配置。 
 * - `video`, `handoutFile`, `ojSceneConfig`, `knowledge`, `testPaper`, `sceneConfigName`: 课程相关的文件和配置信息。 
 * - `createUser`, `practiceCount`, `lessonFile`: 与课程创建者、实践次数和附件列表相关的信息。 
 * - `testPaperPublic`, `scenePermission`: 课程的公开状态和场景权限。 
 * ### 使用的其他类 
 * - `AttachmentBo`: 一个表示附件的类，用于存储课程的附件信息。 
 * ### 总结 
 * - `AdminLessonDetailRes` 类是一个数据传输对象（DTO），用于封装课程的详细信息，便于在系统内部传递这些信息。 
 * - 类中的属性涵盖了课程的各个方面，包括基本信息、配置信息、附件等。 
 * - 通过实现 `Serializable` 接口，使得该类的对象可以被序列化，方便在网络传输和持久化存储中使用。 
**/
@Getter
@Setter
@ApiModel("后台课时详情")
public class AdminLessonDetailRes implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
    /**
     * 资源id
     *
     * @see String
     */
    private String resourceId;

    /**
     * 课程
     *
     * @see String
     */
    private String course;

    /**
     * 名称
     *
     * @see String
     */
    private String name;

    /**
     * 类型
     *
     * @see Integer
     */
    private Integer type;

    /**
     * 讲义类型
     *
     * @see Integer
     */
    private Integer handoutType;

    /**
     * 讲义文件
     *
     * @see String
     */
    private String handoutFile;

    /**
     * 视频
     *
     * @see String
     */
    private String video;

    /**
     * 难度
     *
     * @see Integer
     */
    private Integer difficulty;

    /**
     * 是否公开
     *
     * @see Integer
     */
    private Integer isPublic;

    /**
     * 课程类型
     *
     * @see Integer
     */
    private Integer lessonType;

    /**
     * 期间
     *
     * @see Integer
     */
    private Integer duration;

    /**
     * OJ场景配置
     *
     * @see String
     */
    private String ojSceneConfig;

    /**
     * 共享模式
     *
     * @see Integer
     */
    private Integer shareMode;

    /**
     * 知识
     *
     * @see String
     */
    private String knowledge;

    /**
     * 创建用户
     *
     * @see String
     */
    private String createUser;

    /**
     * 试纸
     *
     * @see String
     */
    private String testPaper;

    /**
     * 试卷 公开
     *
     * @see Integer
     */
    private Integer testPaperPublic;

    /**
     * 课程档案
     *
     * @see List<AttachmentBo>
     */
    private List<AttachmentBo> lessonFile;

    /**
     * 实践计数
     *
     * @see Integer
     */
    private Integer practiceCount;

    /**
     * 场景配置名称
     *
     * @see String
     */
    private String sceneConfigName;

    /**
     * 场景许可
     *
     * @see Boolean
     */
    private Boolean scenePermission;

}

