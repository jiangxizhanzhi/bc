package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("岗位和用户绑定信息")
@TableName("rtks_user_role")
public class UserRole extends BaseTable {

    @ApiModelProperty("用户编号")
    @TableId(type = IdType.UUID, value = "user_id")
    private String userId;

    @ApiModelProperty("姓名")
    private String userName;

    @ApiModelProperty("岗位编号")
    private String roleId;

    @ApiModelProperty("岗位名称")
    private String roleName;

}
