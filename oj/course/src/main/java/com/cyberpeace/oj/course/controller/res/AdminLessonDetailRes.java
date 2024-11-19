package com.cyberpeace.oj.course.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cs.service.context.AttachmentBo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 管理课时详情结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("后台课时详情")
public class AdminLessonDetailRes implements Serializable {

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
     * 课时类型
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
     * 知识点
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
     * 测试试卷
     *
     * @see String
     */
    private String testPaper;

    /**
     * 测试试卷公共
     *
     * @see Integer
     */
    private Integer testPaperPublic;

    /**
     * 课时文件
     *
     * @see List<AttachmentBo>
     */
    private List<AttachmentBo> lessonFile;

    /**
     * 练习计数
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
