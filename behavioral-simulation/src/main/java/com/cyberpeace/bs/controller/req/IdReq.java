package com.cyberpeace.bs.controller.req;

import com.cyberpeace.bs.constant.BsErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * id请求
 * 用于请求体是id的情况
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class IdReq implements Serializable {

    @ApiModelProperty(value = "id")
    private String resourceId;

    /**
     * 获得id
     * 检测输入的id是否为空
     *
     * @return {@code String}
     */
    public String getResourceId() {
        if (StringUtils.isBlank(resourceId)) {
            throw new BusinessException(BsErrorCode.RESOURCE_ID_MISSING);
        }
        return resourceId;
    }
}
