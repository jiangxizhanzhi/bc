package com.cyberpeace.cr.tool.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.ConfigUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 工具
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
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
	 * 手动
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
	@TableField("public")
	private Integer toolPublic;

	/**
	 * 破获
	 */
	private Boolean cracked;

	/**
	 * 禁用
	 */
	private Boolean disabled;

	/**
	 * 标记
	 */
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

	/**
	 * 类型
	 */
	private String type;

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

	public enum Type {

		PROBE("probe", "探测类"), ATTACK("attack", "攻击类"), CONTROL("control", "控制类"), DEFENSE("defense", "防御类");

		@Getter
		private String code;

		@Getter
		private String desc;

		Type(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}