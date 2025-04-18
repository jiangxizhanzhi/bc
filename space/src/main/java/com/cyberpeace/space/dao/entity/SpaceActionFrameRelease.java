package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("space_action_frame_release")
public class SpaceActionFrameRelease extends BaseTable {

	@TableId(type = IdType.AUTO)
	private Integer id;

	private String applicationId;

	private String stage;

	private String userId;

	private String userName;
}
