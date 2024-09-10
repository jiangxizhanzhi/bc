package com.cyberpeace.oj.course.controller.req;

import java.util.List;

import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReportExportReq {

    @ApiModelProperty(value = "课堂id")
	private String classroomId;

    @ApiModelProperty(value = "组织id")
    private String orgId;

    @ApiModelProperty(value = "学员id列表")
	private List<String> userIds = Lists.newArrayList();
}
