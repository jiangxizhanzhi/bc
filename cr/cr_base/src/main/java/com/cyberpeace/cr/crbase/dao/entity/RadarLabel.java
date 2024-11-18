package com.cyberpeace.cr.crbase.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 雷达标签
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@TableName("cr_radar_label")
public class RadarLabel implements Serializable {

	/**
	 * 资源id
	 *
	 * @see Integer
	 */
	@TableId(type = IdType.AUTO, value = "resource_id")
	private Integer resourceId;

	/**
	 * 标签
	 *
	 * @see String
	 */
	private String label;

}
