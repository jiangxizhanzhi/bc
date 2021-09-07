package com.cyberpeace.cr.singletrain.model.room;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.suit.BaseEntity;

import lombok.Data;

@Data
@TableName("single_train_room")
public class Room extends BaseEntity {

	/**
	 * 模板id
	 * 原名training_template
	 */
	private String templateId;

	/**
	 * 用户id 原名user
	 */
	private String userId;

	/**
	 * 中台场景id 原名scene
	 * */
	private String sceneId;

	// 使用createTime替代原start_time

	/**
	 * 结束时间 允许为空
	 * */
	private Date endTime;

	// # 用户训练记录
	// training_record = models.TextField('任务记录', blank=True, null=True, default='')
}
