package com.cyberpeace.oj.ojbase.controller.req;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class NoteUpdateReq extends NoteAddReq {

    @ApiModelProperty("笔记资源编号")
    @NotBlank
    private String resourceId;

}
