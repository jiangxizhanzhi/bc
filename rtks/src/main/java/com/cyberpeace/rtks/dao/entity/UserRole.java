package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("rtks_user_role")
public class UserRole extends BaseTable {

    @TableId(type = IdType.UUID, value = "user_id")
    private String userId;

    private String userName;

    private String roleId;

    private String roleName;

}
