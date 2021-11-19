package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class LibPracticeListBo implements Serializable {

    private String resourceId;

    private String title;

    private String categoryId;

    private Integer practiceType;

    private Integer questionType;

}
