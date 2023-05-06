package com.cyberpeace.bs.controller.req;

import com.cyberpeace.bs.constant.BsErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * id列表请求
 * 用于输入请求体是id列表的情况
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class IdListReq implements Serializable {

    @ApiModelProperty(value = "id列表")
    private List<String> ids;

    /**
     * 得到id列表
     * 检测传入id列表是否为空
     *
     * @return {@code List<String>}
     */
    public List<String> getIds() {
        if (CollectionUtils.isEmpty(ids)) {
            throw new BusinessException(BsErrorCode.EMPTY_ID_COLL);
        }
        return ids;
    }
}
