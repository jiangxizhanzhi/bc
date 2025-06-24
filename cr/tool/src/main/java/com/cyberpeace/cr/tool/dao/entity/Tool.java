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
 * @date 2024/11/14
 */
@Data
@TableName("cr_tool_tool")
public class Tool extends BaseTable {

	/**
	 * 在线
	 */// 在线
	public static final Integer ONLINE = 1;

	/**
	 * 离线
	 */// 离线
	public static final Integer OFFLINE = 0;

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 盖
	 */
	private String cover;

	/**
	 * 主页
	 */
	@Deprecated
	private String homepage;

	/**
	 * 类别
	 */
	private String category;

	/**
	 * 人工
	 */
	@Deprecated
	private String manual;

	/**
	 * 图像资源id
	 */
	private String imageResourceId;

	/**
	 * 可见
	 */
	private Boolean visible;

	/**
	 * 工具类型
	 */// 0 离线， 1在线
	private Integer toolType;

	/**
	 * 平台
	 */
	private String platforms;

	/**
	 * 语言
	 */
	private String language;

	/**
	 * 介绍
	 */
	private String introduction;

	/**
	 * 工具 public
	 */
	@Deprecated
	@TableField("`public`")
	private Integer toolPublic;

	/**
	 * 破获
	 */
	private Boolean cracked;

	/**
	 * 标记
	 */
	@Deprecated
	private String tag;

	/**
	 * 场景
	 */
	private String scene;

	/**
	 * 版本
	 */
	private String version;

	/**
	 * 文件
	 */
	private String files;

	/**
	 * 封面 URL
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