package com.cyberpeace.oj.course.controller.req;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class IndexCategoryReq implements Serializable {
    @ApiModelProperty("课程")
    private List<IndexCourseReq> courses;

    private String categoryName;
    @ApiModelProperty("覆盖度")
    private Integer coverage;
    @ApiModelProperty("权重")
    private String weight;
}
