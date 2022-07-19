package com.cyberpeace.rtks.dao.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("rtks_role_match_record")
public class RoleMatchRecord implements Serializable {

	private Integer id;

	private String userId;

	private String roleId;

	private Integer match;

	private Date createTime;

	private Date updateTime;
}
