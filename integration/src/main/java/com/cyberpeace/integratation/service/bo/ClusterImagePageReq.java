package com.cyberpeace.integratation.service.bo;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * 簇图像分页请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class ClusterImagePageReq {

	/**
	 * 分页
	 */
	private Integer page;

	/**
	 * 每分页
	 */
	private Integer per_page;

	/**
	 * 图像类型
	 */
	private String image_type;

	/**
	 * 岗位
	 */
	private Integer role;

	/**
	 * 主要分类
	 */
	private Integer main_category;

	/**
	 * 子类别
	 */
	private Integer sub_category;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * VM 已启动
	 */
	private Boolean vmStarted;

	public String toQuery() {
		StringBuilder builder = new StringBuilder();
		builder.append("?per_page=").append(this.per_page);
		builder.append("&page=").append(this.page);
		if (StringUtils.isNotBlank(this.image_type)) {
			builder.append("&image_type=").append(this.image_type);
		}
		if (null != this.role) {
			builder.append("&role=").append(this.role);
		}
		if (null != this.main_category) {
			builder.append("&main_category=").append(this.main_category);
		}
		if (null != this.sub_category) {
			builder.append("&sub_category=").append(this.sub_category);
		}
		if (StringUtils.isNotBlank(this.name)) {
			builder.append("&name=").append(this.name);
		}
		if (Boolean.TRUE.equals(this.vmStarted)) {
			builder.append("&vmStarted=true");
		} else {
			builder.append("&vmStarted=false");
		}
		return builder.toString();
	}
}
