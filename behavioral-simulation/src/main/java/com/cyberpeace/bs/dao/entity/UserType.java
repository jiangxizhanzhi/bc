package com.cyberpeace.bs.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * 用户类型
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
@TableName("bs_user_type")
public class UserType extends BaseTable {

    /**
     * 资源id
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 用户模型名称
     */
    private String name;

}
