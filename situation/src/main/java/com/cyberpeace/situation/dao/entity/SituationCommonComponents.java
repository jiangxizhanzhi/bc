package com.cyberpeace.situation.dao.entity;

import java.util.EnumSet;
import java.util.HashMap;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

/**
 * @Author xuxz
 * @Date 16/11/2022 下午5:00
 */
@Data
@TableName("situation_components_common")
public class SituationCommonComponents extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String name;

	private Integer componentType;

	private Integer type;

	private String img;

	private String url;

	private String config;

	public enum CommonComponentsType {

		INPUT(0, "文本输入框"), SITUATION_TITLE(1, "态势标题"), CHART_TITLE(2, "图表标题"), IMG(3,
				"图片"), VIDEO(4, "视频"), AUDIO(5, "音频"), CHART(6, "表格"), FRAME(7, "边框");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		CommonComponentsType(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	public static HashMap<Integer, String> getCommonComponentsType() {
		HashMap<Integer, String> map = new HashMap();
		for (CommonComponentsType commonComponentsType : EnumSet
				.allOf(CommonComponentsType.class)) {
			map.put(commonComponentsType.code, commonComponentsType.desc);
		}
		return map;
	}

}
