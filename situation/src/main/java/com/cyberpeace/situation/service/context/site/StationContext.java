package com.cyberpeace.situation.service.context.site;

import java.io.Serializable;
import java.util.Arrays;

import com.cyberpeace.integratation.constant.BcType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StationContext extends SiteContext implements Serializable {

	@ApiModelProperty("分靶场ID")
	private String subRangeId;

	@ApiModelProperty("场站ID")
	private String stationId;

	@ApiModelProperty("场站名称")
	private String stationName;

	@ApiModelProperty("场站类型")
	private String stationType;

	@ApiModelProperty("资源数量")
	private Integer resourceCount = 0;

	@Override
	public String getId() {
		return this.stationId;
	}

	public void setGroupId(String groupId) {
		this.stationId = groupId;
	}

	public void setName(String name) {
		this.stationName = name;
	}

	public void setBcType(String bcType) {
		this.stationType = Arrays.stream(BcType.class.getEnumConstants())
				.filter(t -> t.getCode().equals(bcType)).findFirst().orElse(BcType.FCZ).getName();;
	}

	public void setParentId(String parentId) {
		this.subRangeId = parentId;
	}

}
