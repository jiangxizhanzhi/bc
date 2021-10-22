package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import com.cyberpeace.oj.ojbase.dao.entity.Attachment;
import com.cyberpeace.oj.ojbase.service.bo.AttachmentBo;
import lombok.Data;

@Data
public class LessonInfoBo implements Serializable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String course;

    private String name;

    private Boolean builtin;

    private Integer type;

    private Integer handoutType;

    private String handoutFile;

    private String video;

    private Integer difficulty;

    private Integer isPublic;

    private Integer lessonType;

    private Integer duration;

    private Integer destroyDelay;

    private String ojSceneConfig;

    private Integer shareMode;

    private String knowledge;

    private String createUser;

    private String testPaper;

    private Integer testPaperPublic;

    private Integer heat;

    private Boolean isCollection;

    private List<AttachmentBo> lessonFile;

    private String next;

    private String pre;

    private Integer practiceCount;

    private String sceneConfigData;

    private String sceneData;

    private String sceneConfigName;

    /**
     * 设置上节和下节课程编号
     * @param infoBo
     * @param sortList
     */
    public void setPreAndNext(LessonInfoBo infoBo, List<String> sortList) {

        int index = sortList.indexOf(infoBo.getResourceId());
        if (index > 0 && index < sortList.size()) {
            infoBo.setPre(sortList.get(index - 1));
            infoBo.setNext(sortList.get(index + 1));
        } else {
            if (index == 0) {
                infoBo.setNext(sortList.get(index + 1));
            } else if (index >= sortList.size() - 1) {
                infoBo.setPre(sortList.get(index - 1));
            }
        }
    }

}
