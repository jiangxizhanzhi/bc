package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.utils.OjCommonValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class CategoryUpdateReq implements Serializable {

    @ApiModelProperty("名称")
    @NotBlank
    private String name;

    @ApiModelProperty("编号")
    @NotBlank
    private String resourceId;

    @ApiModelProperty("父级编号")
    private String parentId;

    public void setResource_id(String resourceId) {
        this.resourceId = resourceId;
    }

    public void setParent_id(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return StringUtils.isNotBlank(name) ? name.trim() : name;
    }

    public void validate() {
        // 校验方向长度
        int lenMin = Integer.parseInt(ConfigUtil.getContextProperty("oj.str.len.name.min"));
        int categoryMax = Integer.parseInt(ConfigUtil.getContextProperty("oj.practice.category.max"));
        if (null != name) {
            OjCommonValidator.validateRange(name.length(),lenMin, categoryMax, OjBusinessErrorCode.MAX_PRACTICE_CATEGORY_128);
        }
    }
}
