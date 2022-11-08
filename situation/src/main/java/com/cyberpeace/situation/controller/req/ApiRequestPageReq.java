package com.cyberpeace.situation.controller.req;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 1/11/2022 下午4:57
 */
@Data
public class ApiRequestPageReq extends PageReqBase implements Serializable {
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    @ApiModelProperty("api名称")
    private String name;

    @ApiModelProperty("api描述")
    private String description;

    @ApiModelProperty("url")
    private String requestUrl;

    @ApiModelProperty("请求方法")
    private String requestMethod;

    private String creatUserId;
}
