package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TrainCommentReq implements Serializable {
    @ApiModelProperty("训练环境id")
    private String trainId;

    @ApiModelProperty("评论内容")
    private String content;

    public void setTrain_id(String trainId) {
        this.trainId = trainId;
    }
}
