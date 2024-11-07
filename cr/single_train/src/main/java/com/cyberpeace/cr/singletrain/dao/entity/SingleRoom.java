package com.cyberpeace.cr.singletrain.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 单人房
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@TableName("cr_single_room")
public class SingleRoom extends BaseTable {

	/**
	 * 资源id
	 */
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
	 * {
	 * "5e1de95e1efc475bbbfeacaa31d0cfb3":
	 * {
	 * "answer": "B",
	 * "record": "",
	 * "time": 1684312011951
	 * }
	 * ,
	 * "266966553e6f4c8a91591170b73dc51c":
	 * {
	 * "answer": "B",
	 * "record": "",
	 * "time": 1684312008977
	 * }
	 * }
	 */
	private String trainingRecord;

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

	/**
	 * 单兵训练在{@link com.cyberpeace.cr.crbase.dao.entity.TrainRecord#extra}中记录的json字符串使用字段名
	 */
	public interface CrTrainRecordFields {
		/**
		 * 训练总分
		 */
		String SCORE = "score";

		/**
		 * 任务训练记录。 训练关闭时, {@link SingleRoom#trainingRecord} 内容记录到此字段
		 */
		String MISSIONS_RECORD = "missions_record";
	}

	/**
	 * {@link SingleRoom#trainingRecord} json字符串字段名
	 */
	public interface MissionsRecordFields {
		/**
		 * 提交时间
		 */
		String TIME = "time";

		/**
		 * 任务得分
		 */
		String SCORE = "score";

		/**
		 * 任务结果(得分)
		 */
		String RESULT = "result";

		/**
		 * 用户提交答案
		 */
		String ANSWER = "answer";

		/**
		 * 用户答题记录
		 */
		String RECORD = "record";
	}

}
