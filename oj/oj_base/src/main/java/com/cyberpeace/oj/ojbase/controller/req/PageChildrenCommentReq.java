package com.cyberpeace.oj.ojbase.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页子级评论请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class PageChildrenCommentReq extends PageReqBase {
    /**
     * 资源id
     */
    @ApiModelProperty(name= "resource_id",value = "一级留言编号")
    private String resourceId;

    public void setResource_id(String resourceId){
        this.resourceId = resourceId;
    }
}
