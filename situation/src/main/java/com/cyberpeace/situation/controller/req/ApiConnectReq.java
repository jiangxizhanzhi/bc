package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.dao.entity.ApiRequest;
import com.cyberpeace.situation.dao.entity.ApiRequestHeaders;
import com.cyberpeace.situation.dao.entity.ApiRequestParams;
import com.cyberpeace.situation.dao.entity.ApiResponseParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @Author xuxz
 * @Date 31/10/2022 上午10:12
 */
@Data
public class ApiConnectReq implements Serializable {

    @NotBlank(groups = {Update.class})
    private String resourceId;

    @ApiModelProperty("api名称")
    private String name;

    @ApiModelProperty("api描述")
    private String description;

    @NotBlank(groups = {Create.class})
    @ApiModelProperty("url")
    private String requestUrl;

    @NotBlank(groups = {Create.class})
    @ApiModelProperty("请求方法")
    private String requestMethod;

    @ApiModelProperty("连接状态")
    private Boolean connectStatus;

    @ApiModelProperty("请求参数")
    private List<ApiRequestParams> requestParams;

    @ApiModelProperty("请求头")
    private List<ApiRequestHeaders> requestHeaders;

    @ApiModelProperty("返回值所属key值")
    private String responseKey;

    @ApiModelProperty("返回参数")
    private List<ApiResponseParams> responseParams;
}
