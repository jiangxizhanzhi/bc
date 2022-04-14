package com.cyberpeace.cr.crbase.service.impl.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CrTitleCertBo {
    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("用户姓名")
    private String username;

    @ApiModelProperty("等级名称")
    private String level;

    @ApiModelProperty("等级ID")
    private String levelId;

    @ApiModelProperty("称号名称")
    private String title;

    @ApiModelProperty("称号等级")
    private String titleId;

    @ApiModelProperty("证书获取日期")
    private String createTime;

    @ApiModelProperty("有效期")
    private String validityTime;

    @ApiModelProperty("证书编号")
    private String certNumber;

}
