package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 3/11/2022 上午10:30
 */
@Data
public class DataSourceRecordReq implements Serializable {

    @NotBlank(groups = {Update.class})
    private String resourceId;

    @ApiModelProperty("数据源名称")
    @NotBlank(groups = {Create.class})
    private String name;

    @ApiModelProperty("数据源类型")
    @NotBlank(groups = {Create.class})
    private String type;

    @ApiModelProperty("连接url")
    @NotBlank(groups = {Create.class})
    private String connectAddress;

    @ApiModelProperty("端口")
    @NotBlank(groups = {Create.class})
    private String port;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("密码")
    private String password;
}
