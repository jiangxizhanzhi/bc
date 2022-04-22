package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TrainCommentReq implements Serializable {
    @ApiModelProperty("训练模板id")
    private String templateId;

    @ApiModelProperty("评论内容")
    private String content;

    public void setTemplate_id(String templateId) {
        this.templateId = templateId;
    }
}
