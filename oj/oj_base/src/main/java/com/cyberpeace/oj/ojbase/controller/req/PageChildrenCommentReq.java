package com.cyberpeace.oj.ojbase.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页子级评论请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
@Getter
@Setter
public class PageChildrenCommentReq extends PageReqBase {
    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty(name= "resource_id",value = "一级留言编号")
    private String resourceId;

    public void setResource_id(String resourceId){
        this.resourceId = resourceId;
    }
}
