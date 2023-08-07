package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ApiModel
@Data
public class MissionFlagQuery {
	@ApiModelProperty(value = "flag类型")
	private Integer flagType;

	@ApiModelProperty(value = "flag值")
	private String flagValue;

	@ApiModelProperty(value = "flag值")
	private List<MissionFlagAttachmentQuery>  attachmentList;

	public enum FlagType {

		STATIC_FLAG(0, "静态flag"), DYNAMIC_FLAG(1, "动态flag");

		public static final List<Integer> FLAG_TYPE_LIST = new ArrayList<>(
				Arrays.asList(STATIC_FLAG.code, DYNAMIC_FLAG.code));
		@Getter
		private Integer code;

		@Getter
		private String desc;

		FlagType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}