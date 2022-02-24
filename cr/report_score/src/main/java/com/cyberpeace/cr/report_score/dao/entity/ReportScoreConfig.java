package com.cyberpeace.cr.report_score.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 可能需要的配置，作为初始化数据
 * 1. 指定态势大屏显示的名称  type system
 * name： xxx系统大屏
 * 2. 指定需要统计的4套场景id   type system
 * scene: 123456672664562,41234123,421341
 * 3. 配置操作机IP和用户一一对应, 排行榜中的用户是否来自这里  type user
 * user_id: 192.168.1.1
 */
@Data
@TableName("cr_report_score_config")
public class ReportScoreConfig {

	/**
	 * 配置类型
	 */
	private String type;

	/**
	 *  配置key
	 */
	private String key;

	/**
	 * 配置值
	 */
	private String value;
}
