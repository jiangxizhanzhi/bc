package com.cyberpeace.cr.singletrain.model.room;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.suit.BaseEntity;

import lombok.Data;

@Data
@TableName("single_train_room")
public class Room extends BaseEntity {

	/**
	 * 关联com.cyberpeace.cr.singletrain.controller.Template的pk
	 * 原名training_template
	 */
	private String template;

	/**
	 * 用户id 原名user
	 */
	private String userId;
	// scene = models.CharField(max_length=100, blank=True, default='')
	// start_time = models.DateTimeField("开始时间", blank=True, null=True)
	// end_time = models.DateTimeField("结束时间", blank=True, null=True)
	// # 用户训练记录
	// training_record = models.TextField('任务记录', blank=True, null=True, default='')
}
