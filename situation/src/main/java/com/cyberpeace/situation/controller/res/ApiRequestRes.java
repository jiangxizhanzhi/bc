package com.cyberpeace.situation.controller.res;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.situation.dao.entity.ApiRequestHeaders;
import com.cyberpeace.situation.dao.entity.ApiRequestParams;
import com.cyberpeace.situation.dao.entity.ApiResponseParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author xuxz
 * @Date 1/11/2022 下午4:10
 */
@Data
public class ApiRequestRes extends PageReqBase implements Serializable {

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

    @ApiModelProperty("请求参数")
    private String requestParams;

    @ApiModelProperty("请求头")
    private String requestHeaders;


}
