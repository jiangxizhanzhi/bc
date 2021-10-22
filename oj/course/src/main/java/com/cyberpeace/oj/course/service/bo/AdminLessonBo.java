package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.oj.ojbase.service.bo.AttachmentBo;
import lombok.Data;

@Data
public class AdminLessonBo implements Serializable {

    private String resourceId;

    private String courseId;

    private String name;

    private Integer type;

    private Integer handoutType;

    private String handoutUrl;

    private String videoUrl;

    private Integer difficulty;

    private Boolean isPublic;

    private Integer compulsory;

    private Integer destroyDelay;

    private Integer duration;

    private String topoId;

    private Integer sceneShareMode;

    private String sceneConfig;

    private String originMarkdownFile;

    private Integer practiceCount;

    private String paperId;

    private String knowledge;

    private List<AttachmentBo> attachments;

}
