package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.PageReqBase;

import lombok.Data;

/**
 * 通用控件分页返回
 *
 * @Author xuxz
 * @Date 16/11/2022 下午5:04
 */
@Data
public class SituationCommonComponentsPageRes extends PageReqBase implements Serializable {

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
	 * 组件类型
	 */
	private Integer componentType;

	/**
	 * 类型
	 */
	private Integer type;

	/**
	 * 图片
	 */
	private String img;

	/**
	 * url
	 */
	private String url;
}
