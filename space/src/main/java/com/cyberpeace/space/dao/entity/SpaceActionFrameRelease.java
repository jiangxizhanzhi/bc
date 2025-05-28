package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Getter;
import lombok.Setter;

/**
 * 空间行动框架下发
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_action_frame_release")
public class SpaceActionFrameRelease extends BaseTable {

	@TableId(type = IdType.AUTO)
	private Integer id;

	private String applicationId;

	private String rvbId;

	private String frameName;

	private String step;

	private String userId;

	private String userName;

	private String tactics;
}
