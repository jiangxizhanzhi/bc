package com.cyberpeace.cr.crbase.controller.req.crtitle;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CrTitleLevelReq implements Serializable {

    @ApiModelProperty("资源ID标识")
    private String resourceId;

    @ApiModelProperty("名称")
    private String name;

    public void validate() {
        if (name != null) {
            CrCommonValidator.validateLength(name,
                    CrCommonConstants.MIN_STR_LEN,
                    CrCommonConstants.LEVEL_NAME_MAX_LEN,
                    CrBusinessErrorCode.ILLEGAL_NAME_LEN
            );
        }
    };
}
