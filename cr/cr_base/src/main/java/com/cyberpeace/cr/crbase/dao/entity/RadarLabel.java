package com.cyberpeace.cr.crbase.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 雷达标签
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@TableName("cr_radar_label")
public class RadarLabel implements Serializable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.AUTO, value = "resource_id")
	private Integer resourceId;

	/**
	 * 标签
	 */
	private String label;

}
