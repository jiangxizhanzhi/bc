package com.cyberpeace.cr.crbase.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 雷达标签
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
@TableName("cr_radar_label")
public class RadarLabel implements Serializable {

	@TableId(type = IdType.AUTO, value = "resource_id")
	private Integer resourceId;

	private String label;

}
