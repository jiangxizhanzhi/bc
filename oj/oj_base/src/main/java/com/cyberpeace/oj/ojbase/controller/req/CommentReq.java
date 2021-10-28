package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel("资源评论信息")
public class CommentReq implements Serializable {

    @ApiModelProperty("评论内容")
    @NotBlank
    private String content;

    @ApiModelProperty("资源编号 课程 课时 练习")
    @NotBlank
    private String businessId;

    @ApiModelProperty("资源评论编号")
    private String rootId;

    @ApiModelProperty("被回复评论编号")
    private String replyId;

}
