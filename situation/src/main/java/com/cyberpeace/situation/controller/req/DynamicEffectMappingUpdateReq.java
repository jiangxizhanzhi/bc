package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuxz
 * @Date 26/11/2022 下午5:15
 */
@Data
public class DynamicEffectMappingUpdateReq extends DynamicEffectMappingReq{
    @ApiModelProperty()
    @NotBlank(groups = { Update.class },
            message = ErrorCode.RESOURCE_NOT_FOUND)
    private String resourceId;
}
