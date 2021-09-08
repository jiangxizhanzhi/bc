package com.cyberpeace.cr.singletrain.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.cebase.model.crevent.CrEvent;
import com.cyberpeace.cr.cebase.resource.BaseResourceConfig;
import com.cyberpeace.cr.cebase.resource.Representation;

@Component
public class SingleTrainResourceConfig extends BaseResourceConfig<SingleTrainRepresentation> {

	@Override
	public int getCeType() {
		return CrEvent.CeType.TRAINING_SINGLE;
	}

	@Override
	public String getLabel() {
		return "单兵训练";
	}

	@Override
	protected SingleTrainRepresentation dumpMore() {
		SingleTrainRepresentation singleTrainRepresentation = new SingleTrainRepresentation();
		singleTrainRepresentation.setTemplate(new TemplateRep());
		// todo
		return singleTrainRepresentation;
	}

	@Override
	protected void loadMore(SingleTrainRepresentation representation) {
		// todo
	}

	@Override
	protected SingleTrainRepresentation jsonObject2T(JSONObject jsonObject) {
		return jsonObject.toJavaObject(SingleTrainRepresentation.class);
	}

}

@EqualsAndHashCode(callSuper = true)
@Data
class SingleTrainRepresentation extends Representation {

	/**
	 * _template
	 * */
	private TemplateRep Template;
}

@Data
class TemplateRep {

	private String sceneConfig;

	private String missionsScheme;

	private String role;
}