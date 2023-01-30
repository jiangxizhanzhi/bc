package com.cyberpeace.situation.service.context.site;

import java.util.Arrays;

import com.cyberpeace.integratation.constant.BcType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RangeContext extends SiteContext {

	@ApiModelProperty("分靶场ID")
	private String subRangeId;

	@ApiModelProperty("分靶场名称")
	private String subRangeName;

	@ApiModelProperty("分靶场类型")
	private String subRangeType;

	@ApiModelProperty("场站数量")
	private Integer stationCount;

	@Override
	public String getId() {
		return this.subRangeId;
	}

	public void setGroupId(String groupId) {
		this.subRangeId = groupId;
	}

	public void setName(String name) {
		this.subRangeName = name;
	}

	public void setBcType(String bcType) {
		this.subRangeType = Arrays.stream(BcType.class.getEnumConstants())
				.filter(t -> t.getCode().equals(bcType)).findFirst().orElse(BcType.MY).getName();;
	}

	public void setBcNumber(String bcNumber) {
		this.stationCount = Integer.parseInt(bcNumber);
	}

}
