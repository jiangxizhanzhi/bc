package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 评论是喜欢请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class CommentIsLikeReq implements Serializable {

    /**
     * 就像
     */
    @ApiModelProperty("是否点赞")
    @NotNull
    private Boolean isLike;

    /**
     * 资源id
     */
    @ApiModelProperty("资源编号")
    @NotBlank
    private String resourceId;

}
