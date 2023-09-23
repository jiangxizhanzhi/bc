package com.cyberpeace.cr.singletrain.service.bo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
public class SingleRoomData extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 引用单兵训练模板id
	 */
	private String businessTemplateId;

	/**
	 * 训练开始时间
	 */
	private Date startTime;

	/**
	 * 训练结束时间
	 */
	private Date endTime;

	/**
	 * 训练记录(任务完成记录)
	 */
	private String trainingRecord;

    /**
     * 训练记录(任务完成记录)
     */
    private String subType;
	/**
	 * 是否已提交训练(训练已提交标识, 禁止用户重复提交)
	 */
	private Boolean isFinish;

	/**
	 * 引用场景id
	 */
	private String localSceneId;

	/**
	 * 训练用户
	 */
	private String userId;

}
