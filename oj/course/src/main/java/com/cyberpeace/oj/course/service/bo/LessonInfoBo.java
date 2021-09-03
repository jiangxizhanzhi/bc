package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.oj.course.dao.entity.LessonFile;

import lombok.Data;

@Data
public class LessonInfoBo implements Serializable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String course;

    private String name;

    private Integer type;

    private String pdf;

    private String html;

    private Integer htmlType;

    private String video;

    private Integer difficulty;

    private Integer isPublic;

    private String attachments;

    private Integer lessonType;

    private Integer duration;

    private Integer destroyDelay;

    private String ojSceneConfig;

    private String knowledge;

    private String markdown;

    private String markdownFile;

    private String createUser;

    private String testPaper;

    private Integer testPaperPublic;

    private Integer heat;

    private Boolean isCollection;

    private List<LessonFile> lessonFile;

    private String next;

    private String pre;

    private Integer practiceCount;

    private String sceneConfigData;

    private String sceneData;

    private String sceneConfigName;

}
