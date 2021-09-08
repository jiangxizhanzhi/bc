package com.cyberpeace.cr.cebase.resource;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.cebase.model.crevent.CrEvent;

public interface ResourceConfig {

	int getCeType();

	String getLabel();

	Representation dump(CrEvent crEvent, Collector collector);

	void load(JSONObject jsonObject, Dispatcher dispatcher);

}
