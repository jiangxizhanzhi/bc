package com.cyberpeace.cr.crbase.controller.req.crtitle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class CrTitleDeleteReq implements Serializable {
    @NotNull
    @ApiModelProperty("需要删除id")
    private List<String> resourceIds;
}
