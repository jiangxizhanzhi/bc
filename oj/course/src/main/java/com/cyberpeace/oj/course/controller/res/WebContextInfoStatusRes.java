package com.cyberpeace.oj.course.controller.res;

import com.cyberpeace.oj.course.service.bo.ContextInfoStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 场景内上一题 当前 下一题 lessionid及错误信息
 */
@Data
public class WebContextInfoStatusRes implements Serializable {
    @ApiModelProperty("第一次进入的课时id")
    private String startLessonId;
    @ApiModelProperty("上一题")
    private ContextInfoStatus prev;
    @ApiModelProperty("当前题")
    private ContextInfoStatus curr;
    @ApiModelProperty("下一题")
    private ContextInfoStatus next;
    @ApiModelProperty("上一课flg")
    private Integer prevFlg = 0;
    @ApiModelProperty("下一课flg")
    private Integer nextFlg = 0;
}
