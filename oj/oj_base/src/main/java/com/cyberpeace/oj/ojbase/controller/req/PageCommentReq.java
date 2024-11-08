package com.cyberpeace.oj.ojbase.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页评论请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class PageCommentReq extends PageReqBase {

    /**
     * 业务id
     */
    @ApiModelProperty(name = "business_id", value = "资源编号")
    private String businessId;

    /**
     * 业务类型
     */
    @ApiModelProperty(name = "business_type", value = "资源类型 1=课程 2=课时 3=实操题")
    private Integer businessType;

    public void setBusiness_id(String businessId) {
        this.businessId = businessId;
    }

    public void setBusiness_type(Integer businessType) {
        this.businessType = businessType;
    }
}
