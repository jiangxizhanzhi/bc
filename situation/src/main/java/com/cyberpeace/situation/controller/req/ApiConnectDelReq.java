package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @Author xuxz
 * @Date 31/10/2022 上午10:29
 */
@Data
public class ApiConnectDelReq implements Serializable {

    @ApiModelProperty("资源编号")
    @NotEmpty
    private List<String> apiConnectIdList;
}
