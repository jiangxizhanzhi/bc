package com.cyberpeace.cr.cebase.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.cebase.model.crevent.CrEvent;
import com.cyberpeace.cr.cebase.service.CrEventService;

@Component
public abstract class BaseResourceConfig<T extends Representation> implements ResourceConfig {

	protected abstract T dumpMore();

	protected abstract void loadMore(T t);

	@Autowired
	private CrEventService crEventService;

	@Override
	public Representation dump(CrEvent crEvent, Collector collector) {
		Representation representation = dumpMore();
		representation.setName(crEvent.getName());
		representation.setDescription(crEvent.getDescription());
		representation.setCategory(crEvent.getCategory());
		representation.setDifficult(crEvent.getDifficulty());
		return representation;
	}

	@Override
	public void load(JSONObject jsonObject, Dispatcher dispatcher) {
		T representation = jsonObject2T(jsonObject);
		CrEvent crEvent = new CrEvent();
		crEvent.setName(representation.getName());
		crEvent.setDescription(representation.getDescription());
		crEvent.setCategory(representation.getCategory());
		crEvent.setDifficulty(representation.getDifficult());
		crEventService.save(crEvent);
		loadMore(representation);
	}

	protected abstract T jsonObject2T(JSONObject jsonObject);
}
