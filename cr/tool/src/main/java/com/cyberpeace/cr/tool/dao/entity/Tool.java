package com.cyberpeace.cr.tool.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.ConfigUtil;

import lombok.Data;
import lombok.Getter;

/**
 * 工具
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("cr_tool_tool")
public class Tool extends BaseTable {

	/**
	 * 在线
	 *
	 * @see Integer
	 */ // 在线
	public static final Integer ONLINE = 1;

	/**
	 * 离线
	 *
	 * @see Integer
	 */ // 离线
	public static final Integer OFFLINE = 0;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 盖
	 *
	 * @see String
	 */
	private String cover;

	/**
	 * 主页
	 *
	 * @see String
	 */
	@Deprecated
	private String homepage;

	/**
	 * 类别
	 *
	 * @see String
	 */
	private String category;

	/**
	 * 手动
	 *
	 * @see String
	 */
	@Deprecated
	private String manual;

	/**
	 * 图像资源id
	 *
	 * @see String
	 */
	private String imageResourceId;

	/**
	 * 可见
	 *
	 * @see Boolean
	 */
	private Boolean visible;

	/**
	 * 工具类型
	 *
	 * @see Integer
	 */ // 0 离线， 1在线
	private Integer toolType;

	/**
	 * 平台
	 *
	 * @see String
	 */
	private String platforms;

	/**
	 * 语言
	 *
	 * @see String
	 */
	private String language;

	/**
	 * 介绍
	 *
	 * @see String
	 */
	private String introduction;

	/**
	 * 工具 public
	 *
	 * @see Integer
	 */
	@Deprecated
	@TableField("public")
	private Integer toolPublic;

	/**
	 * 破获
	 *
	 * @see Boolean
	 */
	private Boolean cracked;

	/**
	 * 标记
	 *
	 * @see String
	 */
	@Deprecated
	private String tag;

	/**
	 * 场景
	 *
	 * @see String
	 */
	private String scene;

	/**
	 * 版本
	 *
	 * @see String
	 */
	private String version;

	/**
	 * 文件
	 *
	 * @see String
	 */
	private String files;

	/**
	 * 封面 URL
	 *
	 * @see String
	 */
	@TableField(exist = false)
	private String coverUrl;

	public String getCoverUrl() {
		String prefix = ConfigUtil.getContextProperty("cr.local.file.prefix");
		if (StringUtils.isNotBlank(cover) && !cover.startsWith(prefix)) {
			return prefix + cover;
		}
		return cover;
	}

	public enum Cracked {

		FREE("free", "免费"), TRIAL("trial", "限制"), NON_FREE("non-free", "收费");

		@Getter
		private String code;

		@Getter
		private String desc;

		Cracked(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}