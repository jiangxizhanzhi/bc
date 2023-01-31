package com.cyberpeace.bs.dao.entity;

import java.io.Serializable;
import java.time.LocalTime;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("bs_user_instance")
public class UserInstance implements Serializable {

	private Integer id;

	private String userName;

	private String userTypeId;

	private String userType;

	private LocalTime startTime;

	private LocalTime endTime;

	private Integer accessConfig;

	private String behaviorList;

	private String behaviorVariable;

	private Integer behaviorCount;

	private String accessHabit;

	private Boolean bandwidthLimited;

	private Integer bandwidth;

	private String userSimulatorId;

	private String userModelId;
}
