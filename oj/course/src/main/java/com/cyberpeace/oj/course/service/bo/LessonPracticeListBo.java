package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class LessonPracticeListBo implements Serializable {

    private String resourceId;

    private Double totalScore;

    private List<? extends LessonTheoryBo> practiceBos;

}
