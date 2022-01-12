package com.cyberpeace.cr.singletrain.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("cr_single_room")
public class SingleRoom extends BaseTable {

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
	 * 引用场景id
	 */
	private String localSceneId;

	/**
	 * 训练用户
	 */
	private String userId;

	/**
	 * 单兵训练在{@link com.cyberpeace.cr.crbase.dao.entity.TrainRecord#extra}中记录的json字符串使用字段名
	 */
	public interface CrTrainRecordFields {
		/**
		 * 训练得分
		 */
		String SCORE = "score";

		/**
		 * 任务训练记录
		 */
		String MISSION_RECORD = "train_record";
	}

}
