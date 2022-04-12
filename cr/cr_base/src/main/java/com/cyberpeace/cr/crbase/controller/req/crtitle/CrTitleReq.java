package com.cyberpeace.cr.crbase.controller.req.crtitle;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CrTitleReq implements Serializable {

    @ApiModelProperty("资源ID标识")
    private String resourceId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("等级")
    private String levelId;

    @ApiModelProperty("及格标准")
    private Integer score;

    @ApiModelProperty("关联训练ids")
    private List<String> businessTemplateIds;

    public void validate() {
        // 分值范围验证
        if (score != null && (CrCommonConstants.TITLE_MIN_SCORE > score || score > CrCommonConstants.TITLE_MAX_SCORE)) {
            throw new BusinessException(
                    CrBusinessErrorCode.TITLE_SCORE_VALUE_ERROR,
                    CrCommonConstants.TITLE_MIN_SCORE,
                    CrCommonConstants.TITLE_MAX_SCORE
            );
        }
        if (name != null) {
            CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
        }
    };

}
