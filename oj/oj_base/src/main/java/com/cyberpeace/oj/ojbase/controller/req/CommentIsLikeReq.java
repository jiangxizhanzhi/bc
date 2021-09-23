package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
public class CommentIsLikeReq implements Serializable {

    @ApiModelProperty("是否点赞")
    @NotNull
    private Boolean isLike;

    @ApiModelProperty("资源编号")
    @NotBlank
    private String resourceId;

}
