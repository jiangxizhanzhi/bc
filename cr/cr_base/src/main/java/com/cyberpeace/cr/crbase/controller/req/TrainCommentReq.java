package com.cyberpeace.cr.crbase.controller.req;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TrainCommentReq implements Serializable {
    @ApiModelProperty("训练环境id")
    private String trainId;

    @ApiModelProperty("评论内容")
    private String content;

    public void setTrain_id(String trainId) {
        this.trainId = trainId;
    }

    public String getContent() {
        if (content == null) {
            throw new BusinessException(CrBusinessErrorCode.TRAIN_COMMENT_IS_ILLEGAL,
                    CrCommonConstants.MIN_STR_LEN,
                    CrCommonConstants.COMMENT_MAX_LEN);
        }

        CrCommonValidator.validateLength(content,
                CrCommonConstants.MIN_STR_LEN,
                CrCommonConstants.COMMENT_MAX_LEN,
                CrBusinessErrorCode.TRAIN_COMMENT_IS_ILLEGAL
        );
        return content;
    }
}
