package com.cyberpeace.oj.practice.controller.req;

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
}
