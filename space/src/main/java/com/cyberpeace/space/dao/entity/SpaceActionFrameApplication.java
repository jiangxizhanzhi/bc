package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.frame.ActionFrameArrangeBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameApplicationBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
@TableName("space_action_frame_application")
public class SpaceActionFrameApplication extends SpaceBaseTable {

	@ApiModelProperty(value = "红蓝对抗业务名称")
	private String rvbName;

	@ApiModelProperty(value = "红蓝对抗业务id")
	private String rvbId;

	@ApiModelProperty(value = "框架id")
	private String frameId;

	@ApiModelProperty(value = "框架名称")
	private String name;

	@ApiModelProperty(value = "分类，面向流程，面向目标")
	private Integer type;

	@ApiModelProperty(value = "类型id")
	private String categoryId;

	@ApiModelProperty(value = "应用场景")
	private String tag;

	@ApiModelProperty(value = "使用说明")
	private String description;

	@ApiModelProperty(value = "框架编排")
	private String frame;

	public SpaceActionFrameApplicationBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceActionFrameApplicationBo bo = BeanUtils.copy(this,
				SpaceActionFrameApplicationBo.class);
		bo.setTag(JsonUtil.toJsonObjectArray(this.getTag(), String.class));
		bo.setFrame(this.getArrangeBo());
		return bo;
	}

	public ActionFrameArrangeBo getArrangeBo() {
		ActionFrameArrangeBo bo = JsonUtil.toJsonObject(this.frame, ActionFrameArrangeBo.class);
		if (null == bo) {
			bo = new ActionFrameArrangeBo();
			bo.setResourceId(this.getResourceId());
		}
		return bo;
	}
}
