package com.cyberpeace.oj.ojbase.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageChildrenCommentReq extends PageReqBase {
    @ApiModelProperty(name= "resource_id",value = "一级留言编号")
    private String resourceId;

    public void setResource_id(String resourceId){
        this.resourceId = resourceId;
    }
}
