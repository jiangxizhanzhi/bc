package com.cyberpeace.oj.ojbase.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebNoteRes implements Serializable {

    @ApiModelProperty("笔记编号")
    private String resourceId;

    @ApiModelProperty("访问类型")
    private Integer readAuthType;

    @ApiModelProperty("访问编号")
    private String readAuthIds;

    @ApiModelProperty("笔记内容")
    private String content;

    @ApiModelProperty("更新时间")
    private Date updateTime;

}
