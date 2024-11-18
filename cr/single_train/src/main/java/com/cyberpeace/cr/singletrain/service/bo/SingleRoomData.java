package com.cyberpeace.cr.singletrain.service.bo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * 单人房数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseTable
 * @since 1.0.0
 */
@Data
public class SingleRoomData extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 引用单兵训练模板id
	 *
	 * @see String
	 */
	private String businessTemplateId;

	/**
	 * 训练开始时间
	 *
	 * @see Date
	 */
	private Date startTime;

	/**
	 * 训练结束时间
	 *
	 * @see Date
	 */
	private Date endTime;

	/**
	 * 训练记录(任务完成记录)
	 *
	 * @see String
	 */
	private String trainingRecord;

	/**
	 * 训练记录(任务完成记录)
	 *
	 * @see String
	 */
	private String subType;
	/**
	 * 是否已提交训练(训练已提交标识, 禁止用户重复提交)
	 *
	 * @see Boolean
	 */
	private Boolean isFinish;

	/**
	 * 引用场景id
	 *
	 * @see String
	 */
	private String localSceneId;

	/**
	 * 训练用户
	 *
	 * @see String
	 */
	private String userId;

}
