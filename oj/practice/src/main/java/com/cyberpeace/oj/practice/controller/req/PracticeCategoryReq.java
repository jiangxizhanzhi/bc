package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 练习类别请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class PracticeCategoryReq {

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", required = true)
    @NotBlank
    private String name;

    /**
     * 练习类型
     */
    @ApiModelProperty(value = "练习类型 0=理论基础 2=实操习题", required = true)
    @NotNull
    private Integer practiceType;

    /**
     * 父母id
     */
    @ApiModelProperty("所属父级节点")
    private String parentId;

    public void setPractice_type(Integer practiceType) {
        this.practiceType = practiceType;
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
        if (null != name && !name.isEmpty()) {
            CrCommonValidator.validateRange(name.length(),lenMin, categoryMax, OjBusinessErrorCode.MAX_PRACTICE_CATEGORY_128);
        }
    }
}
