package com.cyberpeace.auth.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author :   SN
 * @date :   2023/12/22 17:43
 * @desc :
 **/
@Data
public class UserReq extends PageReqBase {

    @ApiModelProperty("用户状态")
    private Integer enabled;
}
