package com.cyberpeace.oj.course.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cs.service.context.AttachmentBo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 管理课详情结果
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel("后台课时详情")
public class AdminLessonDetailRes implements Serializable {

    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 课程
     */
    private String course;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 讲义类型
     */
    private Integer handoutType;

    /**
     * 讲义文件
     */
    private String handoutFile;

    /**
     * 视频
     */
    private String video;

    /**
     * 困难
     */
    private Integer difficulty;

    /**
     * 是公共
     */
    private Integer isPublic;

    /**
     * 课程类型
     */
    private Integer lessonType;

    /**
     * 期间
     */
    private Integer duration;

    /**
     * OJ场景配置
     */
    private String ojSceneConfig;

    /**
     * 共享模式
     */
    private Integer shareMode;

    /**
     * 知识
     */
    private String knowledge;

    /**
     * 创建用户
     */
    private String createUser;

    /**
     * 试试卷
     */
    private String testPaper;

    /**
     * 试卷 公开
     */
    private Integer testPaperPublic;

    /**
     * 课程档案
     */
    private List<AttachmentBo> lessonFile;

    /**
     * 练习计数
     */
    private Integer practiceCount;

    /**
     * 场景配置名称
     */
    private String sceneConfigName;

    /**
     * 场景许可
     */
    private Boolean scenePermission;

}
