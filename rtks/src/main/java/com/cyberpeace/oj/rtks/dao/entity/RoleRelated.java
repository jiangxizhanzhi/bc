package com.cyberpeace.oj.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("rtks_role_related")
public class RoleRelated implements Serializable {

    @JsonIgnore
    @TableId(type = IdType.AUTO)
    private Integer id;

    @JsonIgnore
    @TableField(value = "role_id")
    private String roleId;

    @TableField(value = "related_id")
    private String relatedId;

    @JsonIgnore
    private int type;

}
