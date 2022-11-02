package com.cyberpeace.situation.controller.res;

import com.cyberpeace.situation.dao.entity.ApiRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 31/10/2022 上午10:22
 */
@Data
public class ApiRequestInfoRes extends ApiRequest implements Serializable {

    @ApiModelProperty("请求返回参数")
    private String response;
}
