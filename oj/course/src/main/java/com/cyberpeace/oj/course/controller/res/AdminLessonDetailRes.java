package com.cyberpeace.oj.course.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.oj.course.dao.entity.LessonFile;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("后台课时详情")
public class AdminLessonDetailRes implements Serializable {

    private String resourceId;

    private String course;

    private String name;

    private Integer type;

    private Integer handoutType;

    private String handoutFile;

    private String video;

    private Integer difficulty;

    private Integer isPublic;

    private Integer lessonType;

    private Integer duration;

    private String ojSceneConfig;

    private Integer shareMode;

    private String knowledge;

    private String createUser;

    private String testPaper;

    private Integer testPaperPublic;

    private List<LessonFile> lessonFile;

    private Integer practiceCount;

    private String sceneConfigName;

    private Boolean scenePermission;

}
